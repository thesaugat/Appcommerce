package com.thesaugat.appcommerce.home.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.thesaugat.appcommerce.R;
import com.thesaugat.appcommerce.api.ApiClient;
import com.thesaugat.appcommerce.api.response.AllProductResponse;
import com.thesaugat.appcommerce.api.response.Product;
import com.thesaugat.appcommerce.home.fragments.home.adapters.ShopAdapter;
import com.thesaugat.appcommerce.utils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {
    RecyclerView allProductRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allProductRV = view.findViewById(R.id.allProductRV);

        getCartItems();
    }

    private void getCartItems() {
        String key = SharedPrefUtils.getString(getActivity(), getString(R.string.api_key));
        Call<AllProductResponse> cartItemsCall = ApiClient.getClient().getMyCart(key);
        cartItemsCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError())
                    {
                        loadCartList(response.body().getProducts());
                    }
                }
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {

            }
        });
    }

    private void loadCartList(List<Product> products) {
        allProductRV.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        allProductRV.setLayoutManager(layoutManager);
        ShopAdapter shopAdapter = new ShopAdapter(products, getContext(),true);
        allProductRV.setAdapter(shopAdapter);
    }
}