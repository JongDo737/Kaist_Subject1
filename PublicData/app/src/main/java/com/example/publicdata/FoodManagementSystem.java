package com.example.publicdata;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.Serializable;
import java.util.ArrayList;


public class FoodManagementSystem
        extends AppCompatActivity implements Serializable {
    PieChart pieChart;

    TextView nowCalorie;
    TextView wantCalorie;

    EditText foodName;
    EditText food_amount;

    ScrollView scrollView;

    Button btnAdd;
    Button commitBtn;

    AutoCompleteTextView autoCompleteTextView;

    int[] colorArray = new int[] {Color.LTGRAY, Color.BLUE, Color.RED};

    ArrayList<FoodDataDto> foodList = new ArrayList<FoodDataDto>();
    ArrayList<String> foodNameList = new ArrayList<>();
    ArrayList<FoodDataDto> todayTotalFoodList;
    String date;

    FoodDataDto displayFoodData = new FoodDataDto();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fms);
        setTitle("식단 관리 어플");
        // 스크롤뷰
        scrollView = findViewById(R.id.scrollView);

        commitBtn = findViewById(R.id.commitBtn);

        //인텐트로 데이터 받기
        Intent intent = getIntent();
        todayTotalFoodList = (ArrayList<FoodDataDto>)intent.getSerializableExtra("foodListByDate");
        date = intent.getStringExtra("Date");
        foodList = (ArrayList<FoodDataDto>)intent.getSerializableExtra("foodList");

        // 리스트 뷰 구성
        ListView listView = (ListView)findViewById(R.id.listView);
        final MyAdapter myAdapter = new MyAdapter(this,todayTotalFoodList);


        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        // 이름 리스트
        for(int i=0; i<foodList.size(); i++){
            foodNameList.add(foodList.get(i).getName());
        }

        listView.setAdapter(myAdapter);

        // 원형 그래프
        pieChart = findViewById(R.id.pieChart);

        PieDataSet pieDataSet = new PieDataSet(data1(displayFoodData), "오늘 먹은 음식 : "+displayFoodData.getName());
        pieDataSet.setColors(colorArray);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

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
                int foodAmount = 1;


                // 예외 처리
                // 에러 메시지 Toast로 띠워주기
                Context context = getApplicationContext();
                CharSequence text = "오류";
                int duration = Toast.LENGTH_SHORT;
                Toast toast;

                try{     // 숫자가 정상적으로 들어오게 되면
                    foodAmount = Integer.parseInt(food_amount.getText().toString());
                    if(foodSearchByName(searchName, foodList) != null){
                        FoodDataDto foodData = foodSearchByName(searchName, foodList);
                        //먹은 음식 추가해주기
                        foodAdd(foodData,foodAmount,myAdapter,todayTotalFoodList);
                    }
                    else  {
                        text = "음식 이름을 올바르게 입력해 주세요";
                        toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                }catch (NumberFormatException e){
                    // 숫자가 아닌게 들어오게 되면
                    text = "숫자를 입력해 주세요";
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });

        // 마지막 확인버튼
       commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent();
                // 데이터 전달
                intent2.putExtra("result", todayTotalFoodList);
                intent2.putExtra("Date", date);
                System.out.println("데이터 넘겨주기 !!!!!!!!!!!!!!!!!!!!!!!!!");
                for(int i=0;i<todayTotalFoodList.size();i++){
                    System.out.println(todayTotalFoodList.get(i).getName());
                }
                setResult(RESULT_OK, intent2);
                finish();
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
        boolean check = false;
        FoodDataDto foodData = new FoodDataDto();
        for(position=0; position<foodList.size();position++){
            if(foodList.get(position).getName().equals(name)){
                check = true;
                break;
            }
        }
        if(check){
            foodData = foodList.get(position);
            return foodData;
        }
        else{
            return null;
        }
    }

    public void foodAdd(FoodDataDto eatData, int foodAmount, MyAdapter myAdapter,ArrayList<FoodDataDto> todayTotalFoodList ){
       // float total
        // 인분 수만큼 데이터 추가
        for(int i=0;i < foodAmount; i++){
            todayTotalFoodList.add(eatData);
            displayFoodData.addName(todayTotalFoodList.get(todayTotalFoodList.size()-1).getName());
            displayFoodData.addCalorie(todayTotalFoodList.get(todayTotalFoodList.size()-1).getCalorie());
            displayFoodData.addCarbohydrate(todayTotalFoodList.get(todayTotalFoodList.size()-1).getCarbohydrate());
            displayFoodData.addProtein(todayTotalFoodList.get(todayTotalFoodList.size()-1).getProtein());
            displayFoodData.addFat(todayTotalFoodList.get(todayTotalFoodList.size()-1).getFat());
           }

        // 현재 칼로리 변경
        nowCalorie = findViewById(R.id.nowCalorie);
        nowCalorie.setText("현재 :  "+displayFoodData.getCalorie()+"Kcal");

        myAdapter.notifyDataSetChanged();

        // 파이데이터 변화주기
        pieChart = findViewById(R.id.pieChart);
        PieDataSet pieDataSet = new PieDataSet(data1(displayFoodData), "오늘 먹은 음식 : "+displayFoodData.getName());
        pieDataSet.setColors(colorArray);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }
    public void initFood(MyAdapter myAdapter,ArrayList<FoodDataDto> todayTotalFoodList ){
        // float total
        // 인분 수만큼 데이터 추가
//        for(int i=0;i < foodAmount; i++){
//            todayTotalFoodList.add(eatData);
//            displayFoodData.addName(todayTotalFoodList.get(todayTotalFoodList.size()-1).getName());
//            displayFoodData.addCalorie(todayTotalFoodList.get(todayTotalFoodList.size()-1).getCalorie());
//            displayFoodData.addCarbohydrate(todayTotalFoodList.get(todayTotalFoodList.size()-1).getCarbohydrate());
//            displayFoodData.addProtein(todayTotalFoodList.get(todayTotalFoodList.size()-1).getProtein());
//            displayFoodData.addFat(todayTotalFoodList.get(todayTotalFoodList.size()-1).getFat());
//        }

        // 현재 칼로리 변경
        nowCalorie = findViewById(R.id.nowCalorie);
        nowCalorie.setText("현재 :  "+displayFoodData.getCalorie()+"Kcal");

        myAdapter.notifyDataSetChanged();

        // 파이데이터 변화주기
        pieChart = findViewById(R.id.pieChart);
        PieDataSet pieDataSet = new PieDataSet(data1(displayFoodData), "오늘 먹은 음식 : "+displayFoodData.getName());
        pieDataSet.setColors(colorArray);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

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
