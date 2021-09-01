package com.example.bamboo.model.network;

import android.content.Context;

import com.example.bamboo.utils.Constants;
import com.example.bamboo.view.HomeActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient( ) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(createHttpLoggingInterceptor2())
                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
        }

        return retrofit;
    }
    public  static OkHttpClient createHttpLoggingInterceptor(){
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient client = new OkHttpClient.Builder()

                .addInterceptor(interceptor)

                .build();
        return  client;
    }

    public  static OkHttpClient createHttpLoggingInterceptor2(){
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                      .addHeader("Authorization", "Bearer " + HomeActivity.token)
                  //  .addHeader("Authorization", "Bearer " + "w8Cgfqvc8JsB06VB1G91vCmtSVnZxcdd")
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        return  client;
    }
}
