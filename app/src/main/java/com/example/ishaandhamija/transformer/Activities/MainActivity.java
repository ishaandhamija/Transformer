package com.example.ishaandhamija.transformer.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.ishaandhamija.transformer.Models.PDFFile;
import com.example.ishaandhamija.transformer.R;
import com.example.ishaandhamija.transformer.Utils.AllContactsAdapter;

import java.io.File;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.sql.Types.*;

public class MainActivity extends AppCompatActivity {

    public static final Integer REQ_CODE = 1001;
    RecyclerView allContactsRV;
    AllContactsAdapter allContactsAdapter;
    public static ArrayList<PDFFile> allContactsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allContactsArrayList = new ArrayList<>();

        int receiveSmsPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSmsPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int writeExtPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if ((receiveSmsPerm != PackageManager.PERMISSION_GRANTED) || (readSmsPerm != PackageManager.PERMISSION_GRANTED) || (writeExtPerm != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQ_CODE);
        }
        else {
            displayPDFs();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQ_CODE) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Permission Not Given", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }
            displayPDFs();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void displayPDFs(){
        allContactsArrayList.clear();

        String path = Environment.getExternalStorageDirectory().toString() + "/Transformer";
        File directory = new File(path);
        File[] files = directory.listFiles();

        if (files == null){
            Toast.makeText(this, "No Files Added", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < files.length; i++) {
            allContactsArrayList.add(new PDFFile(files[i].getName()));
        }

        Collections.sort(allContactsArrayList, new CustomComparator());

        allContactsRV = (RecyclerView) findViewById(R.id.rvList);
        allContactsAdapter = new AllContactsAdapter(this, allContactsArrayList);
        allContactsRV.setLayoutManager(new LinearLayoutManager(this));
        allContactsRV.setAdapter(allContactsAdapter);
    }

    public class CustomComparator implements Comparator<PDFFile> {
        @Override
        public int compare(PDFFile o1, PDFFile o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

}
