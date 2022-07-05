package com.example.final_project;

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

        Button TAB1 = (Button)findViewById(R.id.TAB1);
        TAB1.setOnClickListener(new View.OnClickListener() {      // TAB1 버튼 클릭
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Contacts.class);
                startActivity(intent);
            }
        });

        Button TAB2 = (Button)findViewById(R.id.TAB2);
        TAB2.setOnClickListener(new View.OnClickListener() {      // TAB2 버튼 클릭
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AndroidGridLayoutActivity.class);
                startActivity(intent);
            }
        });

        Button TAB3 = (Button)findViewById(R.id.TAB3);
        TAB3.setOnClickListener(new View.OnClickListener() {      // TAB2 버튼 클릭
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RubyLoading.class);
                startActivity(intent);
            }
        });

    }
}