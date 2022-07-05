package com.example.final_project;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;


public class RubyLoading extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruby_loading);
        System.out.println("123123123");
        VideoView videoView = findViewById(R.id.videoView1);
        VideoView videoView2 = findViewById(R.id.videoView2);
        // sample.mp4 설정
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/rudadaktext");
        videoView.setVideoURI(uri);
        Uri uri2 = Uri.parse("android.resource://" + getPackageName() + "/raw/rudadak");
        videoView2.setVideoURI(uri2);
        // 리스너 등록
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        videoView2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        Handler hand = new Handler();

        hand.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent i = new Intent(RubyLoading.this, Calendar.class);
                startActivity(i);
                finish();

            }
        }, 2000);
    }
}