package com.jackrutorial.gallerytest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.FileInputStream;

public class FullImageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        // get intent data

        /*Intent i = getIntent();
        */

        // Selected image
        /*Bitmap img = (Bitmap) i.getExtras().get("IMG");
        */
        //ImageAdapter imageAdapter = new ImageAdapter(this);

        /*ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        */
        //imageView.setImageResource(imageAdapter.mThumbIds[position]);
        Intent intent = getIntent();
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
        /*
        String title = intent.getStringExtra("title");
        int position = intent.getExtras().getInt("title");

        EditText imgt = (EditText)findViewById(R.id.imgTitle);
        imgt.setText(title);
        Button commit = (Button) findViewById(R.id.commit);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_to_grid = new Intent(getApplicationContext(), AndroidGridLayoutActivity.class);
                intent_to_grid.putExtra("imgtitle", String.valueOf(imgt));
                intent_to_grid.putExtra("position", position);
                System.out.println("position전송전");
                System.out.println(position);
                startActivity(intent_to_grid);
                /*
                View parentRow = (View) view.getParent();
                ListView listView = (ListView) parentRow.getParent();
                String text = imgt.getText().toString();

                final int position = listView.getPositionForView(parentRow);
                galleryList.get(position).setImgTitle(text);


            }
        });
        */


        //byte[] arr = getIntent().getByteArrayExtra("image");
        //Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        ////ImageView BigImage = (ImageView)findViewById(R.id.BigImage);
        //ImageView imageView = (ImageView) findViewById(R.id.full_image_view);

        /*imageView.setImageBitmap(img);*/
    }

}