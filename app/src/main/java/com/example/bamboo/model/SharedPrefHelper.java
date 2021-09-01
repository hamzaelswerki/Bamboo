package com.example.bamboo.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bamboo.utils.Constants;

public class SharedPrefHelper {

 public  static  String getToken(Context context){
         SharedPreferences sharedpreferences = context.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);
         return sharedpreferences.getString("token", "null");
 }

    public  static  void setToken(Context context,String token){
        SharedPreferences sharedpreferences = context.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public  static   void setIsFirst(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(Constants.IS_FIRST, false);
        editor.apply();
    }

    public  static  boolean getIsFirst(Context context){
     return  context.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE).getBoolean(Constants.IS_FIRST,true);
    }
}
