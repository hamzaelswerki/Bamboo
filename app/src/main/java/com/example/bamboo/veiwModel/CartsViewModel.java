package com.example.bamboo.veiwModel;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bamboo.model.entity.CheckoutResponse;
import com.example.bamboo.model.entity.Data;
import com.example.bamboo.model.entity.OrderStatus;
import com.example.bamboo.model.entity.Product;
import com.example.bamboo.model.entity.Status2;
import com.example.bamboo.model.entity.StatusForCarts;
import com.example.bamboo.model.entity.StatusForCheckOut;
import com.example.bamboo.model.entity.StatusFv;
import com.example.bamboo.model.network.ApiClient;
import com.example.bamboo.model.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartsViewModel extends AndroidViewModel {

    ApiInterface apiInterface;
    public MutableLiveData<StatusForCarts> statusForCartsMutableLiveData;
    public MutableLiveData<StatusForCheckOut> statusForCheckOutMutableLiveData;
    public MutableLiveData<StatusFv> deleteStatusLiveData;
    public MutableLiveData<CheckoutResponse> responseMutableLiveData;
    public MutableLiveData<OrderStatus> orderStatusMutableLiveData;
    public MutableLiveData<StatusForCarts> decrementliveData;

    public CartsViewModel(@NonNull Application application) {
        super(application);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        statusForCartsMutableLiveData = new MutableLiveData<>();
        statusForCheckOutMutableLiveData = new MutableLiveData<>();
        deleteStatusLiveData = new MutableLiveData<>();
        responseMutableLiveData = new MutableLiveData<>();
        orderStatusMutableLiveData = new MutableLiveData<>();
        decrementliveData = new MutableLiveData<>();

    }

    public MutableLiveData<StatusForCarts> addProductToCarts(int productID,String token) {
        apiInterface.addToCart(productID).enqueue(new Callback<StatusForCarts>() {
            @Override
            public void onResponse(Call<StatusForCarts> call, Response<StatusForCarts> response) {
                if (response.isSuccessful()){
                    Log.d("ttt","isSuccessful dddddddddd");
                    statusForCartsMutableLiveData.setValue(response.body());
                   }else {
                    Log.d("ttt","isSuccessful Error!=-"+response.toString());

                }
            }
            @Override
            public void onFailure(Call<StatusForCarts> call, Throwable t) {
                statusForCartsMutableLiveData.setValue(null);
                Log.d("ttt"," onFailurerrrr :"+call.toString());
                    t.printStackTrace();
            }
        });
        return  statusForCartsMutableLiveData;
     }

    public void getProductInCheckOut() {
        apiInterface.getCheckOutProducts().enqueue(new Callback<StatusForCheckOut>() {
            @Override
            public void onResponse(Call<StatusForCheckOut> call, Response<StatusForCheckOut> response) {
                if (response.isSuccessful()){
                    statusForCheckOutMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<StatusForCheckOut> call, Throwable t) {
                Log.d("ttt","Error CheckOut");

                statusForCheckOutMutableLiveData.setValue(null);

            }
        });
     }

    public  MutableLiveData<StatusFv>   deleteProductFromCheckOut(int product_id) {
        apiInterface.deleteItemFromCheckout(product_id).enqueue(new Callback<StatusFv>() {
            @Override
            public void onResponse(Call<StatusFv> call, Response<StatusFv> response) {
                if (response.isSuccessful()){
                    deleteStatusLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<StatusFv> call, Throwable t) {
                deleteStatusLiveData.setValue(null);
                Log.d("ttt",call.toString());
            }
        });
        return  deleteStatusLiveData;
    }


    public MutableLiveData<CheckoutResponse> uploadAllCheckouts() {
        apiInterface.uploadAllCheckout().enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                if (response.isSuccessful()){
                    responseMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                responseMutableLiveData.setValue(null);
                t.printStackTrace();
            }
        });
        return  responseMutableLiveData;
    }

  public MutableLiveData<OrderStatus> getOrders() {
        apiInterface.getOrders().enqueue(new Callback<OrderStatus>() {
            @Override
            public void onResponse(Call<OrderStatus> call, Response<OrderStatus> response) {
                if (response.isSuccessful()){
                    orderStatusMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<OrderStatus> call, Throwable t) {
                orderStatusMutableLiveData.setValue(null);
                t.printStackTrace();

            }
        });
        return  orderStatusMutableLiveData;
    }



    public  MutableLiveData<StatusForCarts>  decrementProduct(int product_id) {
        apiInterface.decrementQuantity(product_id).enqueue(new Callback<StatusForCarts>() {
            @Override
            public void onResponse(Call<StatusForCarts> call, Response<StatusForCarts> response) {
                if (response.isSuccessful()){
                    decrementliveData.setValue(response.body());
                    Log.d("ttt","sycccc decrement");
                }
            }

            @Override
            public void onFailure(Call<StatusForCarts> call, Throwable t) {
                decrementliveData.setValue(null);
                Log.d("ttt",call.toString());
            }
        });
        return  decrementliveData;
    }

}
