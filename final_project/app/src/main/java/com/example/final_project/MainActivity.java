package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton TAB1 = (ImageButton)findViewById(R.id.TAB1);
        TAB1.setOnClickListener(new View.OnClickListener() {      // TAB1 버튼 클릭
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Contacts.class);
                startActivity(intent);
            }
        });

        ImageButton TAB2 = (ImageButton)findViewById(R.id.TAB2);
        TAB2.setOnClickListener(new View.OnClickListener() {      // TAB2 버튼 클릭
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AndroidGridLayoutActivity.class);
                startActivity(intent);
            }
        });

        ImageButton TAB3 = (ImageButton)findViewById(R.id.TAB3);
        TAB3.setOnClickListener(new View.OnClickListener() {      // TAB2 버튼 클릭
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Calendar.class);
                startActivity(intent);
            }
        });

    }
}