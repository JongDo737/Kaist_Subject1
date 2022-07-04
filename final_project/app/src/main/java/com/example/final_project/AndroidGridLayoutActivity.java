package com.example.final_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class AndroidGridLayoutActivity extends Activity {

    public ImageAdapter myAdapter = new ImageAdapter(this);

    public ArrayList<GalleryDto> galleryList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);
        Intent intent = getIntent();
        String imgtitle = "";
        int position = -1;
        imgtitle = intent.getStringExtra("imgtitle");
        //position = intent.getExtras().getInt("position");
        position = intent.getIntExtra("position", -1);
        System.out.println("position드갑니다");
        System.out.println(position);
        Button button;              // gallery Open Btn
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {      // 갤러리 오픈 버튼 클릭
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
                System.out.println("여기요 여기 !");

            }
        });

        GridView gridView = (GridView) findViewById(R.id.grid_view);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(this));

        /**
         * On Click event for Single Gridview Item
         * */
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Sending image id to FullScreenActivity
                //Intent i = new Intent(getApplicationContext(), FullImageActivity.class);

                //Bitmap sendBitmap = galleryList.get(position).getImg();
                Intent in1 = new Intent(getApplicationContext(), FullImageActivity.class);
                GalleryDto obj = galleryList.get(position);
                try {
                    //Write file
                    String filename = "bitmap.png";
                    FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
                    Bitmap bmp = obj.getImg();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);


                    //Pop intent
                    in1.putExtra("image", filename);
                    //Cleanup
                    stream.close();
                    //bmp.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                in1.putExtra("title", obj.getImgTitle());
                in1.putExtra("position", position);
                startActivity(in1);
                //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                //byte[] byteArray = stream.toByteArray();
                //i.putExtra("image",byteArray);


                //i.putExtra("IMG", galleryList.get(position).getImg());
                //startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("여기요 여기 222222");
        GalleryDto galleryDto = new GalleryDto();
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
                    galleryList.add(galleryDto);
                    //myAdapter.addGalleryDto(galleryDto);
                    //galleryList.add(galleryDto);
                    System.out.println("여기요 여기 333333");
                    //for(int i=0; i<myAdapter.galleryList_size();i++){
                    //    System.out.println(myAdapter.getGalleryDto(i).getImg());
                    //}

                    for(int i=0; i<galleryList.size();i++){
                        System.out.println(galleryList.get(i).getImg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("여기요 여기 4444444444444444444444444");
        myAdapter.notifyDataSetChanged();
        GridView gridView = (GridView) findViewById(R.id.grid_view);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(this));
        System.out.println("여기요 여기 55555555555555555555555");
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;


        /*
        // Keep all Images in array
        public Integer[] mThumbIds = {
                R.drawable.pic_1, R.drawable.pic_2,
                R.drawable.pic_3, R.drawable.pic_4,
                R.drawable.pic_5, R.drawable.pic_6,
                R.drawable.pic_7, R.drawable.pic_8,
                R.drawable.pic_9, R.drawable.pic_10,
                R.drawable.pic_11, R.drawable.pic_12,
                R.drawable.pic_13, R.drawable.pic_14,
                R.drawable.pic_15
        };
        */
        // Constructor
        public ImageAdapter(Context c){
            mContext = c;
        }

        /*
        @Override
        public int getCount() {
            return mThumbIds.length;
        }
        */
        @Override
        public int getCount() {
            System.out.println("galleryList.size()=================");
            System.out.println(galleryList.size());
            return galleryList.size();
        }


    /*
    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }
    */

        @Override
        public Object getItem(int position) {
            return galleryList.get(position).getImg();
        }


        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        /*
        // 특정 행(position)의 데이터를 구함
        GalleryDto item = (GalleryDto) getItem(position);

        // 같은 행에 표시시킨 View는 재사용되기 때문에 첫 회만 생성
        galleryList.get(position);
        */
            System.out.println("여기에요여기에요여기에요여기에요여기에요여기에요여기에요여기에요여기에요여기에요여기에요여기에요여기에요여기에요여기에요");
        /*
        for(int i=0; i<galleryList.size();i++){
            System.out.println("7777777777775555555555555555555555898");
            System.out.println(galleryList.get(i).getImg());
        }
         */

            ImageView imageView = new ImageView(mContext);
            System.out.println("11111111111111111111111");
            if (position < galleryList.size()){
                imageView.setImageBitmap(galleryList.get(position).getImg());
            }

            //imageView.setImageBitmap(galleryList.get(position).getImg());
            //imageView.setImageResource(mThumbIds[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            return imageView;
        }

    }
}