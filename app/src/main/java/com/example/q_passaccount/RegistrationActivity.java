package com.example.q_passaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    //Initializing Variables
    TextView Email, Fname, Mname, Lname, Contact, Address, Password;
    Button Register;
    CheckBox ShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Assigning Variables
        Email = findViewById(R.id.Register_EMAIL);
        Fname = findViewById(R.id.Register_Firsname);
        Mname = findViewById(R.id.Register_Middlename);
        Lname = findViewById(R.id.Register_Lastname);
        Contact = findViewById(R.id.Register_ContactNumber);
        Address = findViewById(R.id.Register_Address);
        Password = findViewById(R.id.Register_Password);
        Register = findViewById(R.id.btnREGISTER);
        ShowPassword = findViewById(R.id.CB_CheckPass);

        //Show Password using checkbox
        ShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b) {
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NullRestriction();
                QRGenerator();
            }
        });//TODO QR GENERATOR AND REALTIME DATABASE
    }


    //prevent user from entering incorrect information
    private void NullRestriction(){
        if (Email.length()==0){
            Email.setError("Please Enter your Email");
            Email.requestFocus();
        } else if (Fname.length()==0){
            Fname.setError("Please Enter your Firstname");
            Fname.requestFocus();
        } else if (Lname.length()==0){
            Lname.setError("Please Enter your Lastname");
            Lname.requestFocus();
        } else if (Contact.length()==0){
            Contact.setError("Please Enter you contact number");
            Contact.requestFocus();
            if (Contact.length()<=9 || Contact.length()>=11){
                Contact.setError("Invalid Contact number");
                Contact.requestFocus();
            }
        } else if (Address.length()==0) {
            Address.setError("Please Enter you Address");
            Address.requestFocus();
        } else if (Password.length()<=6) {
            Password.setError("Invalid Password");
        } else {
            Intent homeintent = new Intent(RegistrationActivity.this, HomeActivity.class);
            startActivity(homeintent);
            finish();
        }
    }//TODO update information validation using firebase and add password confirmation
    //QR Code Generator
    private void QRGenerator(){

    }
}
