package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calendar extends AppCompatActivity implements Serializable{
    CalendarView calendarView;
    ArrayList<FoodDataDto> foodList = new ArrayList<FoodDataDto>();

    //  키값 : 날짜  value 값 날짜별 음식리스트트
   Map<String, ArrayList<FoodDataDto>> foodMapper = new HashMap<>();
   Map<String, Integer> CalroieMapper = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        // 음식 데이터 클래스 선언
        FoodData foodData = new FoodData();


        try {
            foodList = foodData.getAPIData();        //음식 데이터 Array 선언

        } catch (Exception e) {
            System.out.println("공공데이터 API 함수 호출 오류");
        }


        calendarView = (CalendarView) findViewById(R.id.calendarView); // get the reference of CalendarView
        calendarView.setDate(System.currentTimeMillis(),false,true);
        // 현재 날짜까지 표시
        System.out.println("공공데이터 API 함수 호출 오류");
        calendarView.setMaxDate(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis());
        // 현재 날짜 기준으로
        long selectedDate = calendarView.getDate();

        // 현재 날짜 2022-07-04


        Button total_data = (Button)findViewById(R.id.total_data);
        total_data.setOnClickListener(new View.OnClickListener() {      // total_data 버튼 클릭
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Total_data.class);
                intent.putExtra("Mapper", (Serializable) foodMapper);
                intent.putExtra("calorie_mapper", (Serializable) CalroieMapper);
                startActivity(intent);
            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                System.out.println("여기에요 여기 !!!!!!!!! ");
                // 날짜 format xxxx-xx-xx로 고정시키기
                String selectDate = "";
                selectDate += i+"-";
                if ((i1+1) < 10){
                    selectDate += "0"+(i1+1);
                } else{
                    selectDate += (i1+1);
                }
                if (i2 < 10){
                    selectDate += "-0"+i2 ;
                } else {
                    selectDate += "-" + i2;
                }

                System.out.println(selectDate);
                Intent intent = new Intent(Calendar.this , FoodManagementSystem.class);
                intent.putExtra("foodList",foodList);
                intent.putExtra("Date",selectDate);
                // 날짜에 대한 데이터가 있다면
                if(foodMapper.containsKey(selectDate)){
                    System.out.println("데이터가 있네요 !!!!!!!! ");
                    // 데이터 전달달
                    System.out.println(selectDate);
                    for(int j=0; j<foodMapper.get(selectDate).size();j++){
                        System.out.println(foodMapper.get(selectDate).get(j).getName());
                    }
                    intent.putExtra("foodListByDate",foodMapper.get(selectDate));
                    intent.putExtra("calorie_target",CalroieMapper.get(selectDate));

                  
                }else{
                    System.out.println("데이터가 없네요 !!!!!!!! 만들께요 !!!!!!!!");
                    // 데이터 생성
                    ArrayList<FoodDataDto> foodListByDate = new ArrayList<>();
                    foodMapper.put(selectDate,foodListByDate);
                    CalroieMapper.put(selectDate,2500);
                    // 데이터 전달
                    System.out.println(foodMapper.get((String)selectDate)==foodListByDate);
                    intent.putExtra("foodListByDate", foodMapper.get((String)selectDate));
                    intent.putExtra("calorie_target",CalroieMapper.get(selectDate));
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
                int target = data.getIntExtra("target", 2500);
                foodMapper.put(date, foodList);
                CalroieMapper.put(date, target);
                System.out.println("데이터 받기 !!!!!!!!!!!!!!!!!!!!");
                System.out.println("date :"+date);
                for(int i=0;i<foodList.size();i++){
                    System.out.println(foodList.get(i).getName());
                }
                System.out.println("맵데이터 저장 !!!!!!!!!!!!!!!!!!!!");
                for(int i=0;i<foodList.size();i++){
                    System.out.println(foodMapper.get(date).get(i).getName());
                }

            } else {   // RESULT_CANCEL
                Toast.makeText(Calendar.this, "Failed", Toast.LENGTH_SHORT).show();
            }
//
        }
    }

}