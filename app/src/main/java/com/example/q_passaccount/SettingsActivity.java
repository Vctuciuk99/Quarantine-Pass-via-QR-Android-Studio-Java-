package com.example.q_passaccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {
    private Button update, changeE, changeP, addWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.Set_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        update = findViewById(R.id.Update);
        changeE = findViewById(R.id.ChangeEmail);
        changeP = findViewById(R.id.ChangePassword);
        addWidget = findViewById(R.id.Widget);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateInfo = new Intent(SettingsActivity.this, Update_InfoActivity.class);
                startActivity( updateInfo);
                finish();
            }
        });
        addWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent widget = new Intent(SettingsActivity.this, AddWidget.class);
                startActivity(widget);
                finish();
            }
        });
    }
}