package com.example.bamboo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.databinding.ItemCheckOutBinding;
import com.example.bamboo.databinding.ItemOrderBinding;
import com.example.bamboo.model.entity.CheckOut;
import com.example.bamboo.model.entity.OrderData;
import com.example.bamboo.model.entity.ProductOrder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<OrderData> mValues;
    private Context context;


    public OrderAdapter(Context context, List<OrderData> items
                        //, OnProductClickListener onProductClickListener
                               ) {
        mValues = items;
        this.context = context;
       // this.onProductClickListener = onProductClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.
                inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ViewItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        ViewItemHolder viewItemHolder = (ViewItemHolder) holder;
        final OrderData dataModel = mValues.get(position);
        ((ViewItemHolder) holder).bind(dataModel);



    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private class ViewItemHolder extends RecyclerView.ViewHolder {
        public ItemOrderBinding binding;

        public ViewItemHolder(ItemOrderBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.binding = itemRowBinding;
        }



        public void bind(OrderData product) {
             double price=0;
            for (ProductOrder order:product.getProducts()){
               price+= order.getSalePrice();
            }
            binding.txtPrice.setText("$"+price);
            binding.txtNumberOrder.setText("Order "+product.getId());
            binding.txtTimeReamining.setText(product.getOrderDate());

        }
    }


}
