package com.zeelearn.ekidzee.mlzs;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class SplashActivity extends BaseActivity{

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imgLogo = findViewById(R.id.logo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imgLogo.startAnimation(animation);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                navigateLogin();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
