package com.example.ishaandhamija.transformer.Utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ishaandhamija.transformer.R;

import java.io.File;

/**
 * Created by ishaandhamija on 24/06/17.
 */

public class AllContactsHolder extends RecyclerView.ViewHolder {

    TextView allContactsName;
    View view;

    public AllContactsHolder(View itemView, final Context ctx) {
        super(itemView);

        this.allContactsName = (TextView) itemView.findViewById(R.id.pdfName);

        this.view = itemView;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = Environment.getExternalStorageDirectory() + "/Transformer/" + allContactsName.getText().toString();
                File file = new File(filename);
                Uri internal = Uri.fromFile(file);
                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(internal, "application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                Intent intent = Intent.createChooser(target, "Open File");
                try {
                    ctx.startActivity(intent);
                }
                catch (ActivityNotFoundException e) {

                }
            }
        });

    }
}
