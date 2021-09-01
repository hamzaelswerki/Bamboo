package com.example.bamboo.veiwModel;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bamboo.model.entity.Data;
import com.example.bamboo.model.entity.Product;
import com.example.bamboo.model.entity.Status2;
import com.example.bamboo.model.entity.StatusFv;
import com.example.bamboo.model.entity.StatusToShowFv;
import com.example.bamboo.model.network.ApiClient;
import com.example.bamboo.model.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {

    ApiInterface apiInterface;
    public MutableLiveData<List<Product>> newProductsMutableLiveData;
    public MutableLiveData<List<Product>> hotOffersMutableLiveData;
    public MutableLiveData<List<Product>> bigSalesMutableLiveData;
    public MutableLiveData<Status2> categoriesMutableLiveData;
    public MutableLiveData<List<Product>> productsBYCategoriesLiveData;
    public MutableLiveData<StatusFv> addFavStatusLiveData;
    public MutableLiveData<List<Product>> showProductFvLiveData;
    public MutableLiveData<StatusFv> deleteFavStatusLiveData;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        newProductsMutableLiveData = new MutableLiveData<>();
        hotOffersMutableLiveData = new MutableLiveData<>();
        bigSalesMutableLiveData = new MutableLiveData<>();
        categoriesMutableLiveData = new MutableLiveData<>();
        productsBYCategoriesLiveData = new MutableLiveData<>();
        addFavStatusLiveData = new MutableLiveData<>();
        showProductFvLiveData = new MutableLiveData<>();
        deleteFavStatusLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Product>>  getNewProductsMutableLiveData() {
        apiInterface.getNewProducts().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.d("ttt","onResponse");

                if (response.isSuccessful()&&response.body()!=null)
                       newProductsMutableLiveData.setValue(response.body().getData());}
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                newProductsMutableLiveData.setValue(null);
            }
        });
        return newProductsMutableLiveData;
    }

    public  MutableLiveData<List<Product>>   getbigSalesMutableLiveData() {
        apiInterface.getBigSales().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.d("ttt","onResponse");

                if (response.isSuccessful()&&response.body()!=null)
                    bigSalesMutableLiveData.setValue(response.body().getData());}
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                bigSalesMutableLiveData.setValue(null);
            }
        });
        return bigSalesMutableLiveData;
    }

    public  MutableLiveData<List<Product>>   getHotOffersMutableLiveData() {
        apiInterface.getHotOffers().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.d("ttt","onResponse");

                if (response.isSuccessful()&&response.body()!=null)
                       hotOffersMutableLiveData.setValue(response.body().getData());}
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                hotOffersMutableLiveData.setValue(null);
            }
        });
        return hotOffersMutableLiveData;
    }

    public  MutableLiveData<Status2>   getCategoriesLiveData() {
        apiInterface.getCategories().enqueue(new Callback<Status2>() {
            @Override
            public void onResponse(Call<Status2> call, Response<Status2> response) {
                if (response.isSuccessful()){
                    categoriesMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Status2> call, Throwable t) {
                categoriesMutableLiveData.setValue(null);

            }
        });
        return  categoriesMutableLiveData;
    }


    public  void    getProductsBYCategoriesLiveData(String search,int categoryId) {
        apiInterface.getProductsByCategories(search,categoryId).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.d("ttt","onResponse");
                if (response.isSuccessful()&&response.body()!=null)
                    productsBYCategoriesLiveData.setValue(response.body().getData());}
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                productsBYCategoriesLiveData.setValue(null);
            }
        });
    }

    public  MutableLiveData<StatusFv>   addProductToFav(int product_id) {
        apiInterface.addProductToFav(product_id).enqueue(new Callback<StatusFv>() {
            @Override
            public void onResponse(Call<StatusFv> call, Response<StatusFv> response) {
                 if (response.isSuccessful()){
                     addFavStatusLiveData.setValue(response.body());
                 }
            }

            @Override
            public void onFailure(Call<StatusFv> call, Throwable t) {
                addFavStatusLiveData.setValue(null);
        Log.d("ttt",call.toString());
            }
        });
        return  addFavStatusLiveData;
    }



    public  void    getShowProductFvLiveData() {
        apiInterface.getFvProducts().enqueue(new Callback<StatusToShowFv>() {
            @Override
            public void onResponse(Call<StatusToShowFv> call, Response<StatusToShowFv> response) {
                Log.d("ttt","onResponse");

                if (response.isSuccessful()&&response.body()!=null){
                    showProductFvLiveData.setValue(response.body().getData());
                Log.d("ttt",response.body().getData().size()+"  SIzzeddd");
                }
            }
            @Override
            public void onFailure(Call<StatusToShowFv> call, Throwable t) {
                showProductFvLiveData.setValue(null);
            }
        });
    }



    public  MutableLiveData<StatusFv>   deleteProductFromFav(int product_id) {
        apiInterface.deleteItemFromFv(product_id).enqueue(new Callback<StatusFv>() {
            @Override
            public void onResponse(Call<StatusFv> call, Response<StatusFv> response) {
                if (response.isSuccessful()){
                    deleteFavStatusLiveData.setValue(response.body());
                    Log.d("ttt","============"+response.toString());
                }
            }

            @Override
            public void onFailure(Call<StatusFv> call, Throwable t) {
                deleteFavStatusLiveData.setValue(null);
                Log.d("ttt",call.toString());
            }
        });
        return  deleteFavStatusLiveData;
    }

    public  MutableLiveData<List<Product>>    getProductsByPriceLiveData(String search, int categoryId, int min, int max) {
        apiInterface.getProductsByPrice(search,categoryId,min,max).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()&&response.body()!=null)
                    productsBYCategoriesLiveData.setValue(response.body().getData());}
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                productsBYCategoriesLiveData.setValue(null);
            }
        });
        return productsBYCategoriesLiveData;
    }


}
