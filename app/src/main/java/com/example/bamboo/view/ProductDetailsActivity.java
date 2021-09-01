package com.example.bamboo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;

import com.example.bamboo.R;
import com.example.bamboo.databinding.ActivityProductDetailsBinding;
import com.example.bamboo.model.SharedPrefHelper;
import com.example.bamboo.model.entity.Product;
import com.example.bamboo.model.entity.StatusForCarts;
import com.example.bamboo.utils.Constants;
import com.example.bamboo.veiwModel.CartsViewModel;
import com.example.bamboo.view.fragments.ProductDetailsFragment;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductDetailsActivity extends AppCompatActivity {

    ActivityProductDetailsBinding binding;
    CartsViewModel cartsViewModel;
    int productId=-1;
    SweetAlertDialog pDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=   ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cartsViewModel= ViewModelProviders.of(this).get(CartsViewModel.class);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        if (getIntent()!=null){
            Product product= (Product) getIntent().getExtras().getSerializable(Constants.OBJ_TO_PASS);
            binding.name.setText(product.getName());
            binding.tvCt.setText(product.getCategory());
            binding.textView9.setText(product.getLongDescription());
        binding.textView9.setText(Html.fromHtml((product.getLongDescription())));

            binding.price.setText(product.getSalePrice()+"$");
            binding.imageView13.setImageURI(product.getMainImage());
            productId=product.getId();
        }

        binding.btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     showProg();
                if (productId!=-1) {
                    Log.d("ttt","productId!=-");

                    cartsViewModel.addProductToCarts(productId, SharedPrefHelper.getToken(getApplicationContext()))
                            .observe(ProductDetailsActivity.this, new Observer<StatusForCarts>() {
                        @Override
                        public void onChanged(StatusForCarts statusForCarts) {
                            Log.d("ttt","onChanged!=-");

                            if (statusForCarts!=null){
                                pDialog.hide();
                                showDilog(statusForCarts.getMessage());
                            }else {
                                Log.d("ttt","statusForCarts=null=-");

                            }

                        }
                    });

              /*  getFragmentManager().beginTransaction().replace(R.id.frame_home, new CheckOutFragment())
                        .addToBackStack("1").commit();*/
                }

            }
        });
    }


    void showDilog(String text) {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setContentText(text)
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.hide();
                        onBackPressed();
                    }
                })
                .show();
    }

    void showProg() {
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();



    }
    void openHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
     openHomeActivity();
    }
}