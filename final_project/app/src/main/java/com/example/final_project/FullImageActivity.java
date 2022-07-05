package com.example.final_project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.Serializable;

public class FullImageActivity extends Activity implements Serializable {
    EditText textView3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        textView3 = findViewById(R.id.textView3);
        textView3.setBackground(null);

        GalleryDto galleryDto = new GalleryDto();
        Intent intent = getIntent();
        galleryDto = (GalleryDto)intent.getSerializableExtra("galleryDto");
        String positionStr = intent.getStringExtra("position");



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

    }

}