package com.example.publicdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calendar extends AppCompatActivity implements Serializable{
    CalendarView calendarView;


    //  키값 : 날짜  value 값 날짜별 음식리스트트
   Map<String, ArrayList<FoodDataDto>> foodMapper = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setTitle("식단 관리 어플");


        calendarView = (CalendarView) findViewById(R.id.calendarView); // get the reference of CalendarView
        calendarView.setDate(1463918226920L);
        long selectedDate = calendarView.getDate();
        System.out.println("여기에요 여기 !!!!!!!!! ");
        System.out.println(selectedDate);

        Button total_data = (Button)findViewById(R.id.total_data);
        total_data.setOnClickListener(new View.OnClickListener() {      // total_data 버튼 클릭
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Total_data.class);
                startActivity(intent);
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                System.out.println("여기에요 여기 !!!!!!!!! ");
                String selectDate = i+"-"+ (i1+1)+"-"+i2 ;
                System.out.println(selectDate);
                Intent intent = new Intent(Calendar.this ,FoodManagementSystem.class);

                // 날짜에 대한 데이터가 있다면
                if(foodMapper.containsKey(selectDate)){
                    System.out.println("데이터가 있네요 !!!!!!!! ");
                    // 데이터 전달달
                   intent.putExtra("foodListByDate",foodMapper.get((String)selectDate));
                    }else{
                    System.out.println("데이터가 없네요 !!!!!!!! 만들께요 !!!!!!!!");
                    // 데이터 생성
                    ArrayList<FoodDataDto> foodListByDate = new ArrayList<>();
                    foodMapper.put(selectDate,foodListByDate);
                    // 데이터 전달
                    System.out.println(foodMapper.get((String)selectDate)==foodListByDate);
                    System.out.println("데이터가 없네요 !!!!!!!! 만들께요 !!!!!!!!");
                    intent.putExtra("foodListByDate", foodMapper.get((String)selectDate));
                    intent.putExtra("Data",selectDate);
                }
                startActivityForResult(intent, 0);

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {      // 데이터 받기 성공
                ArrayList<FoodDataDto> foodList =(ArrayList<FoodDataDto>) data.getSerializableExtra("result");
                String date = data.getStringExtra("Date");
                foodMapper.replace(date, foodList);

            } else {   // RESULT_CANCEL
                Toast.makeText(Calendar.this, "Failed", Toast.LENGTH_SHORT).show();
            }
//
        }
    }

}