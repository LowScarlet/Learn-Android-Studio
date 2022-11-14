package com.example.pratikum4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    Button hasil, back, exit;
    EditText berat, tinggi;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        berat = (EditText)findViewById(R.id.berat);
        tinggi = (EditText)findViewById(R.id.tinggi);
        output = (TextView)findViewById(R.id.output);
        hasil = (Button)findViewById(R.id.hasil);
        back = (Button)findViewById(R.id.back);
        exit = (Button)findViewById(R.id.exit);
        hasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_hasil(view);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_exit(view);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_back(view);
            }
        });
    }

    public void go_exit(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda Ingin Keluar ?")
            .setCancelable(false).setPositiveButton("OK", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        SecondActivity.this.finish();
                    }
                })
            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
    }

    public void go_back(View view) {
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivityForResult(i, 0);
    }

    public void get_hasil(View view){
        try {
            int angka = 110;
            int angka1 = Integer.parseInt(berat.getText().toString());

            int angka2 = Integer.parseInt(tinggi.getText().toString());
            int ideal = angka2 - angka1;
            int ideal2 = angka2 - angka;

            if(ideal == 110){
                output.setText("Berat Badan Anda Ideal");
            } else if (ideal > 110){
                output.setText("Anda Kurus");
            } else {
                output.setText("AndaGemuk, berat Anda " + angka1 + " Tinggi Anda" + angka2 + " Seharusnya Berat badan anda " + ideal2);
            }
        } catch(Exception e) {
            Toast.makeText(getBaseContext(), "Masukkan Berat dan Tinggi Badan Anda", Toast.LENGTH_SHORT).show();output.setText("Masukkan Angka Dulu");
        }
    }
}