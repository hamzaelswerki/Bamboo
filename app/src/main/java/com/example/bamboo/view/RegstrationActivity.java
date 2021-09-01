package com.example.bamboo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bamboo.R;
import com.example.bamboo.view.fragments.LoginNameAndEmailFragment;
import com.example.bamboo.view.fragments.MobileNumberFragment;
import com.example.bamboo.view.fragments.SignInFragment;

public class RegstrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regstration);


        getSupportFragmentManager().beginTransaction().replace(R.id.frame_login,new SignInFragment())
                .setCustomAnimations(R.anim.fromleft,R.anim.fromright)
                .commit();
    }
}