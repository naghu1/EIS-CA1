package com.example.eisebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class Login extends AppCompatActivity {

    EditText userId, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userId = (EditText) findViewById(R.id.userId);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uid = userId.getText().toString();
                String pass = password.getText().toString();

                if (!uid.replaceAll(" ", "").equals("varsha")) {
                    showToastMessage("Invalid User ID");

                } else if (!pass.equals("tnces")) {
                    showToastMessage("Invalid Password");

                } else {
                    showToastMessage("Welcome!");

                    Intent intent = new Intent(Login.this, Navigation.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    private void showToastMessage(String msg) {

        Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
    }
}
