package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    int[] buttonIDs = new int[] {R.id.ans_2_a, R.id.ans_2_b, R.id.ans_2_c, R.id.ans_2_d, R.id.ans_2_e };
    int nilai = 0;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void jawaban_salah(View view) {
        nilai -= 5;
    }

    public void jawaban_benar(View view) {
        nilai += 10;
    }

    public void go_submit(View view) {
        for(int i=0; i<buttonIDs.length; i++) {
            CheckBox b = (CheckBox) findViewById(buttonIDs[i]);
            b.setChecked(false);
        }
        output = (TextView)findViewById(R.id.output);
        output.setText("Nilai kamu: "+nilai);

        Toast.makeText(getBaseContext(), "Nilai kamu: "+nilai, Toast.LENGTH_SHORT).show();
        nilai = 0;
    }
}