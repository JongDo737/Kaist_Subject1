package com.example.imagegallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap image;
        image = BitmapFactory.decodeResource(getResources(), R.drawable.good);
        // ListView에 Adapter 설정
        Random rnd = new Random();
        Button button;              // gallery Open Btn

        // Dto 생성
        GalleryDto galleryDto = new GalleryDto();

        // 데이터 생성
        ArrayList<GalleryDto> galleryList = new ArrayList<>();

        for(int i=0; i<10; i++){
            galleryDto.setImg(image);
            galleryDto.setImgTitle("제목 : "+ rnd.nextInt(10000));
            galleryList.add(galleryDto);
        }
        // 어답터 생성
        CustomAdapter myAdapter = new CustomAdapter(this,0,galleryList);

        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(myAdapter);



//        button = (Button)findViewById(R.id.button);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, 1);
//                System.out.println("여기요 여기 !");
//            }
//        });
//
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Check which request we're responding to
//        super.onActivityResult(requestCode, resultCode, data);
//        System.out.println("여기요 여기 222222");
//
//        if (requestCode == 1) {
//            // Make sure the request was successful
//            if (resultCode == RESULT_OK) {
//                try {
//                    // 선택한 이미지에서 비트맵 생성
//                    InputStream in = getContentResolver().openInputStream(data.getData());
//                    Bitmap img = BitmapFactory.decodeStream(in);
//                    in.close();
//                    // 이미지 표시
//                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA");
//                    System.out.println(img);
//                    galleryDto.setImg(img);
//                    galleryDto.setImgTitle("안녕하세요");
//                    galleryList.add(galleryDto);
//                    System.out.println("여기요 여기 333333");
//                    for(int i=0; i<galleryList.size();i++){
//                        System.out.println(galleryList.get(i).getImg());
//                    }
//                    //myAdapter.notifyDataSetChanged();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    // 커스텀 어답터
   class CustomAdapter extends ArrayAdapter<GalleryDto> {

        private LayoutInflater mLayoutInflater;
        public CustomAdapter(@NonNull Context context, int resource, ArrayList<GalleryDto> galleryList ) {
            super(context, resource,galleryList);
            mLayoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 특정 행(position)의 데이터를 구함
            GalleryDto item = (GalleryDto) getItem(position);

            // 같은 행에 표시시킨 View는 재사용되기 때문에 첫 회만 생성
            if (null == convertView) {
                convertView = mLayoutInflater.inflate(R.layout.gallery, null);
            }


            ImageView image = (ImageView)convertView.findViewById(R.id.image) ;
            image.setImageBitmap(item.getImg());

            EditText imgTitle = (EditText)convertView.findViewById(R.id.imgTitle);
            imgTitle.setText(item.getImgTitle());

            return convertView;
        }
    }
}