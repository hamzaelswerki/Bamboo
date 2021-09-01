package com.example.bamboo.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.bamboo.R;
import com.example.bamboo.model.SharedPrefHelper;
import com.example.bamboo.view.fragments.CategoriesFragment;
import com.example.bamboo.view.fragments.CheckOutFragment;
import com.example.bamboo.view.fragments.FavoriteFragment;
import com.example.bamboo.view.fragments.HomeFragment;
import com.example.bamboo.view.fragments.OrdersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    HomeFragment homeFragment;
    CategoriesFragment categoriesFragment;
    CheckOutFragment checkOutFragment;
    FavoriteFragment favoriteFragment;
    OrdersFragment ordersFragment;
    public  static  String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         token= SharedPrefHelper.getToken(getApplicationContext());

        Log.d("ttt",token);
        homeFragment=new HomeFragment();
        categoriesFragment=new CategoriesFragment();
        checkOutFragment=new CheckOutFragment();
        favoriteFragment=new FavoriteFragment();
        ordersFragment=new OrdersFragment();
        createNavigationView();
    }
    private void createNavigationView() {
        navigationView=findViewById(R.id.bottom_navigation);
        navigationView.performClick();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        if (!homeFragment.isVisible())
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,homeFragment).commit();
                        return true;

                    case R.id.action_categories:
                        if (!categoriesFragment.isVisible())
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,categoriesFragment).commit();
                        return true;
                    case R.id.check_out:
                        if (!checkOutFragment.isVisible())
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,checkOutFragment).commit();
                        return true;
                        case R.id.favorite:
                        if (!favoriteFragment.isVisible())
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,favoriteFragment).commit();
                        return true;
                        case R.id.orders:
                        if (!ordersFragment.isVisible())
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,ordersFragment).commit();
                        return true;
                }
                return false;
            }
        });
        navigationView.setSelectedItemId(R.id.action_home);

    }

}