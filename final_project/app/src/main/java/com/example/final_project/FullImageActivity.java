package com.example.final_project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.Serializable;

public class FullImageActivity extends Activity implements Serializable {
    EditText textView3;
    ImageView kakao_send;
    ImageView heart;
    boolean is_heart = false;
    Button commitBtn;
    TextView DESC;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        textView3 = findViewById(R.id.textView3);
        textView3.setBackground(null);
        kakao_send = findViewById(R.id.kakao_talk);
        heart = findViewById(R.id.heart);
        commitBtn = findViewById(R.id.commit);

        GalleryDto galleryDto = new GalleryDto();
        Intent intent = getIntent();
        galleryDto = (GalleryDto)intent.getSerializableExtra("galleryDto");
        int position = intent.getIntExtra("position", 0);
        String path = intent.getStringExtra("path");
        is_heart = intent.getBooleanExtra("is_heart", false);
        String title =intent.getStringExtra("title");
        String desc =intent.getStringExtra("desc");




        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        Bitmap bmp = null;
        String filename = intent.getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bmp);

        //하트 누를 때
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_heart = !is_heart;
                if (is_heart){
                    heart.setImageResource(R.drawable.redheart);
                } else{
                    heart.setImageResource(R.drawable.heart);
                }
            }
        });

        //메시지 보내기 버튼
        kakao_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse(path);	// android image path
                System.out.println(path);
                System.out.println(screenshotUri);
                System.out.println("sadfsdfasdfzxcvzxcvasdfasdf");
                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri.toString());
                startActivity(Intent.createChooser(sharingIntent, "Share image using")); // 변경가능
            }
        });

        // 마지막 확인버튼
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String final_desc = textView3.getText().toString();
                Intent intent2 = new Intent();
                // 데이터 전달
                intent2.putExtra("position", position);
                intent2.putExtra("is_heart", is_heart);
                intent2.putExtra("title", title);
                intent2.putExtra("desc", final_desc);;
                setResult(RESULT_OK, intent2);
                finish();
            }
        });
    }

}