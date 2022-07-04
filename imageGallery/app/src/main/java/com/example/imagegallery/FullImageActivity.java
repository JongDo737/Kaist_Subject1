package com.example.imagegallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class FullImageActivity extends AppCompatActivity {

    private ImageView image;
    private TextView imgTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        image = findViewById(R.id.image);
        imgTitle = findViewById(R.id.imgTitle);


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        image.setImageBitmap(StringToBitmap(intent.getStringExtra("image")));
        imgTitle.setText(intent.getStringExtra("imgTitle"));


    }
    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}