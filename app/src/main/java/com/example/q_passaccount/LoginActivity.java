package com.example.q_passaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout Email, Password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.Login_Email);
        Password = findViewById(R.id.Login_Password);
    }
    //Restrict null on email address
    private Boolean ValidateEmail(){
        String email = Email.getEditText().getText().toString().trim();
        if(email.isEmpty()){
            Email.setError("Please provide your email address");
            return false;
        }
        else {
            Email.setError(null);
            Email.setErrorEnabled(false);
            return true;
        }
    }
    //Restrict null on password
    private Boolean ValidatePass(){
        String password = Password.getEditText().getText().toString().trim();
        if(password.isEmpty()){
            Password.setError("Please provide your password");
            return false;
        }
        else {
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
        }

    }
    //Validate Email and password upon login-in
    public void validateInfo(View v){
        if (!ValidateEmail() | ! ValidatePass()){
            return;
        }
        else{
            UserLogin();
        }

    }//Open register activity
    public void Register(View v){
        Intent register = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(register);
    }
    //Sign-in user
    private void UserLogin(){
        String email = Email.getEditText().getText().toString().trim();
        String password = Password.getEditText().getText().toString().trim();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(home);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}