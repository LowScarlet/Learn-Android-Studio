package com.example.pratikum1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1=(Button)findViewById(R.id.linear_layout_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i =new Intent(getApplicationContext(),LinearLayout.class);
                startActivity(i);
            }
        });

        Button btn2=(Button)findViewById(R.id.table_layout_btn);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i =new Intent(getApplicationContext(),TableLayout.class);
                startActivity(i);
            }
        });

        Button btn3=(Button)findViewById(R.id.scroll_view_layout_btn);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i =new Intent(getApplicationContext(),ScrollViewLayout.class);
                startActivity(i);
            }
        });

        Button btn4=(Button)findViewById(R.id.relative_layout_btn);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i =new Intent(getApplicationContext(),RelativeLayout.class);
                startActivity(i);
            }
        });
    }
}