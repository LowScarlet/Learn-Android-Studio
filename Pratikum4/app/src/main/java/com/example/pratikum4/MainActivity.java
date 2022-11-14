package com.example.pratikum4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button login;
    String user = "yuni", pass = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
    }

    public void login(View view){
        if (username.getText().toString().equals(user) && password.getText().toString().equals(pass)){
            Intent i =new Intent(getApplicationContext(),SecondActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(getBaseContext(),"Login tidak berhasil", Toast.LENGTH_LONG).show();
        }
    }
}