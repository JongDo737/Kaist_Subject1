package com.example.publicdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;

public class Calendar extends AppCompatActivity {
    CalendarView calendarView;
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
    }
}