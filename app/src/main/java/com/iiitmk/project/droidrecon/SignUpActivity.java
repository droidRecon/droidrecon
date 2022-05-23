package com.iiitmk.project.droidrecon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
Button btnLogin,btnRegister;
EditText edusername,edemail,edphone,edpassword,edrepassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnLogin = (Button) findViewById(R.id.loginSignUp);
        btnRegister = (Button) findViewById(R.id.regSignUp);
        edusername = (EditText) findViewById(R.id.usernameSignUp);
        edemail = (EditText) findViewById(R.id.emailSignUp);
        edpassword = (EditText) findViewById(R.id.passwordSignUp);
        edphone = (EditText) findViewById(R.id.phoneSignUp);
        edrepassword = (EditText) findViewById(R.id.repasswordSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user registeration.....
            }
        });

    }
}