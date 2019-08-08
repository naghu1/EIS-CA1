package com.example.eisebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Toast;

public class Navigation extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("EIS Ebooks");
        setSupportActionBar(toolbar);
    }
}
