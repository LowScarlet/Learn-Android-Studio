package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int[] buttonIDs = new int[] {R.id.ans_1_a, R.id.ans_1_b, R.id.ans_1_c, R.id.ans_1_d };
    int nilai = 0;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jawaban_salah(View view) {
        nilai -= 2;
    }

    public void jawaban_benar(View view) {
        nilai += 10;
    }

    public void go_submit(View view) {
        for(int i=0; i<buttonIDs.length; i++) {
            RadioButton b = (RadioButton) findViewById(buttonIDs[i]);
            b.setChecked(false);
        }
        output = (TextView)findViewById(R.id.output);
        output.setText("Nilai kamu: "+nilai);

        Toast.makeText(getBaseContext(), "Nilai kamu: "+nilai, Toast.LENGTH_SHORT).show();
        nilai = 0;
    }

    public void go_next(View view) {
        Intent i =new Intent(getApplicationContext(),SecondActivity.class);
        startActivity(i);
    }

}