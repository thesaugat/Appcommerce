package com.thesaugat.appcommerce.api;

import com.thesaugat.appcommerce.api.response.AllProductResponse;
import com.thesaugat.appcommerce.api.response.CategoryResponse;
import com.thesaugat.appcommerce.api.response.LoginResponse;
import com.thesaugat.appcommerce.api.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/v1/register")
    Call<RegisterResponse> register(@Field("name") String names, @Field("email") String email, @Field("password") String password);

    @GET("/api/v1/get-all-products")
    Call<AllProductResponse> getAllProducts();

    @GET("/api/v1/get-categories")
    Call<CategoryResponse> getCategories();
}
