package com.example.bamboo.view.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bamboo.R;
import com.example.bamboo.databinding.FragmentProductDetailsBinding;
import com.example.bamboo.model.SharedPrefHelper;
import com.example.bamboo.model.entity.Product;
import com.example.bamboo.model.entity.StatusForCarts;
import com.example.bamboo.utils.Constants;
import com.example.bamboo.veiwModel.CartsViewModel;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductDetailsFragment extends Fragment {

    FragmentProductDetailsBinding binding;
    CartsViewModel cartsViewModel;
     int productId=-1;
    SweetAlertDialog pDialog ;
    public ProductDetailsFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProductDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        cartsViewModel= ViewModelProviders.of(this).get(CartsViewModel.class);

            if (getArguments()!=null){
                Product product= (Product) getArguments().getSerializable(Constants.OBJ_TO_PASS);
                binding.name.setText(product.getName());
                binding.tvCt.setText(product.getCategory());


                binding.price.setText(product.getSalePrice()+"$");
                binding.imageView13.setImageURI(product.getMainImage());
                productId=product.getId();
            }

        binding.btnStarss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  showProg(true);
            if (productId!=-1) {
                Log.d("ttt","productId!=-1 ");

                cartsViewModel.addProductToCarts(productId, SharedPrefHelper.getToken(getContext())).observe(ProductDetailsFragment.this, new Observer<StatusForCarts>() {
                    @Override
                    public void onChanged(StatusForCarts statusForCarts) {

                        if (statusForCarts!=null){
                            Log.d("ttt","statusForCarts!=null");

                            pDialog.hide();
                            showDilog(statusForCarts.getMessage());
                          }
                        Log.d("ttt","statusForCarts=========c=null");

                    }
                });

              /*  getFragmentManager().beginTransaction().replace(R.id.frame_home, new CheckOutFragment())
                        .addToBackStack("1").commit();*/
            }else {
                Log.d("ttt","product =null");

            }

            }
        });
    }

    void showDilog(String text) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setContentText(text)
                .show();
    }
    void showDilogERror(String text) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setContentText(text)
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