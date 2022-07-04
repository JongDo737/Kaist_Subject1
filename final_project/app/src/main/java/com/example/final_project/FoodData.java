package com.example.final_project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FoodData {
    String result;

    public ArrayList<FoodDataDto> getAPIData() throws Exception {
        String html = "https://api.odcloud.kr/api/15050912/v1/uddi:0a633058-9843-40fe-93d0-b568f23b715e_201909261047?" +
                "serviceKey=KoUHhFgcXAWFHvii7YKfxL2cdQMYE7j0dUoxZZXryPaJ9lz3HH463WOAopzv0XXAm66dHnxiUGjzj9Zk87ATCw%3D%3D&" +
                "page=1&" +
                "perPage=638";
        System.out.println("여기에요 여기 1111111111111111111");
        String key = "";

        StringBuilder urlBuilder = new StringBuilder(html); /* URL */
        System.out.println("urlBuilder : " + urlBuilder);

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        new Thread(() -> {
            try {
                BufferedReader bf;
                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    bf = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }
                result = bf.readLine();
                System.out.println("result : " + result);
            } catch (Exception e) {
                System.out.println("오류올오ㅠㄹㅇㄹ유로유로유로유로유로유류ㅗ");
            }
        }).start();
        Thread.sleep(1500);


        ArrayList<FoodDataDto> foodList = new ArrayList<>();


        System.out.println("여기에요 여기 444444444444444 " +
                result);

        try{
            // json 데이터만 뽑아서 ArrayList에 넣는 부분
            JSONObject jsonObject = new JSONObject(result);

            JSONArray foodArray = jsonObject.getJSONArray("data");

            for(int i=0; i<foodArray.length(); i++)
            {
                FoodDataDto foodDataDto = new FoodDataDto();
                JSONObject foodObject = foodArray.getJSONObject(i);

                // 이름
                foodDataDto.setName(foodObject.getString("음식명"));
                //칼로리  String to Float
                foodDataDto.setCalorie(Float.parseFloat(foodObject.getString("1인분칼로리(kcal)")));
                foodDataDto.setCarbohydrate(Float.parseFloat(foodObject.getString("탄수화물(g)")));
                foodDataDto.setProtein(Float.parseFloat(foodObject.getString("단백질(g)")));
                foodDataDto.setFat(Float.parseFloat(foodObject.getString("지방(g)")));

                foodList.add(foodDataDto);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("여기에요 여기 777777777777777");


        return foodList;

    }



}
