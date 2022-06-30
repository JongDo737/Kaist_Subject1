package com.example.imagegallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button;              // gallery Open Btn
    GalleryDto galleryDto = new GalleryDto();
    ArrayList<GalleryDto> galleryList = new ArrayList<>();
    CustomAdapter myAdapter = new CustomAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.listView);

        list.setAdapter(myAdapter);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
                System.out.println("여기요 여기 !");
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("여기요 여기 222222");

        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA");
                    System.out.println(img);
                    galleryDto.setImg(img);
                    galleryDto.setImgTitle("안녕하세요");
                    galleryList.add(galleryDto);
                    System.out.println("여기요 여기 333333");
                    for(int i=0; i<galleryList.size();i++){
                        System.out.println(galleryList.get(i).getImg());
                    }
                    myAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private class CustomAdapter extends BaseAdapter {
        Context context;
        GalleryDto galleryDto = new GalleryDto();
        ArrayList<GalleryDto> galleryList = new ArrayList<>();

        public CustomAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return galleryList.size();
        }

        @Override
        public Object getItem(int i) {
            return galleryList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = View.inflate(context, R.layout.gallery, null);

            ImageView image = (ImageView)itemView.findViewById(R.id.image) ;
            EditText imgTitle = (EditText)itemView.findViewById(R.id.imgTitle);
            Bitmap bm = galleryList.get(position).getImg();

            Bitmap resize = Bitmap.createScaledBitmap(bm, 30, 40,true);

            image.setImageBitmap( resize ) ;
            //image.setImageBitmap(galleryList.get(position).getImg());
            imgTitle.setText(galleryList.get(position).imgTitle);

            return itemView;
        }
    }
}