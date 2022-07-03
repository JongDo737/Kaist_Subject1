package com.example.publicdata;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

    EditText foodName;
    EditText food_amount;

    Button btnAdd;

    ListView listView;

    AutoCompleteTextView autoCompleteTextView;

    int[] colorArray = new int[] {Color.LTGRAY, Color.BLUE, Color.RED};

    ArrayList<FoodDataDto> foodList = new ArrayList<FoodDataDto>();
    ArrayList<String> foodNameList = new ArrayList<>();
    ArrayList<FoodDataDto> todayTotalFoodList = new ArrayList<>();

    FoodDataDto displayFoodData = new FoodDataDto();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 리스트 뷰 구성
        ListView listView = (ListView)findViewById(R.id.listView);
        final MyAdapter myAdapter = new MyAdapter(this,todayTotalFoodList);

        listView.setAdapter(myAdapter);

        // 음식 데이터 클래스 선언
        FoodData foodData = new FoodData();


        try {
            foodList = foodData.getAPIData();        //음식 데이터 Array 선언

        } catch (Exception e) {
            System.out.println("공공데이터 API 함수 호출 오류");
        }
        // 이름 리스트
        for(int i=0; i<foodList.size(); i++){
            foodNameList.add(foodList.get(i).getName());
        }
        // search test Data
        foodNameList.add("aaa");
        foodNameList.add("aaabbb");

        // 데이터
        foodAdd(foodSearchByName("햄버거",foodList),1,myAdapter);
        foodAdd(foodSearchByName("피자",foodList),1,myAdapter);
        foodAdd(foodSearchByName("햄버거",foodList),1,myAdapter);
        foodAdd(foodSearchByName("국밥",foodList),1,myAdapter);
        foodAdd(foodSearchByName("돼지고기가공품(등심햄)",foodList),1,myAdapter);

//        // 원형 그래프
//        pieChart = findViewById(R.id.pieChart);
//
//        PieDataSet pieDataSet = new PieDataSet(data1(todayTotalFood), "오늘 먹은 음식 : "+todayTotalFood.getName());
//        pieDataSet.setColors(colorArray);
//
//        PieData pieData = new PieData(pieDataSet);
//        pieChart.setData(pieData);
//        pieChart.invalidate();

        // 칼로리 TextView


        //검색하기 구현
        btnAdd = findViewById(R.id.btnAdd);
        // 인분
        food_amount = findViewById(R.id.food_amount);

        // 검색 창
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        // AutoCompleteTextView 에 아답터를 연결한다.
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, foodNameList ));

        // 확인 버튼을 눌렀을 때
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 검색이름 가져오기
                String searchName = autoCompleteTextView.getText().toString();
                int foodAmount = Integer.parseInt(food_amount.getText().toString());
                FoodDataDto foodData = foodSearchByName(searchName, foodList);

                //먹은 음식 추가해주기
                foodAdd(foodData,foodAmount,myAdapter);

            }
        });

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
    public FoodDataDto foodSearchByName(String name, ArrayList<FoodDataDto> foodList) {
        int position;
        FoodDataDto foodData;
        for(position=0; position<foodList.size();position++){
            if(foodList.get(position).getName().equals(name)){
                break;
            }
        }
        foodData = foodList.get(position);

        return foodData;
    }
    public void foodAdd(FoodDataDto eatData, int foodAmount, MyAdapter myAdapter){
       // float total
        // 인분 수만큼 데이터 추가
        for(int i=0;i < foodAmount; i++){
            todayTotalFoodList.add(eatData);
            System.out.println("여기에요 여기 !!!!!!!!! "+todayTotalFoodList.get(i).getName()+" "+todayTotalFoodList.get(i).getCalorie());
        }

        for(int i=0;i<todayTotalFoodList.size();i++){

            displayFoodData.addName(todayTotalFoodList.get(i).getName());
            displayFoodData.addCalorie(todayTotalFoodList.get(i).getCalorie());
            displayFoodData.addCarbohydrate(todayTotalFoodList.get(i).getCarbohydrate());
            displayFoodData.addProtein(todayTotalFoodList.get(i).getProtein());
            displayFoodData.addFat(todayTotalFoodList.get(i).getFat());
        }

        // 현재 칼로리 변경
        nowCalorie = findViewById(R.id.nowCalorie);
        nowCalorie.setText("현재 :  "+displayFoodData.getCalorie()+"Kcal");

        myAdapter.notifyDataSetChanged();

        // 파이데이터 변화주기


    }
    public class MyAdapter extends BaseAdapter {

        Context mContext = null;
        LayoutInflater mLayoutInflater = null;
        ArrayList<FoodDataDto> todayFoodList = new ArrayList<>();

        public MyAdapter(Context context, ArrayList<FoodDataDto> data) {
            mContext = context;
            todayFoodList = data;
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return todayFoodList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public FoodDataDto getItem(int position) {
            return todayFoodList.get(position);
        }

        @Override
        public View getView(int position, View converView, ViewGroup parent) {
            View view = mLayoutInflater.inflate(R.layout.food_list_view, null);

            TextView name = (TextView)view.findViewById(R.id.name);
            TextView calorie = (TextView)view.findViewById(R.id.calorie);
            TextView carbohydrate = (TextView)view.findViewById(R.id.carbohydrate);
            TextView protein = (TextView)view.findViewById(R.id.protein);
            TextView fat = (TextView)view.findViewById(R.id.fat);

            name.setText(todayFoodList.get(position).getName());
            calorie.setText(todayFoodList.get(position).getCalorie()+"");
            carbohydrate.setText(todayFoodList.get(position).getCarbohydrate()+"");
            protein.setText(todayFoodList.get(position).getProtein()+"");
            fat.setText(todayFoodList.get(position).getFat()+"");

            return view;
        }
    }
}
