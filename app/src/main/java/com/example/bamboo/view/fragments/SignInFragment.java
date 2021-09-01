package com.example.bamboo.view.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bamboo.R;
import com.example.bamboo.databinding.FragmentLoginNameAndEmailBinding;
import com.example.bamboo.databinding.FragmentSignInBinding;
import com.example.bamboo.model.SharedPrefHelper;
import com.example.bamboo.model.entity.Status;
import com.example.bamboo.model.entity.User;
import com.example.bamboo.utils.Constants;
import com.example.bamboo.veiwModel.AuthViewModel;
import com.example.bamboo.view.HomeActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SignInFragment extends Fragment {

    AuthViewModel authViewModel;
    SweetAlertDialog pDialog;
    SweetAlertDialog dialogError;
    FragmentSignInBinding binding;


    public SignInFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding= FragmentSignInBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        dialogError=  new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);


        binding.btnSign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(binding.editTextLoginNumber.getText().toString())){
                    showProg(true);
                   String phoneNumber= Constants.CODE_PALESTINE+binding.editTextLoginNumber.getText().toString();

                    User user = new User(phoneNumber
                            ,binding.editTextPsssword.getText().toString());

                    authViewModel.loginUser(user).observe(getActivity(), new Observer<Status>() {
                        @Override
                        public void onChanged(Status status) {
                            if (status!=null&&status.getCode()==1){
                                SharedPrefHelper.setToken(getContext(),status.getToken());
                                showProg(false);
                                openHomeActivity();

                            }else {
                                showProg(false);
                                showDilog("Error in phone Number or password");
                            }
                        }
                    });
                }else {
                    showDilog("Enter all fields");
                }
            }
        });
        binding.txSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMobileNumberFragment();
            }
        });
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

    void showDilog(String text) {
dialogError.setTitleText(text).setContentText("ok").show();
    }
    void openHomeActivity() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    void startMobileNumberFragment(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out)
                .replace(R.id.frame_login, new MobileNumberFragment())
                .commit();
    }

}