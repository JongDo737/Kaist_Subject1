package com.example.final_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class AndroidGridLayoutActivity extends Activity implements Serializable {

    public ImageAdapter myAdapter = new ImageAdapter(this);

    public ArrayList<GalleryDto> galleryList = new ArrayList<>();

    ImageButton button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);


        // gallery Open Btn
        button = (ImageButton)findViewById(R.id.button);

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
                in1.putExtra("is_heart", galleryDto.getIs_heart());
                in1.putExtra("position", position);
                in1.putExtra("title",galleryDto.getImgTitle());
                in1.putExtra("desc",galleryDto.getImgDes());
                System.out.println("asdsadasdasdzxczxczxczczxczxczxczx");
                System.out.println(galleryDto.getImgDes());
                in1.putExtra("uri",galleryDto.getUri());
                in1.putExtra("path",galleryDto.getPath());
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
                String imagePath = getRealPathFromURI(data.getData()); // path 경로
                System.out.println(imagePath);
                System.out.println("sdfdsfdsafsfasfasdfasdadsfasfdas");
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
                    System.out.println(img);
                    System.out.println("여기여기여기여기여기여기여기여기여기여기여기여기");
                    galleryDto.setImg(img);
                    galleryList.add(galleryDto);
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
                int pos = data.getIntExtra("position", 0);
                Boolean is_heart = data.getBooleanExtra("is_heart", false);
                String title = data.getStringExtra("title");
                String desc = data.getStringExtra("desc");
                galleryList.get(pos).setImgTitle(title);
                galleryList.get(pos).setImgDes(desc);
                System.out.println(desc);
                System.out.println("zxcjkvhuahvnakjsdbfgkjsdbhjk");
                galleryList.get(pos).setIs_heart(is_heart);
            } else {   // RESULT_CANCEL

            }
        }

    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
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