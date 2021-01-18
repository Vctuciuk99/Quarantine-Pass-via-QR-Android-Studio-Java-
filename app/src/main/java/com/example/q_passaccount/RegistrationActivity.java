package com.example.q_passaccount;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    //Declaring Variables
    private EditText Email, Fname, Mname, Lname, Contact, Province, Municipality, Barangay, Street, Password;
    private Button Register;
    private CheckBox ShowPassword;
    private String uid;
    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

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
        Province = findViewById(R.id.Register_Province);
        Municipality = findViewById(R.id.Register_Municipality);
        Barangay  = findViewById(R.id.Register_Brgy);
        Street = findViewById(R.id.Register_StreetNumber);
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

            }
        });
    }

    public void NullRestriction(){
        String email = Email.getText().toString().trim();
        String contact = Contact.getText().toString().trim();

        if (Email.length() == 0) {
            Email.setError("Please Enter your Email");
            Email.requestFocus();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Email.setError("Invalid Email address");
                Email.requestFocus();
            }
            return;
        } else if (Fname.length() == 0) {
            Fname.setError("Please Enter your Firstname");
            Fname.requestFocus();
            return;
        } else if (Lname.length() == 0) {
            Lname.setError("Please Enter your Lastname");
            Lname.requestFocus();
            return;
        } else if (Contact.length() == 0) {
            Contact.setError("Invalid Contact Number");
            Contact.requestFocus();
            return;
        } else if (Province.length() == 0) {
            Province.setError("Please Enter you Address");
            Province.requestFocus();
            return;
        } else if (Municipality.length() == 0) {
            Municipality.setError("Please Enter you Address");
            Municipality.requestFocus();
            return;
        } else if (Barangay.length() == 0) {
            Barangay.setError("Please Enter you Address");
            Barangay.requestFocus();
            return;
        } else if (Street.length() == 0) {
            Street.setError("Please Enter you Address");
            Street.requestFocus();
            return;
        } else if (Password.length() <= 6) {
            Password.setError("Invalid Password");
            return;
        } else {
            RegisterUser();
            progressDialog = new ProgressDialog(RegistrationActivity.this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.register_progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            return;
        }

    }//TODO improve restriction using firebase
    //Register User information
   private void RegisterUser(){
        String email = Email.getText().toString();
        String password = Password.getText().toString();

        //Email and password authentication
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    uid = firebaseUser.getUid();
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("User QR Code/" + uid + "_" + email + ".jpg");

                    //Generate Qr Code for User
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try{
                        BitMatrix bitMatrix = multiFormatWriter.encode(uid, BarcodeFormat.QR_CODE,500,500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        final Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                        //Convert QR Code into JPEG File
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                        byte[] data = byteArrayOutputStream.toByteArray();

                        //Upload converted QR into Firebase Storage
                        UploadTask uploadTask = storageReference.putBytes(data);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        //Save User information to Firebase
                                        databaseReference = FirebaseDatabase.getInstance().getReference().child("User Data");

                                        //Get the string value for each input
                                        Uri QrUrl = uri;
                                        String email = Email.getText().toString().trim();
                                        String fname = Fname.getText().toString().trim();
                                        String mname = Mname.getText().toString().trim();
                                        String lname = Lname.getText().toString().trim();
                                        String contact = Contact.getText().toString().trim();
                                        String province = Province.getText().toString().trim();
                                        String municipality = Municipality.getText().toString().trim();
                                        String barangay = Barangay.getText().toString().trim();
                                        String street = Street.getText().toString().trim();
                                        String password = Password.getText().toString().trim();
                                        String qrcodeurl = QrUrl.toString();

                                        UserData userData = new UserData(email, fname, mname, lname, contact, province, municipality, barangay, street, password ,qrcodeurl);
                                        databaseReference.child(uid).setValue(userData);
                                        progressDialog.dismiss();
                                        firebaseAuth.signOut();
                                        Toast.makeText(RegistrationActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                                        //Open HomeActivity when registration was success
                                        Intent home = new Intent(RegistrationActivity.this, LoginActivity.class);
                                        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(home);

                                        finish();
                                    }
                                });
                            }
                        });
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });



                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }
                else{
                    Toast.makeText(RegistrationActivity.this, "Email is invalid or taken by someone else", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Email.requestFocus();
                }
            }
        });
    }

}

