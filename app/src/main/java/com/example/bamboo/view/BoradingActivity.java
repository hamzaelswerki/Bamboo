package com.example.bamboo.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bamboo.model.SharedPrefHelper;
import com.example.bamboo.view.fragments.OnBoard1Fragment;
import com.example.bamboo.view.fragments.OnBoard2Fragment;
import com.example.bamboo.view.fragments.OnBoard3Fragment;
import com.example.bamboo.R;
import com.example.bamboo.adapter.FragmentPagerAdapterOnBoard;
import com.example.bamboo.utils.Constants;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.Arrays;

public class BoradingActivity extends AppCompatActivity {
    public static ViewPager2 viewPager;
    DotsIndicator springDotsIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStaustBar();
        setContentView(R.layout.activity_borading);

        SharedPrefHelper.setIsFirst(getApplicationContext());

        viewPager = findViewById(R.id.view_pager);
        createViewPager();

    }
    void createViewPager(){

        ArrayList<Fragment> fragmentList =
                new ArrayList<>(Arrays.asList(new OnBoard1Fragment(), new OnBoard2Fragment(), new OnBoard3Fragment()));
        FragmentPagerAdapterOnBoard adapter =
                new FragmentPagerAdapterOnBoard( this,fragmentList);
        springDotsIndicator = (DotsIndicator) findViewById(R.id.spring_dots_indicator);
        viewPager.setAdapter(adapter);

        springDotsIndicator.setViewPager2(viewPager);

    }
    private void hideStaustBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = getWindow().getDecorView().getSystemUiVisibility(); // get current flag
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
            getWindow().getDecorView().setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.material_white)); // optional
        }
    }

}
