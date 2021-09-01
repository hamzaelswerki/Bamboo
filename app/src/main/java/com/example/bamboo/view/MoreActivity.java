package com.example.bamboo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bamboo.R;
import com.example.bamboo.view.fragments.MoreFragment;

public class MoreActivity extends AppCompatActivity {
MoreFragment moreFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        moreFragment =new MoreFragment();


        getSupportFragmentManager().beginTransaction().replace(R.id.more_frame,moreFragment)
                .setCustomAnimations(R.anim.fromleft,R.anim.fromright)
                .commit();
    }
}