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
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Update_InfoActivity extends AppCompatActivity {
    private TextInputLayout Email, Fname, Mname, Lname, Contact, Province, Municipality, Barangay, Street;
    private Button update;
    private String Uid;
    private ProgressDialog progressDialog;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__info);


        Fname = findViewById(R.id.Set_Fname1);
        Mname = findViewById(R.id.Set_Mname1);
        Lname = findViewById(R.id.Set_Lname1);
        Contact = findViewById(R.id.Set_Contact1);
        Province = findViewById(R.id.Set_Province1);
        Municipality = findViewById(R.id.Set_Municipality1);
        Barangay = findViewById(R.id.Set_Barangay1);
        Street = findViewById(R.id.Set_Street1);
        update = findViewById(R.id.Update);

        Toolbar toolbar = findViewById(R.id.update_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Uid = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User Data");

        //Update user information
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = Fname.getEditText().getText().toString().trim();
                String mname = Mname.getEditText().getText().toString().trim();
                String lname = Lname.getEditText().getText().toString().trim();
                String contact = Contact.getEditText().getText().toString().trim();
                String province = Province.getEditText().getText().toString().trim();
                String municipality = Municipality.getEditText().getText().toString().trim();
                String barangay = Barangay.getEditText().getText().toString().trim();
                String street = Street.getEditText().getText().toString().trim();

                //restrict null values
                if (fname.isEmpty()) {
                    Fname.setError("Please provide your first name");
                    return;
                } else if (lname.isEmpty()) {
                    Lname.setError("Please provide your last name");
                    return;
                } else if (contact.isEmpty()) {
                    Contact.setError("Please provide your Contact");
                    return;
                } else if (province.isEmpty()) {
                    Province.setError("Please provide your password");
                    return;
                } else if (municipality.isEmpty()) {
                    Municipality.setError("Please provide your password");
                    return;
                } else if (barangay.isEmpty()) {
                    Barangay.setError("Please provide your password");
                    return;
                } else if (street.isEmpty()) {
                    Street.setError("Please provide your password");
                    return;
                } else {

                    progressDialog = new ProgressDialog(Update_InfoActivity.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.loading_progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    //Assign updated information infto database
                    HashMap newInfo = new HashMap();
                    newInfo.put("first_name", fname);
                    newInfo.put("middle_name", mname);
                    newInfo.put("last_name", lname);
                    newInfo.put("contact_Info", contact);
                    newInfo.put("province", province);
                    newInfo.put("municipality", municipality);
                    newInfo.put("barangay", barangay);
                    newInfo.put("street", street);

                    //database reference
                    databaseReference.child(Uid).updateChildren(newInfo).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(Update_InfoActivity.this, "Your data was updated successfully", Toast.LENGTH_SHORT).show();
                            Intent home = new Intent(Update_InfoActivity.this, HomeActivity.class);
                            progressDialog.dismiss();
                            startActivity(home);

                        }
                    });
                }
            }
        });

    }

}