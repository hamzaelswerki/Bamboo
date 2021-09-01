package com.example.bamboo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.callBack.OnProductClickListener;
import com.example.bamboo.databinding.ItemProductHomeBinding;
import com.example.bamboo.model.entity.Product;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> mValues;
    private Context context;
    private OnProductClickListener onProductClickListener;

    public ProductsAdapter(Context context, List<Product> items
                                  , OnProductClickListener onProductClickListener
                               ) {
        mValues = items;
        this.context = context;
        this.onProductClickListener = onProductClickListener;

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemProductHomeBinding binding = ItemProductHomeBinding.
                inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ViewItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        ViewItemHolder viewItemHolder = (ViewItemHolder) holder;
        final Product dataModel = mValues.get(position);
        ((ViewItemHolder) holder).bind(dataModel);

        ((ViewItemHolder) holder).binding.imgP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductClickListener!=null)
                    onProductClickListener.OnProductClicked(dataModel);
            }
        });

        ((ViewItemHolder) holder).binding.imgcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (onProductClickListener!=null)
                        onProductClickListener.OnAddToCartClicked(dataModel);
            }});

        ((ViewItemHolder) holder).binding.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductClickListener!=null)
                    onProductClickListener.OnAddToFavClicked(dataModel);
            }});
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private static class ViewItemHolder extends RecyclerView.ViewHolder {
        public ItemProductHomeBinding binding;

        public ViewItemHolder(ItemProductHomeBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.binding = itemRowBinding;
        }



        public void bind(Product product) {
            Log.d("ttt",product.getMainImage());
            binding.imgP.setImageURI(product.getMainImage());
            binding.name.setText(product.getName());
            binding.price.setText(product.getSalePrice()+"₪");
            binding.discrption.setText(product.getShortDescription());
        }
    }


}
