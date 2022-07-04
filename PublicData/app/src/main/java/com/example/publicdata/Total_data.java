package com.example.publicdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Total_data extends AppCompatActivity {

    //선 그래프
    private LineChart lineChart_calorie;
    private LineChart lineChart_carbohydrate;
    private LineChart lineChart_protein;
    private LineChart lineChart_fat;
    Map<String, ArrayList<FoodDataDto>> foodMapper = new HashMap<>();

    // String은 동적으로 할당하면됨.
    List<String> xAxisValues = new ArrayList<>(Arrays.asList("Jan", "Feb", "March", "April", "May", "June","July", "August", "September", "October", "November", "Decemeber"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_data);

        //인텐트로 데이터 받기
        Intent intent = getIntent();
        foodMapper = (Map<String, ArrayList<FoodDataDto>>) intent.getSerializableExtra("foodListByDate");
        System.out.println(foodMapper == null);
        Iterator<String> keys = foodMapper.keySet().iterator();
        while (keys.hasNext()){
            String key = keys.next();
            System.out.println(key);
            for (int i = 0;i < foodMapper.get(key).size();i++){
                System.out.println(foodMapper.get(key).get(i));
            }
        }



        ArrayList<Entry> entry_chart1 = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<Entry> entry_chart2 = new ArrayList<>();

        //꺾은선 그래프 xml 파일과 대응시키기
        lineChart_calorie = (LineChart) findViewById(R.id.calorie_chart);
        lineChart_carbohydrate = (LineChart) findViewById(R.id.carbohydrate_chart);
        lineChart_protein = (LineChart) findViewById(R.id.protein_chart);
        lineChart_fat = (LineChart) findViewById(R.id.fat_chart);

        LineData chartData = new LineData(); // 차트에 담길 데이터

        entry_chart1.add(new Entry(1, 1)); //entry_chart1에 좌표 데이터를 담는다.

        entry_chart1.add(new Entry(2, 2));
        entry_chart1.add(new Entry(3, 3));
        entry_chart1.add(new Entry(4, 4));
        entry_chart1.add(new Entry(5, 2));
        entry_chart1.add(new Entry(6, 8));

        entry_chart2.add(new Entry(1, 2)); //entry_chart2에 좌표 데이터를 담는다.
        entry_chart2.add(new Entry(2, 3));
        entry_chart2.add(new Entry(3, 1));
        entry_chart2.add(new Entry(4, 4));
        entry_chart2.add(new Entry(5, 5));
        entry_chart2.add(new Entry(6, 7));


        LineDataSet lineDataSet1 = new LineDataSet(entry_chart1, "LineGraph1"); // 데이터가 담긴 Arraylist 를 LineDataSet 으로 변환한다.
        LineDataSet lineDataSet2 = new LineDataSet(entry_chart2, "LineGraph2");

        lineDataSet1.setColor(Color.RED); // 해당 LineDataSet의 색 설정 :: 각 Line 과 관련된 세팅은 여기서 설정한다.
        lineDataSet2.setColor(Color.BLACK);

        XAxis xAxis = lineChart_calorie.getXAxis(); // x 축 설정
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x 축 표시에 대한 위치 설정
        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues)); //X축의 데이터를 제 가공함. new ChartXValueFormatter은 Custom한 소스    xAxis.setLabelCount(5, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌
        xAxis.setLabelCount(6, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌

        chartData.addDataSet(lineDataSet1); // 해당 LineDataSet 을 적용될 차트에 들어갈 DataSet 에 넣는다.
        chartData.addDataSet(lineDataSet2);

        lineChart_calorie.setData(chartData); // 차트에 위의 DataSet을 넣는다.

        lineChart_calorie.invalidate(); // 차트 업데이트
        lineChart_calorie.setTouchEnabled(false); // 차트 터치 disable

        lineChart_carbohydrate.setData(chartData); // 차트에 위의 DataSet을 넣는다.
        lineChart_carbohydrate.invalidate(); // 차트 업데이트
        lineChart_carbohydrate.setTouchEnabled(false); // 차트 터치 disable

        lineChart_protein.setData(chartData); // 차트에 위의 DataSet을 넣는다.
        lineChart_protein.invalidate(); // 차트 업데이트
        lineChart_protein.setTouchEnabled(false); // 차트 터치 disable

        lineChart_fat.setData(chartData); // 차트에 위의 DataSet을 넣는다.
        lineChart_fat.invalidate(); // 차트 업데이트
        lineChart_fat.setTouchEnabled(false); // 차트 터치 disable


    }
}