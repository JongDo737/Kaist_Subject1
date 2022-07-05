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
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class AndroidGridLayoutActivity extends Activity implements Serializable {

    public ImageAdapter myAdapter = new ImageAdapter(this);

    public ArrayList<GalleryDto> galleryList = new ArrayList<>();

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);


        // gallery Open Btn
        button = (Button)findViewById(R.id.button);

        // 갤러리 오픈 버튼 클릭
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
                System.out.println("전송 !!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("position : "+position);
                startActivityForResult(in1,200);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        GalleryDto galleryDto = new GalleryDto();

        // 갤러리 들어갔다가 나왔을때
        if (requestCode == 1) {

            System.out.println("open 버튼 눌렀을 때 !!!!!!!!! ");
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    galleryDto.setImg(img);
                    galleryList.add(galleryDto);
                    System.out.println("여기요 여기 333333");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("여기요 여기 4444444444444444444444444");
            myAdapter.notifyDataSetChanged();
            GridView gridView = (GridView) findViewById(R.id.grid_view);

            // Instance of ImageAdapter Class
            gridView.setAdapter(new ImageAdapter(this));
            System.out.println("여기요 여기 55555555555555555555555");
        }

        // fullImage 후
        else if(requestCode == 200) {
            if (resultCode == RESULT_OK) {      // 데이터 받기 성공
                galleryDto = (GalleryDto)data.getSerializableExtra("result");
                String positionStr = data.getStringExtra("position");


            } else {   // RESULT_CANCEL

            }
        }

    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        // Constructor
        public ImageAdapter(Context c){
            mContext = c;
        }

        @Override
        public int getCount() {
            System.out.println("galleryList.size()=================");
            System.out.println(galleryList.size());
            return galleryList.size();
        }


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

            ImageView imageView = new ImageView(mContext);
            System.out.println("11111111111111111111111");
            if (position < galleryList.size()){
                imageView.setImageBitmap(galleryList.get(position).getImg());
            }

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            return imageView;
        }

    }
}