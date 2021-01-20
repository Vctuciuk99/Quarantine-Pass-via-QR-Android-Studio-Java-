package com.example.q_passaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {
    private TextView Email, Fname, Mname, Lname, Contact, Province, Municipality, Barangay, Street;
    private String  Uid;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Email = findViewById(R.id.Acc_Email);
        Fname = findViewById(R.id.Acc_Fname);
        Mname = findViewById(R.id.Acc_Mname);
        Lname = findViewById(R.id.Acc_Lname);
        Contact = findViewById(R.id.Acc_Contact);
        Province = findViewById(R.id.Acc_Province);
        Municipality = findViewById(R.id.Acc_Municipality);
        Barangay = findViewById(R.id.Acc_Barangay);
        Street = findViewById(R.id.Acc_Street);


        Toolbar toolbar = findViewById(R.id.Acc_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Uid = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User Data").child(Uid);

        //retrieving information from database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Information database references
                String email = dataSnapshot.child("email").getValue().toString();
                String fname = dataSnapshot.child("first_name").getValue().toString();
                String mname = dataSnapshot.child("middle_name").getValue().toString();
                String lname = dataSnapshot.child("last_name").getValue().toString();
                String contact = dataSnapshot.child("contact_Info").getValue().toString();
                String province = dataSnapshot.child("province").getValue().toString();
                String municipality = dataSnapshot.child("municipality").getValue().toString();
                String barangay = dataSnapshot.child("barangay").getValue().toString();
                String street = dataSnapshot.child("street").getValue().toString();

                //Assigning retrieve information into each textview
                Email.setText(email);
                Fname.setText(fname);
                if (mname.isEmpty()){
                    Mname.setText("No middle name given");
                }
                else {
                    Mname.setText(mname);
                }
                Lname.setText(lname);
                Contact.setText(contact);
                Province.setText(province);
                Municipality.setText(municipality);
                Barangay.setText(barangay);
                Street.setText(street);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}