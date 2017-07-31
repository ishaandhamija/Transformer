package com.example.ishaandhamija.transformer.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ishaandhamija.transformer.R;

public class MainActivity extends AppCompatActivity {

    public static final Integer REQ_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int receiveSmsPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSmsPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        if ((receiveSmsPerm != PackageManager.PERMISSION_GRANTED) || (readSmsPerm != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS
            }, REQ_CODE);
        }
        else {

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
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
