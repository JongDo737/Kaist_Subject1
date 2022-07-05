package com.example.final_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Total_data extends AppCompatActivity implements Serializable {

    //선 그래프
    PieChart pieChart;
    private LineChart lineChart_summary;
    private LineChart lineChart_calorie;
    private LineChart lineChart_carbohydrate;
    private LineChart lineChart_protein;
    private LineChart lineChart_fat;
    FoodDataDto todayTotalFood = new FoodDataDto();
    Map<String, ArrayList<FoodDataDto>> foodMapper = new HashMap<>();
    Map<String, Integer> CalroieMapper = new HashMap<>();

    int[] colorArray = new int[] {Color.LTGRAY, Color.BLUE, Color.RED};

    // intent로 받은 date
    List<String> date = new ArrayList<>();

    private float total_calorie = 0.0f;
    private float total_carbohydrate = 0.0f;
    private float total_protein = 0.0f;
    private float total_fat = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //인텐트로 데이터 받기
        Intent intent = getIntent();
        foodMapper = (Map<String, ArrayList<FoodDataDto>>) intent.getSerializableExtra("Mapper");
        CalroieMapper = (Map<String, Integer>) intent.getSerializableExtra("calorie_mapper");
        System.out.println(foodMapper == null);
        Iterator<String> keys = foodMapper.keySet().iterator();
        while (keys.hasNext()){
            String key = keys.next();
            date.add(key);
        }
        if (date.size()==0){
            setContentView(R.layout.no_data);
            return;
        }else{
            setContentView(R.layout.activity_total_data);
        }

        Collections.sort(date);

        ArrayList<Entry> entry_chart_calorie = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<Entry> entry_chart_carbohydrate = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<Entry> entry_chart_protein = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<Entry> entry_chart_fat = new ArrayList<>(); // 데이터를 담을 Arraylist

        ArrayList<Entry> target_calorie = new ArrayList<>();
        ArrayList<Entry> avg_calorie = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<Entry> avg_carbohydrate = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<Entry> avg_protein = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<Entry> avg_fat = new ArrayList<>(); // 데이터를 담을 Arraylist

        int num_dates = date.size();
        for (int i=0;i<date.size();i++){
            String key = date.get(i);
            ArrayList<FoodDataDto> foodList = foodMapper.get(key);
            int target = CalroieMapper.get(key);
            target_calorie.add(new Entry((float) i, target));
            float calorie = 0; float carbohydrate = 0;
            float protein = 0; float fat = 0;
            for (int k = 0;k<foodList.size();k++){
                calorie += foodList.get(k).getCalorie();
                carbohydrate += foodList.get(k).getCarbohydrate();
                protein += foodList.get(k).getProtein();
                fat += foodList.get(k).getFat();
            }
            total_calorie += calorie;
            total_carbohydrate += carbohydrate;
            total_protein += protein;
            total_fat += fat;

            todayTotalFood.addCalorie(calorie);
            todayTotalFood.addCarbohydrate(carbohydrate);
            todayTotalFood.addFat(protein);
            todayTotalFood.addProtein(fat);

            entry_chart_calorie.add(new Entry((float) i, calorie));
            entry_chart_carbohydrate.add(new Entry((float) i, carbohydrate));
            entry_chart_protein.add(new Entry((float) i, protein));
            entry_chart_fat.add(new Entry((float) i, fat));
        }

        // 평군값 계산하기
        float avg_calorie_f = total_calorie / date.size();
        float avg_carbohydrate_f = total_carbohydrate / date.size();
        float avg_protein_f = total_protein / date.size();
        float avg_fat_f = total_fat / date.size();

        for (int i=0;i<date.size();i++){
            avg_calorie.add(new Entry((float) i, avg_calorie_f));
            avg_carbohydrate.add(new Entry((float) i, avg_carbohydrate_f));
            avg_protein.add(new Entry((float) i, avg_protein_f));
            avg_fat.add(new Entry((float) i, avg_fat_f));
        }

        ArrayList<Entry> entry_chart1 = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<Entry> entry_chart2 = new ArrayList<>();

        // 원형 그래프
        pieChart = findViewById(R.id.pieChart_summary);

        PieDataSet pieDataSet = new PieDataSet(data1(todayTotalFood), "총 식사 탄,단,지 비율");
        pieDataSet.setColors(colorArray);
        pieDataSet.setValueTextSize(15f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        //꺾은선 그래프 xml 파일과 대응시키기
        lineChart_summary = (LineChart) findViewById(R.id.summary_chart);
        lineChart_calorie = (LineChart) findViewById(R.id.calorie_chart);
        lineChart_carbohydrate = (LineChart) findViewById(R.id.carbohydrate_chart);
        lineChart_protein = (LineChart) findViewById(R.id.protein_chart);
        lineChart_fat = (LineChart) findViewById(R.id.fat_chart);

        LineData chartData = new LineData(); // 차트에 담길 데이터

        // 데이터가 담긴 Arraylist 를 LineDataSet 으로 변환한다.
        LineDataSet lineDataSet_calorie = new LineDataSet(entry_chart_calorie, "칼로리");
        LineDataSet lineDataSet_carbohydrate = new LineDataSet(entry_chart_carbohydrate, "탄수화물");
        LineDataSet lineDataSet_protein = new LineDataSet(entry_chart_protein, "단백질");
        LineDataSet lineDataSet_fat = new LineDataSet(entry_chart_fat, "지방");

        //목표값
        LineDataSet lineDataSet_calorie_target = new LineDataSet(target_calorie, "칼로리 목표값");
        //평균값들
        LineDataSet lineDataSet_calorie_avg = new LineDataSet(avg_calorie, "칼로리 평균값");
        LineDataSet lineDataSet_carbohydrate_avg = new LineDataSet(avg_carbohydrate, "탄수화물 평균값");
        LineDataSet lineDataSet_protein_avg = new LineDataSet(avg_protein, "단백질 평균값");
        LineDataSet lineDataSet_fat_avg = new LineDataSet(avg_fat, "지방 평균값");

        // 차트에 담길 데이터
        LineData chartData_summary = new LineData();
        LineData chartData_calorie = new LineData();
        LineData chartData_carbohydrate = new LineData();
        LineData chartData_protein = new LineData();
        LineData chartData_fat = new LineData();

        //Summary data
        chartData_summary.addDataSet(lineDataSet_carbohydrate);
        chartData_summary.addDataSet(lineDataSet_protein);
        chartData_summary.addDataSet(lineDataSet_fat);

        // 탄단지에 대한 값, 평균값을 그려줌.
        chartData_calorie.addDataSet(lineDataSet_calorie);
        chartData_calorie.addDataSet(lineDataSet_calorie_avg);
        chartData_calorie.addDataSet(lineDataSet_calorie_target);

        chartData_carbohydrate.addDataSet(lineDataSet_carbohydrate);
        chartData_carbohydrate.addDataSet(lineDataSet_carbohydrate_avg);

        chartData_protein.addDataSet(lineDataSet_protein);
        chartData_protein.addDataSet(lineDataSet_protein_avg);

        chartData_fat.addDataSet(lineDataSet_fat);
        chartData_fat.addDataSet(lineDataSet_fat_avg);

        // 해당 LineDataSet의 색 설정 :: 각 Line 과 관련된 세팅은 여기서 설정한다.
        lineDataSet_calorie.setColor(ContextCompat.getColor(this, R.color.grey_good));
        lineDataSet_carbohydrate.setColor(ContextCompat.getColor(this, R.color.red_good));
        lineDataSet_protein.setColor(ContextCompat.getColor(this, R.color.blue_good));
        lineDataSet_fat.setColor(ContextCompat.getColor(this, R.color.orange_good));
        lineDataSet_calorie_target.setColor(ContextCompat.getColor(this, R.color.rupee));

        // 평균값 색깔
        lineDataSet_calorie_avg.setColor(ContextCompat.getColor(this, R.color.green_good));
        lineDataSet_carbohydrate_avg.setColor(ContextCompat.getColor(this, R.color.green_good));
        lineDataSet_protein_avg.setColor(ContextCompat.getColor(this, R.color.green_good));
        lineDataSet_fat_avg.setColor(ContextCompat.getColor(this, R.color.green_good));

        // 선 굵기 설정
        lineDataSet_calorie.setLineWidth(2);
        lineDataSet_carbohydrate.setLineWidth(2);
        lineDataSet_protein.setLineWidth(2);
        lineDataSet_fat.setLineWidth(2);
        lineDataSet_calorie_target.setLineWidth(3);

        lineDataSet_calorie_avg.setLineWidth(5);
        lineDataSet_carbohydrate_avg.setLineWidth(5);
        lineDataSet_protein_avg.setLineWidth(5);
        lineDataSet_fat_avg.setLineWidth(5);

        LineDataSet lineDataSet1 = new LineDataSet(entry_chart1, "LineGraph1"); // 데이터가 담긴 Arraylist 를 LineDataSet 으로 변환한다.
        LineDataSet lineDataSet2 = new LineDataSet(entry_chart2, "LineGraph2");

        chartData.addDataSet(lineDataSet1); // 해당 LineDataSet 을 적용될 차트에 들어갈 DataSet 에 넣는다.
        chartData.addDataSet(lineDataSet2);

        lineChart_summary.setData(chartData_summary); // 차트에 위의 DataSet을 넣는다.
        lineChart_summary.invalidate(); // 차트 업데이트
        lineChart_summary.setTouchEnabled(false); // 차트 터치 disable

        lineChart_calorie.setData(chartData_calorie); // 차트에 위의 DataSet을 넣는다.
        lineChart_calorie.invalidate(); // 차트 업데이트
        lineChart_calorie.setTouchEnabled(false); // 차트 터치 disable

        lineChart_carbohydrate.setData(chartData_carbohydrate); // 차트에 위의 DataSet을 넣는다.
        lineChart_carbohydrate.invalidate(); // 차트 업데이트
        lineChart_carbohydrate.setTouchEnabled(false); // 차트 터치 disable

        lineChart_protein.setData(chartData_protein); // 차트에 위의 DataSet을 넣는다.
        lineChart_protein.invalidate(); // 차트 업데이트
        lineChart_protein.setTouchEnabled(false); // 차트 터치 disable

        lineChart_fat.setData(chartData_fat); // 차트에 위의 DataSet을 넣는다.
        lineChart_fat.invalidate(); // 차트 업데이트
        lineChart_fat.setTouchEnabled(false); // 차트 터치 disable

        XAxis xAxis_summary = lineChart_summary.getXAxis(); // x 축 설정
        xAxis_summary.setPosition(XAxis.XAxisPosition.BOTTOM); //x 축 표시에 대한 위치 설정
        xAxis_summary.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(date)); //X축의 데이터를 제 가공함. new ChartXValueFormatter은 Custom한 소스    xAxis.setLabelCount(5, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌
        xAxis_summary.setLabelCount(num_dates, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌

        XAxis xAxis_calorie = lineChart_calorie.getXAxis(); // x 축 설정
        xAxis_calorie.setPosition(XAxis.XAxisPosition.BOTTOM); //x 축 표시에 대한 위치 설정
        xAxis_calorie.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(date)); //X축의 데이터를 제 가공함. new ChartXValueFormatter은 Custom한 소스    xAxis.setLabelCount(5, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌
        xAxis_calorie.setLabelCount(num_dates, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌

        XAxis xAxis_carbohydrate = lineChart_carbohydrate.getXAxis(); // x 축 설정
        xAxis_carbohydrate.setPosition(XAxis.XAxisPosition.BOTTOM); //x 축 표시에 대한 위치 설정
        xAxis_carbohydrate.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(date)); //X축의 데이터를 제 가공함. new ChartXValueFormatter은 Custom한 소스    xAxis.setLabelCount(5, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌
        xAxis_carbohydrate.setLabelCount(num_dates, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌

        XAxis xAxis_protein = lineChart_protein.getXAxis(); // x 축 설정
        xAxis_protein.setPosition(XAxis.XAxisPosition.BOTTOM); //x 축 표시에 대한 위치 설정
        xAxis_protein.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(date)); //X축의 데이터를 제 가공함. new ChartXValueFormatter은 Custom한 소스    xAxis.setLabelCount(5, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌
        xAxis_protein.setLabelCount(num_dates, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌

        XAxis xAxis_fat = lineChart_fat.getXAxis(); // x 축 설정
        xAxis_fat.setPosition(XAxis.XAxisPosition.BOTTOM); //x 축 표시에 대한 위치 설정
        xAxis_fat.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(date)); //X축의 데이터를 제 가공함. new ChartXValueFormatter은 Custom한 소스    xAxis.setLabelCount(5, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌
        xAxis_fat.setLabelCount(num_dates, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌

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
}