package com.example.bamboo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.callBack.OnProductClickListener;
import com.example.bamboo.databinding.ItemCatBinding;
import com.example.bamboo.databinding.ItemProductCategorBinding;
import com.example.bamboo.databinding.ItemProductHomeBinding;
import com.example.bamboo.model.entity.Product;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductsByCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> mValues;
    private Context context;
   private OnProductClickListener onProductClickListener;

    public ProductsByCategoryAdapter(Context context, List<Product> items
                                             , OnProductClickListener onProductClickListener
                               ) {
        mValues = items;
        this.context = context;
         this.onProductClickListener = onProductClickListener;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemProductCategorBinding binding = ItemProductCategorBinding.
                inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ViewItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        ViewItemHolder viewItemHolder = (ViewItemHolder) holder;
        final Product dataModel = mValues.get(position);
        ((ViewItemHolder) holder).bind(dataModel);
        ((ViewItemHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductClickListener!=null)
                    onProductClickListener.OnProductClicked(dataModel);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private static class ViewItemHolder extends RecyclerView.ViewHolder {
        public ItemProductCategorBinding binding;

        public ViewItemHolder(ItemProductCategorBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.binding = itemRowBinding;
        }



        public void bind(Product product) {

            binding.imgP.setImageURI(product.getMainImage());
            binding.name.setText(product.getName());
            binding.price.setText(product.getSalePrice()+"₪");
            binding.discrption.setText(product.getShortDescription());
        }
    }


}
