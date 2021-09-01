package com.example.bamboo.model.network;


import android.provider.ContactsContract;

import com.example.bamboo.model.entity.CheckoutResponse;
import com.example.bamboo.model.entity.Data;
import com.example.bamboo.model.entity.OrderData;
import com.example.bamboo.model.entity.OrderStatus;
import com.example.bamboo.model.entity.Status;
import com.example.bamboo.model.entity.Status2;
import com.example.bamboo.model.entity.StatusForCarts;
import com.example.bamboo.model.entity.StatusForCheckOut;
import com.example.bamboo.model.entity.StatusFv;
import com.example.bamboo.model.entity.StatusToShowFv;
import com.example.bamboo.model.entity.User;
import com.example.bamboo.model.entity.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {



  @FormUrlEncoded
  @POST("login")
  Call<Status>loginUser(@Field("phone_number") String phone_number,
                              @Field("password") String password);


  @FormUrlEncoded
    @POST("register_new_customer")
    Call<Status>registerNewUser2(@Field("phone_number") String phone_number,
                                @Field("password") String password,
                                 @Field("name") String name,
                                 @Field("email") String email,
                                 @Field("address") String address,
                                 @Field("image") String image);



    @FormUrlEncoded
    @POST("customer/update")
    Call<Status>updateCustomer(
            @Field("name") String name,
                                @Field("email") String email,
                               @Field("address") String address,
                               @Field("image") String image,
                               @Field("text_image") String text_image
                              );


  @GET("new_arrival")
  Call<Data>getNewProducts();

  @GET("products")
  Call<Data>getBigSales();

  @GET("hot_offers")
  Call<Data>getHotOffers();

  @GET("categories")
  Call<Status2>getCategories();

  @GET("products")
  Call<Data>getProductsByCategories(@Query("search")String search,
                                    @Query("category_id")int CategoryId);

  @FormUrlEncoded
  @POST("cart/store")
  Call<StatusForCarts>addToCart(@Field("product_id")int product_id);


@Headers({ "Content-Type: application/json;charset=UTF-8"})
  @GET("cart")
  Call<StatusForCheckOut>getCheckOutProducts();


  @FormUrlEncoded
  @POST("wishlist")
  Call<StatusFv>addProductToFav(@Field("product_id") int product_id);


  @GET("wishlist")
  Call<StatusToShowFv>getFvProducts();




  @POST("wishlist/destroy/{product_id}")
  Call<StatusFv>deleteItemFromFv(@Path("product_id")int product_id);


  @POST("cart/delete/{product_id}")
  Call<StatusFv>deleteItemFromCheckout(@Path("product_id")int product_id);


  @POST("checkout")
  Call<CheckoutResponse>uploadAllCheckout();



  @Headers({ "Content-Type: application/json;charset=UTF-8"})
  @GET("orders")
  Call<OrderStatus>getOrders();


@Headers({ "Content-Type: application/json;charset=UTF-8"})
  @GET("customer/show")
  Call<UserResponse>getUserData();



  @POST("cart/{product_id}")
  Call<StatusForCarts>decrementQuantity(@Path("product_id")int product_id);




  @GET("products")
  Call<Data>getProductsByPrice(@Query("search")String search,
                                    @Query("category_id")int CategoryId,
                                    @Query("min_price")int min_price,
                                    @Query("max_price")int max_price
                            );



}



