package com.example.uts_ti_a_2110031802003;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button hasil;
    EditText nama_mekanik, total_belanja;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama_mekanik = (EditText)findViewById(R.id.nama_mekanik);
        total_belanja = (EditText)findViewById(R.id.total_belanja);
        output = (TextView)findViewById(R.id.output);
        hasil = (Button)findViewById(R.id.hasil);

        hasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_hasil(view);
            }
        });
    }

    public int get_diskon_jabatan(String jabatan){
        if (jabatan == "Supervisior Mekanik") {
            return 40;
        }
        else if (jabatan == "Mekanik") {
            return 20;
        }
        else if (jabatan == "Administrasi") {
            return 10;
        }
        return 0;
    }

    public void get_hasil(View view){
        try {
            String jabatan = "";
            int diskon_jabatan;
            int besar_diskon, besar_diskon_2, pph = 0;
            int int_total_belanja = Integer.parseInt(total_belanja.getText().toString());
            int int_total_belanja_1, int_total_belanja_2, int_total_belanja_3 = 0;

            if (nama_mekanik.getText().toString().equals("Arjuna")) {
                jabatan = "Supervisior Mekanik";
            }
            else if (nama_mekanik.getText().toString().equals("Arya")) {
                jabatan = "Mekanik";
            }
            else if (nama_mekanik.getText().toString().equals("Aditya")) {
                jabatan = "Administrasi";
            };
            diskon_jabatan = get_diskon_jabatan(jabatan);

            besar_diskon = int_total_belanja*10/100;
            int_total_belanja_1 = int_total_belanja-besar_diskon;
            besar_diskon_2 = int_total_belanja_1*diskon_jabatan/100;
            int_total_belanja_2 = int_total_belanja_1-besar_diskon_2;
            pph = int_total_belanja_2*10/100;
            int_total_belanja_3 = int_total_belanja_2+pph;

            output.setText("Total belanja "+nama_mekanik.getText()+" adalah Rp."+int_total_belanja_3);

            Toast.makeText(getBaseContext(), "Total = "+int_total_belanja_3, Toast.LENGTH_SHORT).show();

        } catch(Exception e) {
            Toast.makeText(getBaseContext(), "Masukan nama mekanik dan total belanja", Toast.LENGTH_SHORT).show();output.setText("Masukkan Angka Dulu");
        }
    }
}