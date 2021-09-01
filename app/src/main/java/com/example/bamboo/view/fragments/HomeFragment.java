package com.example.bamboo.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bamboo.R;
import com.example.bamboo.adapter.ImagesAdsAdapter;
import com.example.bamboo.adapter.ImagesAdsAdapter2;
import com.example.bamboo.adapter.ProductsAdapter;
import com.example.bamboo.callBack.OnProductClickListener;
import com.example.bamboo.databinding.FragmentHomeBinding;
import com.example.bamboo.model.SharedPrefHelper;
import com.example.bamboo.model.entity.Product;
import com.example.bamboo.model.entity.Status;
import com.example.bamboo.model.entity.StatusForCarts;
import com.example.bamboo.model.entity.StatusFv;
import com.example.bamboo.model.entity.UserResponse;
import com.example.bamboo.utils.Constants;
import com.example.bamboo.veiwModel.AuthViewModel;
import com.example.bamboo.veiwModel.CartsViewModel;
import com.example.bamboo.veiwModel.HomeViewModel;
import com.example.bamboo.view.MapsActivity;
import com.example.bamboo.view.MoreActivity;
import com.example.bamboo.view.ProductDetailsActivity;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements OnProductClickListener {

    FragmentHomeBinding binding;
    HomeViewModel homeViewModel;
   CartsViewModel cartsViewModel;
   public  static  String imgUser=null;
   public  static  String userName=null;
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getContext());
        homeViewModel= ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=  FragmentHomeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        openMoreFfragment();
        getImgProfile();
        Date current_date = new Date();

        Log.d("ttt",current_date.toString());
        cartsViewModel= ViewModelProviders.of(this).get(CartsViewModel.class);
        binding.shimmerBigSles.startShimmerAnimation();
        binding.shimmerNewrivv.startShimmerAnimation();


        homeViewModel.getNewProductsMutableLiveData().observe(getActivity(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products!=null){
                    ProductsAdapter adapter = new ProductsAdapter(getContext(), products,
                            HomeFragment.this);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                    binding.recyclerView.setAdapter(adapter);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.shimmerNewrivv.stopShimmerAnimation();
                    binding.shimmerNewrivv.setVisibility(View.GONE);
                }

            }
        });

        List<String> myList = new ArrayList<>();

        myList.add("https://firebasestorage.googleapis.com/v0/b/sweet-app-2b6e7.appspot.com/o/Categories%20Images%2Fgreeew1.png?alt=media&token=98abefc9-538f-4404-9694-062ec285bd57");
        myList.add("https://firebasestorage.googleapis.com/v0/b/sweet-app-2b6e7.appspot.com/o/Categories%20Images%2Fgreeew2.png?alt=media&token=a8d3392f-130a-4fea-b623-ec03aef7bce7");
        myList.add("http://braastore.000webhostapp.com/media/products/extra_images/Serina Newtont/lqJsbN0JtPoUDA7BIVB97yGW3gaJQFmK6c3N9c70.png");
        ImagesAdsAdapter2 imagesAdsAdapter=new ImagesAdsAdapter2(getContext(),myList);
        binding.viewPagerAds.setAdapter(imagesAdsAdapter);
        binding.viewPagerAds.setVisibility(View.VISIBLE);
        binding.springDotsIndicator.setViewPager(binding.viewPagerAds);


        homeViewModel.getHotOffersMutableLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products!=null){
                 /*   List<String> listImgs = new ArrayList<>();
                    for (Product p :products){
                        listImgs.add(p.getMainImage());
                    }*/
                    Collections.reverse(products);

            ImagesAdsAdapter imagesAdsAdapter=new ImagesAdsAdapter(getContext(),products,true);
                    binding.viewPagerHotOffer.setAdapter(imagesAdsAdapter);
                    binding.viewPagerHotOffer.setVisibility(View.VISIBLE);
                    binding.springDotsIndicatorHotOffer.setViewPager(binding.viewPagerHotOffer);


                }
            }
        });


        homeViewModel.getbigSalesMutableLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products!=null){
                    ProductsAdapter adapter = new ProductsAdapter(getContext(), products,
                            HomeFragment.this);
                    binding.bigSales.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                    binding.bigSales.setAdapter(adapter);
                    binding.bigSales.setVisibility(View.VISIBLE);
                    binding.shimmerBigSles.stopShimmerAnimation();
                    binding.shimmerBigSles.setVisibility(View.GONE);
                }

            }
        });

        binding.btnMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MapsActivity.class));
            }
        });
    }



    private void openMoreFfragment() {
        binding.imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         startActivity(new Intent(getContext(), MoreActivity.class));

            }
        });
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
        Product product= (Product) object;
        homeViewModel.addProductToFav(product.getId()).observe(this, new Observer<StatusFv>() {
            @Override
            public void onChanged(StatusFv status) {
                if (status!=null){
                    Toast.makeText(getContext(),status.getMassege(),Toast.LENGTH_SHORT).show();
            }else {
                    Log.d("ttt","Nullllllllllllllllllll");
                }
            }
        });
    }

    void getImgProfile(){
        AuthViewModel authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        authViewModel.getUserData();
        authViewModel.currencyInfoRepository.userResponseMutableLiveData.observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if (userResponse!=null){
                    if (userResponse.getData().getText_image()!=null){
                        imgUser=userResponse.getData().getText_image();
                        userName=userResponse.getData().getName();
                        byte[] imageByteArray = Base64.decode(userResponse.getData().getText_image(), Base64.DEFAULT);
                        Glide.with(getContext()).asBitmap().load(imageByteArray).into(binding.imageViewProfile);
                    }
                }

            }
        });

    }
}