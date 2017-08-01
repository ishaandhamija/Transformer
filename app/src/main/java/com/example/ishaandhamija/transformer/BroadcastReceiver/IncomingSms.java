package com.example.ishaandhamija.transformer.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ishaandhamija on 31/07/17.
 */

public class IncomingSms extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);

                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

//                    Log.d("SmsReceiver", "SenderNumber: "+ senderNum + "\nMessage: " + message);
//
//                    Log.d("SmsReceiver", "onReceive: " + message.indexOf('\n'));
//
//                    Log.d("SmsReceiver", "onReceive: " + message.indexOf('z'));
//
//                    Log.d("SmsReceiver", "onReceive: " + message.toLowerCase().contains("Hello".toLowerCase()));

                    if (message.contains("GSM ID")){

                        File direct = new File(Environment.getExternalStorageDirectory() + "/Transformer");

                        if(!direct.exists()) {
                            direct.mkdir();
                        }

                        int bracketStart = message.indexOf('(');
                        int bracketEnd = message.indexOf(')');

                        String gsmID = message.substring(bracketStart + 1, bracketEnd).trim();

                        File myFile = new File(direct.getAbsolutePath() + "/" + gsmID.toString() + ".pdf");

                        if (myFile.exists()){
                            String pdfText = readPDF("/Transformer/" + gsmID.toString() + ".pdf");

                            pdfText = pdfText + "\n" + message;

                            createPDF(pdfText, gsmID.toString());
                        }
                        else{
                            String pdfText = "";
                            pdfText = pdfText + "\nGSM ID : " + gsmID + "\n\n" + message;
                            createPDF(pdfText, gsmID.toString());
                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }

    private void createPDF(String msg, String pdf) throws FileNotFoundException, DocumentException {
        Document doc = new Document();

        String outPath = Environment.getExternalStorageDirectory() + "/Transformer/" + pdf + ".pdf";

        PdfWriter.getInstance(doc, new FileOutputStream(outPath));
        doc.open();
        doc.add(new Paragraph(msg));
        doc.close();
    }

    private String readPDF(String filename) throws IOException {
        String parsedText = "";
        PdfReader reader = new PdfReader(Environment.getExternalStorageDirectory() + filename);
        int n = reader.getNumberOfPages();
        for (int i = 0; i <n ; i++) {
            parsedText  = parsedText + PdfTextExtractor.getTextFromPage(reader, i+1).trim() + "\n";
        }
        Log.d("KuchMila", "createPDF: " + parsedText);
        reader.close();

        return parsedText;

    }

}
