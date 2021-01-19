package com.example.q_passaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView fullname, contactNo;
    private ImageView imageView;


    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String Uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fullname = findViewById(R.id.txtFullName);
        contactNo = findViewById(R.id.txtContactNo);
        imageView = findViewById(R.id.QR_Image);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Uid = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User Data").child(Uid);

        ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);

        //Fetch data form DB after user Login
        databaseReference.addValueEventListener(new ValueEventListener() {



            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String qrlink = dataSnapshot.child("qrcode_Url").getValue().toString();
                Picasso.get().load(qrlink).into(imageView);

                String fname = dataSnapshot.child("first_name").getValue().toString();
                String mname = dataSnapshot.child("middle_name").getValue().toString();
                String lname = dataSnapshot.child("last_name").getValue().toString();
                String contact = dataSnapshot.child("contact_Info").getValue().toString();

                fullname.setText(lname + ", " + fname + " " + mname + ".");
                contactNo.setText(contact);
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
            case R.id.item1:
                Intent account = new Intent(HomeActivity.this, AccountActivity.class);
                startActivity(account);
                return true;
            case R.id.item2:
                Toast.makeText(this, "hulika balbon", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}