package com.iiitmk.project.droidrecon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViewSubDomain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sub_domain);

        TextView txt = findViewById(R.id.txt1save);

        Intent intent = getIntent();
        String domainName = intent.getStringExtra("domainName");
        String fullName = intent.getStringExtra("fullName");
        String Mode = intent.getStringExtra("Mode");
        String time = intent.getStringExtra("time");

        txt.setText(time);





    }
}