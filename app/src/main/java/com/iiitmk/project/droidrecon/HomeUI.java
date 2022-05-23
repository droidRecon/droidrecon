package com.iiitmk.project.droidrecon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeUI extends AppCompatActivity {
Button btnSaved,btnNormal,btnStaged,btnWifi;
EditText edTarget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ui);

        edTarget = (EditText) findViewById(R.id.targetHomeUI);
        btnSaved = (Button) findViewById(R.id.saveHomeUI);
        btnNormal = (Button) findViewById(R.id.normalScanHomeUI);
        btnStaged = (Button) findViewById(R.id.stagedScanHomeUI);
        btnWifi = (Button) findViewById(R.id.wifiScanHomeUI);


        btnSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SavedResults.class));
            }
        });
        btnStaged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),StagedHome.class));
            }
        });
        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),WifiActivity.class));
            }
        });
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Scanning on normal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}