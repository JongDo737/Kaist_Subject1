# 햅삐 루피

![image](https://user-images.githubusercontent.com/92903481/177324695-42682d19-093c-475e-8b75-d6313ff14102.png)

## 개발 환경
 - OS: Android 11(minSdk: 21, targetSdk: 30)
 - 언어: Java
 - IDE: Android Studio
 - Target Device: Galaxy S10e
## 

## TAB 1 - 전화번호부

![image](https://user-images.githubusercontent.com/92903481/177322659-2b702e51-9894-4e32-8853-afa774027ab4.png)

### Major features

핸드폰 전화번호부로 받아온 연락처 데이터를 기준으로 연락처를 입력, 수정 할 수 있습니다.

입력 및 수정한 연락처는 핸드폰 주소록 데이터에 반영됩니다.

- Show contacts 버튼을 누르면 안드로이드 내에 있는 연락처의 정보를 불러와 listview로 보여줍니다.
- 사용자의 이름, 전화번호, 이메일을 입력하여 삽입 버튼을 누르면 입력한 이름, 전화번호, 이메일 데이터가 새 연락처 만들기 정보에 들어가게 됩니다.

```
private class CustomAdapter extends BaseAdapter {
    Context context;

    public CustomAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return information_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
```

### 기술 설명

   1.  `CustomAdapter`

- `BaseAdapter`을 상속 받는 `CustomAdapter`을 만들어 이를 listview에 적용하였습니다.
- 데이터 추가, 수정이 있을 때마다 `adapter.notifyDataSetChanged();`를 통해 화면에 나오는 listview를 수정해주었습니다.

```
private class CustomAdapter extends BaseAdapter {
    Context context;

    public CustomAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return information_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = View.inflate(context, R.layout.item, null);
        TextView nameItem = itemView.findViewById(R.id.nameItem);
        TextView numItem = itemView.findViewById(R.id.numItem);
        TextView emailItem = itemView.findViewById(R.id.emailItem);
        nameItem.setText(information_list.get(position).getName());
        numItem.setText(information_list.get(position).getNum());
        emailItem.setText(information_list.get(position).getEmail());
        return itemView;
    }
}
```

   2. listview

- listview를 누를 때 스크롤이 가능하도록 코드를 구현하였습니다.
- listview의 요소를 오래 눌렀을 때 연동된 연락처 데이터가 나오도록 하여 수정 및 삭제, 전화 등 기능이 가능하게 구현하였습니다.

   3. `ArrayList<information> information_list`

- 연락처의 정보를 담기 위해 information class를 만들고, 이의 attribute에 전화번호, 이름, 이메일 등 정보를 넣었습니다.
- 이러한 arraylist를 사용하여 데이터를 불러오고, 수정하였습니다.

```
package com.example.final_project;

import android.net.Uri;

public class information {
    private String name="";
    private String num="";
    private String key="";
    private String id = "";
    private long long_id = 0;
    private String email = "";
    private Uri url;

    public String getName(){
        return name;
    }
    public String getNum(){
        return num;
    }
    public String getKey(){ return key; }
    public String getId(){
        return id;
    }
    public String getEmail() {return email;}
    public Uri getUrl() {return url;}
    public long getLong_id() {return long_id;}

    public void setName(String args){
        name = args;
    }
    public void setNum(String args){
        num = args;
    }
    public void setKey(String args){
        key = args;
    }
    public void setId(String args){
        id = args;
    }
    public void setEmail(String args) {email = args;}
    public void setUrl(Uri args) {url = args;}
    public void setLong_id(long args) {long_id = args;}
}

```

## TAB 2 - 나만의 갤러리 (instagram)

![image](https://user-images.githubusercontent.com/92903481/177322567-6df37f4d-0c34-40c0-a1b5-d1e7e87dfa22.png)


### Major features

- 우측 상단의 더하기 버튼으로 로컬 저장소의 사진으로 사진을 업로드하고 제목을 작성할 수 있습니다.
- GridLayout을 통하여 인스타그램과 같은 레이아웃을 제공합니다.

### 기술 설명

   1.  로컬 저장소 갤러리 받아오기

- 우측 상단의 더하기 버튼으로 로컬 저장소로 들어가 사진을 클릭하면 사진데이터를 받아왔습니다.
- `startActivityForResult` 를 통하여 Intent 데이터(사진 경로, uri)를 쌍방향으로 주고 받아서 데이터를 저장하였습니다.

```
// 갤러리 오픈 버튼 클릭
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }
});
```

```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // Check which request we're responding to
    super.onActivityResult(requestCode, resultCode, data);
    GalleryDto galleryDto = new GalleryDto();

    // 갤러리 들어갔다가 나왔을때
    if (requestCode == 1) {
        // Make sure the request was successful
        if (resultCode ==RESULT_OK) {
            String imagePath = getRealPathFromURI(data.getData()); // path 경로
            ExifInterface exif = null;
            galleryDto.setPath(imagePath);
            galleryDto.setUri(data.getData());
            try {
                exif = new ExifInterface(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                galleryDto.setImg(img);
                galleryList.add(galleryDto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        myAdapter.notifyDataSetChanged();
        GridView gridView = (GridView) findViewById(R.id.grid_view);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(this));
    }
```

1. Gridview 사진 클릭 시 사진이 가지고 있는 여러가지 데이터를 표현해주기

```
/**
 * On Click event for Single Gridview Item
 * */
gridView.setOnItemClickListener(new OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {

        Intent in1 = new Intent(getApplicationContext(), FullImageActivity.class);
        GalleryDto galleryDto = galleryList.get(position);
        try {
            //Write file
            String filename = "bitmap.png";
            FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
            Bitmap bmp = galleryDto.getImg();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            //Pop intent
            in1.putExtra("image", filename);
            //Cleanup
            stream.close();
            //bmp.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        }
        in1.putExtra("is_heart", galleryDto.getIs_heart());
        in1.putExtra("position", position);
        in1.putExtra("title",galleryDto.getImgTitle());
        in1.putExtra("desc",galleryDto.getImgDes());
        in1.putExtra("uri",galleryDto.getUri());
        in1.putExtra("path",galleryDto.getPath());
        startActivityForResult(in1,200);
    }
});
```

- 사진 데이터를 Bitmap으로 intent에 넣어 전송 시 intent 크기 제한이 있었습니다. 이를 해결하기 위해 Bitmap 데이터를 FileoutputStream으로 변환하여 압축하고 압축한 파일 명을 intent에 보냈습니다.
- `startActivityForResult` 를 통하여 Intent 데이터를 쌍방향으로 주고 받아서 데이터를 저장하였습니다.

```
<GridView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/grid_view"
    android:layout_width="match_parent"
    android:layout_height="500dp"
    android:layout_weight="1"
    android:numColumns="3"
    android:columnWidth="100dp"
    android:horizontalSpacing="1dp"
    android:verticalSpacing="1dp"
    android:gravity="center"
    android:stretchMode="columnWidth" >
</GridView>
```

```java
GridView gridView = (GridView) findViewById(R.id.grid_view);
gridView.setOnItemClickListener(new OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {

        Intent in1 = new Intent(getApplicationContext(), FullImageActivity.class);
        GalleryDto galleryDto = galleryList.get(position);
        try {
            //Write file
            String filename = "bitmap.png";
            FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
            Bitmap bmp = galleryDto.getImg();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            //Pop intent
            in1.putExtra("image", filename);
            //Cleanup
            stream.close();
            //bmp.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        }
        in1.putExtra("position", position);
        in1.putExtra("text",galleryDto.getImgTitle());
        startActivityForResult(in1,200);
    }
});
```

## TAB 3 - 식단 관리 어플리케이션

![image](https://user-images.githubusercontent.com/92903481/177322473-5d440569-8ad2-445b-9bbb-043427626a0f.png)

### Major features

공공데이터로 받아온 음식 데이터를 기준으로 식단을 입력, 수정 할 수 있습니다.

- 하루 식단에 따른 탄수화물, 단백질, 지방 비율을 알 수 있고 목표 칼로리를 설정하고,
- 변경 및 식단 진행 상황을 progressBar를 통해서 알 수 있습니다.
- 날짜 별로 다른 식단을 비교하고 평소 식단의 단단지 비율, 각 영양소의 추이를 알 수 있습니다.

### 기술 설명

   1.  공공데이터

- 농림수산식품교육문화정보원에서 제공하는 칼로리 정보를 JSON 데이터로 받아왔습니다.
- 이름, 칼로리, 탄수화물, 단백질, 지방 데이터를 ArrayList<FoodDataDto>에 담았습니다.
- 데이터가 많아 데이터를 저장하는 동안 지루하지 않게 스플레시를 제작하여 넣었습니다.

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
