package com.example.bamboo.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bamboo.R;
import com.example.bamboo.view.ChatBranch;
import com.google.gson.internal.$Gson$Preconditions;

public class MoreFragment extends Fragment {

TextView profileTxt,settings,liveChat,tvName;
ImageView imageView;
    public MoreFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileTxt=view.findViewById(R.id.profile);
        settings=view.findViewById(R.id.settings);
        liveChat=view.findViewById(R.id.live_chat);
        tvName=view.findViewById(R.id.textView10);
        imageView=view.findViewById(R.id.image_view_profile);
        view.findViewById(R.id.my_coupons).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToGmail();
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
     getUserImg();
        goProfile();
        Settings();
        LiveChat();

    }

    private void LiveChat() {
        liveChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext()
                        , ChatBranch.class).putExtra("branchName", "Bamboo 1")
                        .putExtra("branchId", "branch_1"));
            }
        });
    }

    private void Settings() {
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingSheetFragment settingSheetFragment = new SettingSheetFragment();
                settingSheetFragment.show(getFragmentManager(),settingSheetFragment.getTag());
            }
        });
    }

    private void goProfile() {
profileTxt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
         getActivity().getSupportFragmentManager().beginTransaction()
                 .setCustomAnimations(
                         R.anim.slide_in,
                         R.anim.fade_out,
                         R.anim.fade_in,
                         R.anim.slide_out
                 ).replace(R.id.more_frame,new ProfileFragment()).addToBackStack("ytt").commit();

    }
});
    }
    void sendToGmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "halswyrky2@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is my subject text");
        startActivity(Intent.createChooser(emailIntent, null));
    }
   void getUserImg(){
        if (HomeFragment.imgUser!=null){
       byte[] imageByteArray = Base64.decode(HomeFragment.imgUser, Base64.DEFAULT);
       Glide.with(getContext()).asBitmap().load(imageByteArray).into(imageView);
       tvName.setText("HI  "+HomeFragment.userName.toUpperCase());
        }
    }
}