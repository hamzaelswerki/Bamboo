package com.example.bamboo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bamboo.R;
import com.example.bamboo.databinding.ActivityMainBinding;
import com.example.bamboo.model.SharedPrefHelper;
import com.example.bamboo.utils.Constants;
import com.example.bamboo.veiwModel.HomeViewModel;

public class MainActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 650;
    Boolean  isFirst;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= ActivityMainBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

openActivityWithAnim(binding.imageView);

    }
    private void openActivityWithAnim(final ImageView ivLogo) {
        ivLogo.animate()
                .setDuration(400)
                .alpha(0.9f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setStartDelay(400)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ivLogo.animate()
                                .setDuration(550)
                                .scaleX(1.2f)
                                .scaleY(1.2f)
                                .alpha(1.2f)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        openActivity();
                                    }
                                });
                    }
                });
    }
    void openActivity(){
        isFirst= SharedPrefHelper.getIsFirst(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isFirst){
                    startActivity(new Intent(getApplicationContext(), BoradingActivity.class));
                }else {
                    if (SharedPrefHelper.getToken(getApplicationContext()).equalsIgnoreCase("null")){
                  // todo should be Rigester
                        startActivity(new Intent(getApplicationContext(), RegstrationActivity  .class));
                    }else {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }

                }
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }


}
