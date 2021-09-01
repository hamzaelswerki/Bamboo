package com.example.bamboo.veiwModel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.bamboo.model.Repository.AuthRepository;
import com.example.bamboo.model.entity.Status;
import com.example.bamboo.model.entity.User;
import com.example.bamboo.model.entity.UserResponse;

public class AuthViewModel extends AndroidViewModel {

   public AuthRepository currencyInfoRepository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        currencyInfoRepository=AuthRepository.getInstance();
    }

    public MutableLiveData<Status>  loginUser(User user){
        return currencyInfoRepository.loginUser(user);
    }
    public MutableLiveData<Status>  registerNewUser2(User user){
        return currencyInfoRepository.registerNewUser2(user);
    }

    public MutableLiveData<Status>  updateUser(User user){
        return currencyInfoRepository.updateUser(user);
    }
   public void   getUserData(){
         currencyInfoRepository.getUserData();
    }

}
