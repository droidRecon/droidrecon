package com.iiitmk.project.droidrecon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
EditText edusername,edpassword;
Button btnLogin,btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edusername = (EditText) findViewById(R.id.usernameLogin);
        edpassword = (EditText) findViewById(R.id.passwordLogin);
        btnLogin = (Button) findViewById(R.id.loginLogin);
        btnReg = (Button) findViewById(R.id.regLogin);




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = edusername.getText().toString().trim();
                Toast.makeText(getApplicationContext(), a.toString(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),HomeUI.class));
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }
        });
    }
}