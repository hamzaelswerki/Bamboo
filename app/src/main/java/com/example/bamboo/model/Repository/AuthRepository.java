package com.example.bamboo.model.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bamboo.model.entity.Status;
import com.example.bamboo.model.entity.User;
import com.example.bamboo.model.entity.UserResponse;
import com.example.bamboo.model.network.ApiClient;
import com.example.bamboo.model.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    public MutableLiveData<Status> registerNewUserMutableLiveData;
    public MutableLiveData<Status> loginserMutableLiveData;
    public MutableLiveData<Status> updateUserMutableLiveData;
    public MutableLiveData<UserResponse> userResponseMutableLiveData;
    ApiInterface apiInterface;
    private static AuthRepository instance;


    private AuthRepository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        registerNewUserMutableLiveData = new MutableLiveData<>();
        loginserMutableLiveData = new MutableLiveData<>();
        updateUserMutableLiveData = new MutableLiveData<>();
        userResponseMutableLiveData = new MutableLiveData<>();
    }

    public static AuthRepository getInstance() {
        if (instance == null) {
            instance = new AuthRepository();
        }
        return instance;
    }



    public MutableLiveData<Status> loginUser(User user) {
        apiInterface.loginUser(user.getPhone_number(),user.getPassword())
                .enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.body() != null) {
                    loginserMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                loginserMutableLiveData.setValue(null);
                t.printStackTrace();
            }
        });
        return loginserMutableLiveData;
    }
    public MutableLiveData<Status> registerNewUser2(User user) {
        user.setImage("sdf");
        apiInterface.registerNewUser2(user.getPhone_number(),user.getPassword()
                ,user.getName(),user.getEmail(),user.getAddress(),"null6")
                .enqueue(new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if (response.body() != null) {
                            registerNewUserMutableLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {

                        Log.d("ttt",t.getMessage());
                        t.printStackTrace();
                        registerNewUserMutableLiveData.setValue(null);

                    }
                });
        return registerNewUserMutableLiveData;
    }


    public MutableLiveData<Status>  updateUser(User user) {
        apiInterface.updateCustomer(user.getName(),user.getEmail(),user.getAddress(),null,user.getText_image())
                .enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

                if (response.body() != null) {

                    updateUserMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                updateUserMutableLiveData.setValue(null);
                t.printStackTrace();

            }
        });
        return updateUserMutableLiveData;
    }


    public void   getUserData() {
        apiInterface.getUserData()
                .enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.isSuccessful()) {
                    userResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                userResponseMutableLiveData.setValue(null);
                t.printStackTrace();

            }
        });
    }
}
