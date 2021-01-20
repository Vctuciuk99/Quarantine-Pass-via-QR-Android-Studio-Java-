package com.example.q_passaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    //Declaring Variable
    private TextView fullname, contactNo, Email;
    private ImageView imageView;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String Uid;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Email = findViewById(R.id.txtEmail);
        fullname = findViewById(R.id.txtFullName);
        contactNo = findViewById(R.id.txtContactNo);
        imageView = findViewById(R.id.QR_Image);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Uid = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User Data").child(Uid);

        ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.loading_progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //retrieving information from database
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Information database references
                final String qrlink = dataSnapshot.child("qrcode_Url").getValue().toString();
                Picasso.get().load(qrlink).into(imageView);
                String email = dataSnapshot.child("email").getValue().toString();
                String fname = dataSnapshot.child("first_name").getValue().toString();
                String mname = dataSnapshot.child("middle_name").getValue().toString();
                String lname = dataSnapshot.child("last_name").getValue().toString();
                String contact = dataSnapshot.child("contact_Info").getValue().toString();

                //Assigning retrieve information into each textview
                fullname.setText(lname + ", " + fname + " " + mname);
                contactNo.setText(contact);
                Email.setText(email);
                progressDialog.dismiss();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    //Call menu_bar.xml to HomeActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_button, menu);
        return true;
    }
    //Menu button function when click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Item1:
                Intent account = new Intent(HomeActivity.this, AccountActivity.class);
                startActivity(account);
                return true;
            case R.id.Item2:
                Intent update = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(update);
                return true;
            case R.id.Item3:
                new AlertDialog.Builder(this)
                        .setTitle("Sign-out")
                        .setMessage("Do you want to Sign out?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent login = new Intent(HomeActivity.this, LoginActivity.class);
                                startActivity(login);

                                FirebaseAuth.getInstance().signOut();
                                finish();
                            }

                        }).create().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Show warning when backpressed is click while user is on homeactivity
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Sign-out")
                .setMessage("Do you want to Sign out?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent login = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(login);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }

                }).create().show();

    }
}