package com.example.bamboo.view.fragments;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bamboo.R;
import com.example.bamboo.adapter.CheckOutAdapter;
import com.example.bamboo.adapter.ShowListFvAdapter;
import com.example.bamboo.callBack.OnProductClickListener;
import com.example.bamboo.databinding.FragmentCheckOutBinding;
import com.example.bamboo.databinding.FragmentFavoriteBinding;
import com.example.bamboo.model.SharedPrefHelper;
import com.example.bamboo.model.entity.Product;
import com.example.bamboo.model.entity.StatusForCarts;
import com.example.bamboo.model.entity.StatusForCheckOut;
import com.example.bamboo.model.entity.StatusFv;
import com.example.bamboo.utils.Constants;
import com.example.bamboo.veiwModel.CartsViewModel;
import com.example.bamboo.veiwModel.HomeViewModel;
import com.example.bamboo.view.HomeActivity;
import com.example.bamboo.view.ProductDetailsActivity;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class FavoriteFragment extends Fragment  implements OnProductClickListener {

    HomeViewModel homeViewModel;
    FragmentFavoriteBinding binding;
    List<Product> listProducts=null;
    int productId;
    public FavoriteFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     binding=    FragmentFavoriteBinding.inflate(inflater,container,false);
     return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel= ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getShowProductFvLiveData();


        homeViewModel.showProductFvLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                binding.shimmerFv.startShimmerAnimation();

                if (products!=null){

                    listProducts=products;
                    Log.d("ttt",products.toString());
                    Log.d("ttt",products.size()+"");
                    ShowListFvAdapter adapter = new ShowListFvAdapter(getContext(),products,
                            FavoriteFragment.this);
                    binding.rvProductFV.setLayoutManager(new LinearLayoutManager(getContext(),
                            RecyclerView.VERTICAL, false));
                    binding.rvProductFV.setAdapter(adapter);
                    binding.rvProductFV.setVisibility(View.VISIBLE);
                    binding.shimmerFv.stopShimmerAnimation();
                    binding.shimmerFv.setVisibility(View.GONE);

                }
            }
        });
        moveToDelete();
    }


    @Override
    public void OnProductClicked(Object object) {
        Product product= (Product) object;
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constants.OBJ_TO_PASS,product);
        startActivity(new Intent(getActivity(), ProductDetailsActivity.class).putExtras(bundle));

        getActivity().overridePendingTransition(R.anim.fromleft, R.anim.fromright);
    }

    @Override
    public void OnAddToCartClicked(Object object) {
        Product product= (Product) object;
        CartsViewModel cartsViewModel= ViewModelProviders.of(this).get(CartsViewModel.class);

        cartsViewModel.addProductToCarts(product.getId(), SharedPrefHelper.getToken(getContext())).observe(this, new Observer<StatusForCarts>() {
            @Override
            public void onChanged(StatusForCarts statusForCarts) {
                if (statusForCarts!=null){
                    Toast.makeText(getContext(),statusForCarts.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void OnAddToFavClicked(Object object) {

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
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.rvProductFV);
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
                        homeViewModel.deleteProductFromFav(productId).observe(FavoriteFragment.this, new Observer<StatusFv>() {
                            @Override
                            public void onChanged(StatusFv statusFv) {
                                if (statusFv!=null){
                                    homeViewModel.getShowProductFvLiveData();
                                    sDialog.setTitleText("Deleted!")
                                            .setContentText("")
                                            .showCancelButton(false)
                                            .setConfirmText("OK").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
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
}