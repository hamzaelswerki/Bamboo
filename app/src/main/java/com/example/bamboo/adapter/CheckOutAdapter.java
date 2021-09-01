package com.example.bamboo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.databinding.ItemCheckOutBinding;
import com.example.bamboo.model.entity.CheckOut;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CheckOutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CheckOut> mValues;
    private Context context;

    public interface  OnQuantiyChanged{
        void onClickedPlusButton(TextView tv,int productId );
        void onClickedMinusButton(TextView tv,int productId );
    }
    private OnQuantiyChanged onQuantiyChanged;

   public void setOnQuantiyChanged( OnQuantiyChanged onQuantiyChanged){
        this.onQuantiyChanged=onQuantiyChanged;
    }
    public CheckOutAdapter(Context context, List<CheckOut> items
                                  //, OnProductClickListener onProductClickListener
                               ) {
        mValues = items;
        this.context = context;
       // this.onProductClickListener = onProductClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemCheckOutBinding binding = ItemCheckOutBinding.
                inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ViewItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        ViewItemHolder viewItemHolder = (ViewItemHolder) holder;
        final CheckOut dataModel = mValues.get(position);
        ((ViewItemHolder) holder).bind(dataModel);

        ((ViewItemHolder) holder).binding.imgP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (onProductClickListener!=null)
                 //   onProductClickListener.OnProductClicked(dataModel);
            }
        });
        ((ViewItemHolder) holder).binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onQuantiyChanged!=null){
                    onQuantiyChanged.onClickedPlusButton(  ((ViewItemHolder) holder).binding.tvNum,dataModel.getId());
                }
            }
        });
        ((ViewItemHolder) holder).binding.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onQuantiyChanged!=null){
                    onQuantiyChanged.onClickedMinusButton(  ((ViewItemHolder) holder).binding.tvNum,dataModel.getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private static class ViewItemHolder extends RecyclerView.ViewHolder {
        public ItemCheckOutBinding binding;

        public ViewItemHolder(ItemCheckOutBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.binding = itemRowBinding;
        }



        public void bind(CheckOut product) {
            binding.imgP.setImageURI(product.getProductImage());
            binding.name.setText(product.getProduct());
            binding.price.setText(product.getPrice()+"$");
            binding.discrption.setText(product.getCategory());
            binding.tvNum.setText(product.getQuantity()+"");
        }
    }


}
