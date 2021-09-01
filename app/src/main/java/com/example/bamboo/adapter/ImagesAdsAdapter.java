package com.example.bamboo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.bamboo.R;
import com.example.bamboo.model.entity.Product;

import java.util.List;

public class ImagesAdsAdapter extends PagerAdapter {

  Context context;
    List<String> listUrlImages;
    List<Product> adsList;
    static int myPosition;
    OnImageListener onImageListener;
    Boolean showTv;

    public  interface OnImageListener{
        void onImageClicked(String url);
    }
   public void setOnImageListener(OnImageListener onImageListener){
        this.onImageListener=onImageListener;
    }

    public ImagesAdsAdapter(Context context, List<Product> adsList,Boolean showTv){
          this.context=context;
          this.adsList=adsList;
          this.showTv=showTv;
     }
    @Override
    public int getCount() {
        return adsList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
         View view = LayoutInflater.from(context).inflate(R.layout.item_ads_row, null);
        ImageView imageView = view.findViewById(R.id.img_pager);
        TextView textView = view.findViewById(R.id.textView15);

          if (adsList.get(position)!=null)
              Glide.with(context).load(adsList.get(position).getMainImage()).into(imageView);

          if (showTv){
              textView.setVisibility(View.VISIBLE);
              textView.setText("Up to "+adsList.get(position).getDiscount()+"OFF");
          }else {
              textView.setVisibility(View.GONE);

          }
        // imageView.setImageURI(adsList.get(position));

         myPosition=position+1;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (onImageListener!=null)
             //   onImageListener.onImageClicked(adsList.get(position).getLink());
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return object == view;
    }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((View) view);
        }

}
