package com.example.q_passaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {
    //Initializing Variable
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Assigning Variable
        imageView.findViewById(R.id.imageView2);

    }
}