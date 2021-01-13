package com.example.q_passaccount;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

public class RegistrationActivity extends AppCompatActivity {

    //Initializing Variables
    TextView AccId;
    EditText Email, Fname, Mname, Lname, Contact, Province, Municipality, Barangay, Street, Password;
    Button Register;
    CheckBox ShowPassword;
    ImageView imageView;
    //Uri uri;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User Data");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Assigning Variables
        AccId = findViewById(R.id.AccId);
        Email = findViewById(R.id.Register_EMAIL);
        Fname = findViewById(R.id.Register_Firsname);
        Mname = findViewById(R.id.Register_Middlename);
        Lname = findViewById(R.id.Register_Lastname);
        Contact = findViewById(R.id.Register_ContactNumber);
        Province = findViewById(R.id.Register_Province);
        Municipality = findViewById(R.id.Register_Municipality);
        Barangay  = findViewById(R.id.Register_Brgy);
        Street = findViewById(R.id.Register_StreetNumber);
        Password = findViewById(R.id.Register_Password);
        Register = findViewById(R.id.btnREGISTER);
        ShowPassword = findViewById(R.id.CB_CheckPass);
        imageView = findViewById(R.id.imageView);


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

            }
        });
    }


    //prevent user from entering incorrect or null information
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
        } else if (Contact.length()!=10){
            Contact.setError("Invalid Contact Number");
            Contact.requestFocus();
        } else if (Province.length()==0) {
            Province.setError("Please Enter you Address");
            Province.requestFocus();
        } else if (Municipality.length()==0) {
            Municipality.setError("Please Enter you Address");
            Municipality.requestFocus();
        } else if (Barangay.length()==0) {
            Barangay.setError("Please Enter you Address");
            Barangay.requestFocus();
        } else if (Street.length()==0) {
            Street.setError("Please Enter you Address");
            Street.requestFocus();
        } else if (Password.length()<=6) {
            Password.setError("Invalid Password");
        } else {
            RegisterUser();
        }
    }//TODO update information validation using firebase and add password confirmation

    //Register user Email and password
    private void RegisterUser(){
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "Email is invalid or taken by someone else", Toast.LENGTH_SHORT).show();
                }
                else{
                    SaveUserInformation();
                }
            }
        });
    }

    //Save User Infromation To FireBase
    private void SaveUserInformation(){
        //Uri QrUrl = uri;
        String email = Email.getText().toString();
        String fname = Fname.getText().toString();
        String mname = Mname.getText().toString();
        String lname = Lname.getText().toString();
        String contact = Contact.getText().toString();
        String province = Province.getText().toString();
        String municipality = Municipality.getText().toString();
        String barangay = Barangay.getText().toString();
        String street = Street.getText().toString();
        String password = Password.getText().toString();
        //String qrcodeurl = QrUrl.toString();

        String id = databaseReference.push().getKey();
        UserData userData = new UserData(email, fname, mname, lname, contact, province, municipality, barangay, street, password);
        databaseReference.child(id).setValue(userData);
        AccId.setText(id);
        QRGenerator();
    }
    //QR Code Generator
    private void QRGenerator(){
        String Value = AccId.getText().toString();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{//generate QR code
            BitMatrix bitMatrix = multiFormatWriter.encode(Value, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            final Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
            //TODO Pang try ko lang ung imageview dito di pa to final
       }catch (Exception e){
           e.printStackTrace();
       }

    }

}
