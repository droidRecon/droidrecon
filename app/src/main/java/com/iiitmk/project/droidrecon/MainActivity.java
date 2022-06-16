package com.iiitmk.project.droidrecon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH=2000;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preferences=getSharedPreferences("LOGIN",MODE_PRIVATE);
                String phone=preferences.getString("mobile",null);

                if(phone!=null){
                    startActivity(new Intent(getApplicationContext(),HomeUI.class));
                    finish();
                }else{
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }

            }
        },SPLASH);
    }
}