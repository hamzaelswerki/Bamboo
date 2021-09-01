package com.example.bamboo.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bamboo.R;
import com.example.bamboo.adapter.CategoriesAdapter;
import com.example.bamboo.adapter.ProductsByCategoryAdapter;
import com.example.bamboo.callBack.OnClickCategoryRecycleViewListener;
import com.example.bamboo.callBack.OnProductClickListener;
import com.example.bamboo.databinding.FragmentCategoriesBinding;
import com.example.bamboo.model.SharedPrefHelper;
import com.example.bamboo.model.entity.Category;
import com.example.bamboo.model.entity.Product;
import com.example.bamboo.model.entity.Status2;
import com.example.bamboo.model.entity.StatusForCarts;
import com.example.bamboo.model.entity.StatusFv;
import com.example.bamboo.utils.Constants;
import com.example.bamboo.veiwModel.CartsViewModel;
import com.example.bamboo.veiwModel.HomeViewModel;
import com.example.bamboo.view.ProductDetailsActivity;

import java.util.List;


public class CategoriesFragment extends Fragment implements OnProductClickListener {

    FilterSearchFragment filterSearchFragment;
FragmentCategoriesBinding binding;
public static HomeViewModel homeViewModel;
    CategoriesAdapter categoriesAdapter;
    CartsViewModel cartsViewModel;

    public  static ProductsByCategoryAdapter byCategoryAdapter;
    public CategoriesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentCategoriesBinding.inflate(inflater,container,false);

       return binding.getRoot();


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
  homeViewModel= ViewModelProviders.of(this).get(HomeViewModel.class);
  cartsViewModel= ViewModelProviders.of(this).get(CartsViewModel.class);
        binding.ShimmerRvProductCt.startShimmerAnimation();

        createDrawer();
        openSearchFilter();
        homeViewModel.getCategoriesLiveData().observe(this, new Observer<Status2>() {
            @Override
            public void onChanged(Status2 status2) {
                if (status2!=null){
                      categoriesAdapter=new CategoriesAdapter(getContext(), status2.getData(),
                            new OnClickCategoryRecycleViewListener() {
                        @Override
                        public void OnViewClick(Object object, int position) {
                            Category c= (Category) object;
                            homeViewModel.getProductsBYCategoriesLiveData(binding.editTextFilter.getText().toString(),c.getId());
                        }
                    });
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                    binding.recyclerView.setAdapter(categoriesAdapter);
                }
            }
        });
        homeViewModel.getProductsBYCategoriesLiveData("",0);

        listenTOProductsBYCategoriesLiveData();
        Log.d("ttt","onViewCreated");

        binding.editTextFilter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (binding.editTextFilter.getRight() - binding.editTextFilter.getCompoundDrawables()
                            [DRAWABLE_RIGHT].getBounds().width())) {

                        homeViewModel.getProductsBYCategoriesLiveData(binding.editTextFilter.getText().toString(),categoriesAdapter.getSelected_position());
                        return true;
                    }
                }
                return false;
            }
        });
    }


    void  createDrawer(){
       binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        filterSearchFragment = (FilterSearchFragment) getChildFragmentManager().
                findFragmentById(R.id.fragment_navigation_search);

        filterSearchFragment.setUp(R.id.fragment_navigation_search
                , binding.drawerLayout);


    }


    private void openSearchFilter() {
       binding.filterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                filterSearchFragment.openDrawer((CoordinatorLayout) getActivity().findViewById(R.id.cl_container)
                        ,getActivity().findViewById(R.id.fragment_navigation_search));

            }
        });
    }

 void listenTOProductsBYCategoriesLiveData(){
        homeViewModel.productsBYCategoriesLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
            if (products!=null){
                byCategoryAdapter = new ProductsByCategoryAdapter(getContext(), products,
                        CategoriesFragment.this);
                binding.rvProductCt.setLayoutManager(new GridLayoutManager(getContext(), 2));
                binding.rvProductCt.setAdapter(byCategoryAdapter);
                binding.rvProductCt.setVisibility(View.VISIBLE);
                binding.ShimmerRvProductCt.stopShimmerAnimation();
                binding.ShimmerRvProductCt.setVisibility(View.GONE);
            }

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
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    Log.d("ttt","onResume");
    }
}