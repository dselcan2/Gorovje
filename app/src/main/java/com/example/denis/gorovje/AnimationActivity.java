package com.example.denis.gorovje;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class AnimationActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //final Context context;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        VideoView animacijaview;
        animacijaview = (VideoView) findViewById(R.id.videoView);
        animacijaview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.animacija));

        animacijaview.start();

        /*animacijaview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //mp.setLooping(true);

            }
        });*/
        animacijaview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
                startActivity(intent);
            }
        });
    }
}
