package com.example.q_passaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText email, pass;
    TextView register;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.txtboxEMAIL);
        pass = findViewById(R.id.txtboxPASS);
        register = findViewById(R.id.txtREGISTER);
        login = findViewById(R.id.btnLOGIN);

        //button to trigger login function
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//TODO LOGIN FUNCTION HERE


        //button to trigger register function
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(register);
            }
        });
    }
}