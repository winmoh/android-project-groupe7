package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class acueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acueil);
        /*ImageView image=(ImageView)findViewById(R.id.imlogo);
        AnimationDrawable anima=new AnimationDrawable();
        anima.addFrame(getResources().getDrawable(R.drawable.again),500);
        anima.addFrame(getResources().getDrawable(R.drawable.again),500);
        anima.addFrame(getResources().getDrawable(R.drawable.again),500);
        anima.addFrame(getResources().getDrawable(R.drawable.again),500);

        image.setBackground(anima);
        anima.start();*/
        ImageView logo=(ImageView)findViewById(R.id.imlogo);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.log_animation);
        logo.startAnimation(animation);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                Intent ntt=new Intent(acueil.this,MainActivity.class);
                startActivity(ntt);
            }

        },4000);
    }
}