package com.example.publicdata;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.renderer.PieChartRenderer;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity
        extends AppCompatActivity {
    PieChart pieChart;
    TextView nowCalorie;
    TextView wantCalorie;

    int[] colorArray = new int[] {Color.LTGRAY, Color.BLUE, Color.RED};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 음식 데이터
        FoodData foodData = new FoodData();
        ArrayList<FoodDataDto> foodList = new ArrayList<FoodDataDto>();
        try {
            foodList = foodData.getAPIData();        //음식 데이터 Array 선언

        } catch (Exception e) {
            System.out.println("공공데이터 API 함수 호출 오류");
        }

        FoodDataDto morning = new FoodDataDto();

        // 햄버거 검색했을 때
        String morningSearch = "햄버거";
        int hambergerPosition = foodSearchByName(morningSearch, foodList);
        morning = foodList.get(hambergerPosition);

        FoodDataDto lunch = new FoodDataDto();
        String lunchSearch ="피자";
        int pizzaPosition = foodSearchByName(lunchSearch,foodList);
        lunch = foodList.get(pizzaPosition);

        FoodDataDto dinner = new FoodDataDto();
        String dinnerSearch ="국밥";
       int gookbabPosition = foodSearchByName(dinnerSearch,foodList);
        dinner = foodList.get(gookbabPosition);

        FoodDataDto todayTotalFood = new FoodDataDto();
        todayTotalFood.setCalorie(morning.getCalorie()+lunch.getCalorie()+dinner.getCalorie());
        todayTotalFood.setName(morning.getName()+", "+lunch.getName()+", "+dinner.getName());
        todayTotalFood.setCarbohydrate(morning.getCarbohydrate()+lunch.getCarbohydrate()+dinner.getCarbohydrate());
        todayTotalFood.setProtein(morning.getProtein()+lunch.getProtein()+dinner.getProtein());
        todayTotalFood.setFat(morning.getFat()+lunch.getFat()+dinner.getFat());


        // 원형 그래프
        pieChart = findViewById(R.id.pieChart);

        PieDataSet pieDataSet = new PieDataSet(data1(todayTotalFood), "오늘 먹은 음식 : "+todayTotalFood.getName());
        pieDataSet.setColors(colorArray);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        // 칼로리 TextView
        nowCalorie = findViewById(R.id.nowCalorie);
        nowCalorie.setText("현재 :  "+todayTotalFood.getCalorie()+"Kcal");



    }

    // 원형함수 데이터 셋
    private ArrayList<PieEntry> data1(FoodDataDto todayTotalFood){
        ArrayList<PieEntry> datavalue = new ArrayList<>();

        // 아침에 먹은 햄버거 데이터     --> String to Float
        datavalue.add(new PieEntry(todayTotalFood.getCarbohydrate(), "탄수화물"));
        datavalue.add(new PieEntry(todayTotalFood.getProtein(), "단백질"));
        datavalue.add(new PieEntry(todayTotalFood.getFat(), "지방"));
        return datavalue;
    }

    // 이름을 검색해서 포지션 검색
    public int foodSearchByName(String name, ArrayList<FoodDataDto> foodList) {
        int position;
        for(position=0; position<foodList.size();position++){
            if(foodList.get(position).getName().equals(name)){
                break;
            }
        }
        return position;
    }

}
