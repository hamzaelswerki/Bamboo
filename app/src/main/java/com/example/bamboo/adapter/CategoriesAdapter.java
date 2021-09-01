package com.example.bamboo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bamboo.R;
import com.example.bamboo.callBack.OnClickCategoryRecycleViewListener;
import com.example.bamboo.databinding.ItemCatBinding;
import com.example.bamboo.model.entity.Category;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Category> mValues;
    private Context context;
    public int selected_position = 0;

    private OnClickCategoryRecycleViewListener onItemClickRecycleView;

    public CategoriesAdapter(Context context, List<Category> items
                                   , OnClickCategoryRecycleViewListener onItemClickRecycleView
                               ) {
        mValues = items;
        this.context = context;
        this.onItemClickRecycleView = onItemClickRecycleView;

    }
   public int getSelected_position(){
        return  selected_position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemCatBinding binding = ItemCatBinding.
                inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ViewItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {


        ViewItemHolder viewItemHolder = (ViewItemHolder) holder;
        final Category dataModel = mValues.get(position);
        ((ViewItemHolder) holder).bind(dataModel);

        if (selected_position==position){
            viewItemHolder.binding.catName.setBackground( context.getResources().getDrawable(R.drawable.ic_bc_blc)) ;
                viewItemHolder.binding.catName.setTextColor(context.getResources().getColor(R.color.material_white));
        }else {
            viewItemHolder.binding.catName.setBackground( context.getResources().getDrawable(R.drawable.ic_bc_white)) ;
            viewItemHolder.binding.catName.setTextColor(context.getResources().getColor(R.color.colorTextBlack));

        }




    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private class ViewItemHolder extends RecyclerView.ViewHolder {

        public ItemCatBinding binding;
        public ViewItemHolder(ItemCatBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.binding = itemRowBinding;

            binding.catName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
                    notifyItemChanged(selected_position);
                    selected_position = getAdapterPosition();
                    notifyItemChanged(selected_position);

                    if (onItemClickRecycleView != null)
                        onItemClickRecycleView.OnViewClick(mValues.get(selected_position), selected_position);

                }
            });
        }




        public void bind(Category category) {
            binding.catName.setText(category.getName());
          }
    }




}
