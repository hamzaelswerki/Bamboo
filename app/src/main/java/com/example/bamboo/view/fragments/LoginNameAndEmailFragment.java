package com.example.bamboo.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bamboo.databinding.FragmentLoginNameAndEmailBinding;
import com.example.bamboo.model.SharedPrefHelper;
import com.example.bamboo.model.entity.Status;
import com.example.bamboo.model.entity.User;
import com.example.bamboo.utils.Constants;
import com.example.bamboo.veiwModel.AuthViewModel;
import com.example.bamboo.view.HomeActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class LoginNameAndEmailFragment extends Fragment {

    FragmentLoginNameAndEmailBinding binding;
    AuthViewModel authViewModel;
    String phoneNumber;
    SweetAlertDialog pDialog;

    public LoginNameAndEmailFragment(String phoneNumber) {
        this.phoneNumber = phoneNumber;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

     /*   authViewModel.registerNewUser(new User(phoneNumber+"66","12345678")).
                observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if (status!=null&&status.getCode()==1){

                    Log.d("ttt",status.getMessage());
                    storeToken(status.getToken());
                    showProg(false);

                } else {
                    Toast.makeText(getContext(),"You already have an account",Toast.LENGTH_SHORT).show();
                    openMobileNumberFragment();
                    showProg(false);

                }

            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginNameAndEmailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.editTextName.getText()) || TextUtils.isEmpty(binding.editTextAddress.getText()) ||
                        TextUtils.isEmpty(binding.editTextEmail.getText())) {
                    showDilog("Enter all fields");
                } else {
                    showProg(true);

                    User user = new User(phoneNumber, binding.editTextPss.getText().toString(),
                            binding.editTextName.getText().toString(),
                            binding.editTextEmail.getText().toString(),
                            binding.editTextAddress.getText().toString());

                    Log.d("ttt",phoneNumber+"Phone in Rigesssster");
                    authViewModel.registerNewUser2(user).observe(getActivity(), new Observer<Status>() {
                        @Override
                        public void onChanged(Status status) {
                            if (status != null && status.getCode() == 1) {
                                SharedPrefHelper.setToken(getContext(),status.getToken());
                                openHomeActivity();
                                showProg(false);
                            } else {

                                showDilog("ُThe phone number or Email is pre-registered");
                                showProg(false);

                            }

                        }
                    });

                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void openHomeActivity() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    void showDilog(String text) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText(text)
                .setContentText("ok")
                .show();
    }

    void showProg(boolean isShow) {
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        if (isShow) {
            pDialog.show();
        } else {
            pDialog.hide();

        }

    }


}