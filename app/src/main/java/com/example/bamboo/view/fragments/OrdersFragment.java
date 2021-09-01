package com.example.bamboo.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.adapter.OrderAdapter;
import com.example.bamboo.databinding.FragmentOrdresBinding;
import com.example.bamboo.model.entity.OrderData;
import com.example.bamboo.model.entity.OrderStatus;
import com.example.bamboo.veiwModel.CartsViewModel;

import java.util.ArrayList;
import java.util.List;


public class OrdersFragment extends Fragment {

    CartsViewModel cartsViewModel;
    FragmentOrdresBinding binding;
  public   List<OrderData> orderActiveDataList =new ArrayList<>();
  public   List<OrderData> orderCompletedDataList =new ArrayList<>();

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrdresBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartsViewModel = ViewModelProviders.of(this).get(CartsViewModel.class);

        binding.shimmerActive.startShimmerAnimation();
        binding.shimmerCompleted.startShimmerAnimation();

        cartsViewModel.getOrders().observe(this, new Observer<OrderStatus>() {
            @Override
            public void onChanged(OrderStatus orderStatus) {

                if (orderStatus != null) {
                    for (OrderData data:orderStatus.getData()){
                        if (data.getStatus().equalsIgnoreCase("pending")){
                            orderActiveDataList.add(data);
                        }else {

                            orderCompletedDataList.add(data);
                        }

                    }
                    createOrderAdapterActive();
                    createOrderAdapterCompleted();
                }
            }

        });
    }


    void createOrderAdapterActive(){
        OrderAdapter orderAdapter=new OrderAdapter(getContext(), orderActiveDataList);
        binding.rvActive.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));
        binding.rvActive.setAdapter(orderAdapter);
        binding.rvActive.setVisibility(View.VISIBLE);
        binding.shimmerActive.stopShimmerAnimation();
        binding.shimmerActive.setVisibility(View.GONE);
    }

    void createOrderAdapterCompleted(){
        OrderAdapter orderAdapter=new OrderAdapter(getContext(),orderCompletedDataList);
        Log.d("ttt","sivve = "+orderCompletedDataList.size());
        binding.rvProductCompleted.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));
        binding.rvProductCompleted.setAdapter(orderAdapter);
        //binding.rvProductCompleted.setVisibility(View.VISIBLE);
        binding.shimmerCompleted.stopShimmerAnimation();
        binding.shimmerCompleted.setVisibility(View.GONE);
        binding.noItemImg.setVisibility(View.VISIBLE);
        binding.tvNoCrt.setVisibility(View.VISIBLE);
        if (orderCompletedDataList.size()==0){
            binding.noItemImg.setVisibility(View.VISIBLE);
            binding.tvNoCrt.setVisibility(View.VISIBLE);
        }else {

        }
    }
}