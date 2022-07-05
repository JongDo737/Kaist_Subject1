# Kaist_Subject1

# 카이스트 공동과제1

## TAB 3 - 식단 관리 어플리케이션

사진

### Major features

공공데이터로 받아온 음식 데이터를 기준으로 식단을 입력, 수정 할 수 있습니다.

- 하루 식단에 따른 탄수화물, 단백질, 지방 비율을 알 수 있고 목표 칼로리를 설정하고,
- 변경 및 식단 진행 상황을 progressBar를 통해서 알 수 있습니다.
- 날짜 별로 다른 식단을 비교하고 평소 식단의 단단지 비율, 각 영양소의 추이를 알 수 있습니다.

### 기술 설명

   1.  공공데이터

- 농림수산식품교육문화정보원에서 제공하는 칼로리 정보를 JSON 데이터로 받아왔습니다.
- 이름, 칼로리, 탄수화물, 단백질, 지방 데이터를 ArrayList<FoodDataDto>에 담았습니다.

```java
String html = "https://api.odcloud.kr/api/15050912/v1/uddi:0a633058-9843-40fe-93d0-b568f23b715e_201909261047?" +
                "serviceKey=KoUHhFgcXAWFHvii7YKfxL2cdQMYE7j0dUoxZZXryPaJ9lz3HH463WOAopzv0XXAm66dHnxiUGjzj9Zk87ATCw%3D%3D&" +
                "page=1&" +
                "perPage=638";
URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

ArrayList<FoodDataDto> foodList = new ArrayList<>();
```

   2. 날짜 데이터

- CalenderView로 달력을 구현하였습니다.
- setDate() 와 setMaxDate() 함수로 오늘 날짜를 기준으로 달력을 만들었고 이후의 날짜는 선택할 수 없게 만들었습니다.
- HashMap을 통하여 key : 날짜 , value : 식단 데이터를 구축하였습니다.
- 날짜를 클릭하게 되면 FMS( Food Management System)으로 해당 날짜의 식단 데이터를 FMS로 보내주게 됩니다. 없다면 새로 생성합니다.

```java
CalendarView calendarView;
calendarView = (CalendarView) findViewById(R.id.calendarView);
calendarView.setDate(System.currentTimeMillis(),false,true);
// 현재 날짜까지 표시
calendarView.setMaxDate(System.currentTimeMillis());
```

```java
// 날짜에 대한 데이터가 있다면
if(foodMapper.containsKey(selectDate)){
    intent.putExtra("foodListByDate",foodMapper.get(selectDate));
    intent.putExtra("calorie_target",CalroieMapper.get(selectDate));

  
}else{
// 날짜에 대한 식단 데이터가 없다면 (처음 생성)
    // 데이터 생성
    ArrayList<FoodDataDto> foodListByDate = new ArrayList<>();
    foodMapper.put(selectDate,foodListByDate);
    CalroieMapper.put(selectDate,2500);
    // 데이터 전달
    intent.putExtra("foodListByDate", foodMapper.get((String)selectDate));
    intent.putExtra("calorie_target",CalroieMapper.get(selectDate));
}
startActivityForResult(intent, 0);
```

   3. 식단 데이터를 통한 데이터 표현

- ProgressBar를 통한 식단 진행 상황 표시
- PieChart를 통해서 탄단지 비율 확인
- LineChart를 통한 날짜 별 영양소 변화 추이 표시