package com.example.bamboo.view.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.R;
import com.example.bamboo.adapter.CheckOutAdapter;
import com.example.bamboo.adapter.ProductsAdapter;
import com.example.bamboo.databinding.FragmentCheckOutBinding;
import com.example.bamboo.model.entity.CheckOut;
import com.example.bamboo.model.entity.CheckoutResponse;
import com.example.bamboo.model.entity.Product;
import com.example.bamboo.model.entity.StatusForCarts;
import com.example.bamboo.model.entity.StatusForCheckOut;
import com.example.bamboo.model.entity.StatusFv;
import com.example.bamboo.veiwModel.CartsViewModel;
import com.example.bamboo.view.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class CheckOutFragment extends Fragment implements CheckOutAdapter.OnQuantiyChanged {

CartsViewModel cartsViewModel;
FragmentCheckOutBinding binding;
    List<CheckOut> listProducts=new ArrayList<>();
    CheckOutAdapter adapter;
    int productId;
    SweetAlertDialog pDialog;
    public CheckOutFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentCheckOutBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartsViewModel= ViewModelProviders.of(this).get(CartsViewModel.class);
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        binding.ShimmerRvCheck.startShimmerAnimation();

        cartsViewModel.getProductInCheckOut();
        cartsViewModel.statusForCheckOutMutableLiveData.observe(this, new Observer<StatusForCheckOut>() {
           @Override
           public void onChanged(StatusForCheckOut statusForCheckOut) {
              if (statusForCheckOut!=null){
                  Log.d("ttt","onChanged");
                  listProducts.clear();
                  listProducts=statusForCheckOut.getData();
                    adapter = new CheckOutAdapter(getContext(), statusForCheckOut.getData());
                  binding.rcCheckOut.setLayoutManager(new LinearLayoutManager(getContext(),
                          RecyclerView.VERTICAL, false));
                  binding.rcCheckOut.setAdapter(adapter);
                  binding.rcCheckOut.setVisibility(View.VISIBLE);
                  binding.ShimmerRvCheck.stopShimmerAnimation();
                  binding.ShimmerRvCheck.setVisibility(View.GONE);
                adapter.setOnQuantiyChanged(CheckOutFragment.this);
                  adapter.notifyDataSetChanged();

              }

           }

});


        moveToDelete();
   binding.btnCheckOut.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           showProg();
           cartsViewModel.uploadAllCheckouts().observe(CheckOutFragment.this, new Observer<CheckoutResponse>() {
               @Override
               public void onChanged(CheckoutResponse checkoutResponse) {
                      if (checkoutResponse!=null){
                          Log.d("ttt",checkoutResponse.getCode());
                          if (checkoutResponse.getCode().equals("1")){
                              pDialog.hide();
                           showDilog(checkoutResponse.getMessage());
                           cartsViewModel.getProductInCheckOut();
                          }else {

                              Log.d("ttt",checkoutResponse.toString());

                          }
                      }
               }
           });

       }
   });
    }





    void moveToDelete(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(5,
                ItemTouchHelper.LEFT) {
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull
                    RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .addSwipeLeftBackgroundColor(R.color.blueLight)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX /3, dY, actionState, isCurrentlyActive);
            }


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                productId= listProducts.get(viewHolder.getAdapterPosition()).getId();
                if (direction == ItemTouchHelper.LEFT) {

                    showSwer();
                }
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.rcCheckOut);
    }

    void showSwer(){
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Delete")
                .setCancelText("No")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        cartsViewModel.deleteProductFromCheckOut(productId).observe(CheckOutFragment.this, new Observer<StatusFv>() {
                            @Override
                            public void onChanged(StatusFv statusFv) {
                                if (statusFv!=null){
                                    cartsViewModel.getProductInCheckOut();
                                    sDialog.setTitleText("Deleted!")
                                            .setContentText("")
                                            .showCancelButton(false)
                                            .setConfirmText("OK").setConfirmClickListener(
                                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.cancel();
                                        }
                                    })
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                }
                            }
                        });



                    }
                })

                .show();
    }

    void showDilog(String text) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setContentText(text)
                .show();
    }
    void  showProg(){
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    public void onClickedPlusButton(TextView tv, int productId) {
        cartsViewModel.addProductToCarts(productId,"").observe(this, new Observer<StatusForCarts>() {
            @Override
            public void onChanged(StatusForCarts statusForCarts) {

                if (statusForCarts!=null)
                    cartsViewModel.getProductInCheckOut();
            }
        });
    }

    @Override
    public void onClickedMinusButton(TextView tv, int productId) {
        cartsViewModel.decrementProduct(productId).observe(this, new Observer<StatusForCarts>() {
            @Override
            public void onChanged(StatusForCarts statusForCarts) {
                if (statusForCarts!=null)
                    cartsViewModel.getProductInCheckOut();

            }
        });
    }
}