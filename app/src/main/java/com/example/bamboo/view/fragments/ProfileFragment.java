package com.example.bamboo.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.bamboo.R;
import com.example.bamboo.databinding.ProfileFragmentBinding;
import com.example.bamboo.model.entity.Status;
import com.example.bamboo.model.entity.User;
import com.example.bamboo.model.entity.UserResponse;
import com.example.bamboo.veiwModel.AuthViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ProfileFragment extends Fragment {
        ImageView back;

    AuthViewModel authViewModel;
    SweetAlertDialog pDialog;
        ProfileFragmentBinding binding;
        String imgString=null;
    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= ProfileFragmentBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
          pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        showProg();

        authViewModel.getUserData();
        authViewModel.currencyInfoRepository.userResponseMutableLiveData.observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if (userResponse!=null){


                    if (userResponse.getData().getText_image()!=null){
                        imgString=userResponse.getData().getText_image();
                        byte[] imageByteArray = Base64.decode(userResponse.getData().getText_image(), Base64.DEFAULT);
                        Glide.with(getContext()).asBitmap().load(imageByteArray).into(binding.imageViewProfile);
                    }

             //       Glide.with(ProfileFragment.this).load(userResponse.getData().getImagePath()).into(binding.imageViewProfile);

                binding.editTextPio.setText(userResponse.getData().getName());
                binding.editTextGmails.setText(userResponse.getData().getEmail());
                binding.editTextAddress.setText(userResponse.getData().getAddress());
                binding.editTextPhone.setText(userResponse.getData().getPhoneNumber());

                 pDialog.hide();
                }

            }
        });

      binding.back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              getFragmentManager().popBackStack();
          }
      });


      binding.save.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              showProg();
              User user=new User();
              user.setEmail(binding.editTextGmails.getText().toString());
              user.setAddress(binding.editTextAddress.getText().toString());
              user.setName(binding.editTextPio.getText().toString());
              user.setText_image(imgString);
              Log.d("ttt",user.getText_image());
           //   user.setImage("http://braastore.000webhostapp.com/media/products/extra_images/Serina " +
             //         "Newtont/xCPvPYBR6z9mw7YigwBSAXUiVyvdIcZ5vtFjsTLr.png");

            authViewModel.currencyInfoRepository.updateUser(user).observe(ProfileFragment.this, new Observer<Status>() {
                @Override
                public void onChanged(Status status) {
                    if (status!=null){
                        authViewModel.getUserData();
                        showDilog("Updated Successfully");
                    }
                }
            });
          }
      });

     binding.imageViewProfile.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
             photoPickerIntent.setType("image/*");
             startActivityForResult(photoPickerIntent, 1);
         }
     });
    }


    void  showProg(){
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

    }
    void showDilog(String text) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setContentText(text)
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                 binding.imageViewProfile.setImageBitmap(bitmap);
                 imgString=getEncoded64ImageStringFromBitmap(bitmap);

/*
                    Log.d("ttt",getEncoded64ImageStringFromBitmap(bitmap));
                    byte[] imageBytes = Base64.decode(getEncoded64ImageStringFromBitmap(bitmap), Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    binding.imageViewProfile.setImageBitmap(decodedImage);
*/

               /*      imgString=getEncoded64ImageStringFromBitmap(bitmap);

                    byte[] decodedString = Base64.decode(imgString, Base64.URL_SAFE);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
*/

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //     String filePath = getPath(selectedImage);
             //   String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
             //   image_name_tv.setText(filePath);
/*
                if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                    //FINE
                } else {
                    //NOT IN REQUIRED FORMAT
                }*/
            }
    }


    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }
}