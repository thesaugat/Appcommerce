package com.thesaugat.appcommerce.home.fragments.home;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.thesaugat.appcommerce.R;
import com.thesaugat.appcommerce.api.ApiClient;
import com.thesaugat.appcommerce.api.response.AllProductResponse;
import com.thesaugat.appcommerce.api.response.Category;
import com.thesaugat.appcommerce.api.response.CategoryResponse;
import com.thesaugat.appcommerce.api.response.Product;
import com.thesaugat.appcommerce.home.fragments.home.adapters.CategoryAdapter;
import com.thesaugat.appcommerce.home.fragments.home.adapters.ShopAdapter;
import com.thesaugat.appcommerce.utils.DataHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    RecyclerView allProductRV, categoryRV;
    ProgressBar loadingProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allProductRV = view.findViewById(R.id.allProductRV);
        categoryRV = view.findViewById(R.id.categoryRV);
        loadingProgress = view.findViewById(R.id.loadingProgress);
        serverCall();
        getCategoriesOnline();
    }

    private void getCategoriesOnline() {
        Call<CategoryResponse> getCategories = ApiClient.getClient().getCategories();
        getCategories.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        DataHolder.categories = response.body().getCategories();
                        showCategories(response.body().getCategories());

                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });

    }

    private void showCategories(List<Category> categories) {
        List<Category> temp;
        if (categories.size() > 8) {
            temp = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                temp.add(categories.get(categories.size()-i-1));
            }
        } else {
            temp = categories;
        }
        categoryRV.setHasFixedSize(true);
        categoryRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CategoryAdapter categoryAdapter = new CategoryAdapter(temp, getContext());
        categoryRV.setAdapter(categoryAdapter);

    }

    private void serverCall() {
        toggleLoading(true);
        Call<AllProductResponse> allProductResponseCall = ApiClient.getClient().getAllProducts();
        allProductResponseCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                toggleLoading(false);
                setProdctRecyclerView(response.body().getProducts());

            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                toggleLoading(false);
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setProdctRecyclerView(List<Product> products) {
        allProductRV.setHasFixedSize(true);
        allProductRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ShopAdapter shopAdapter = new ShopAdapter(products, getContext());
        allProductRV.setAdapter(shopAdapter);
    }

    void toggleLoading(boolean toggle) {
        if (toggle)
            loadingProgress.setVisibility(View.VISIBLE);
        else
            loadingProgress.setVisibility(View.GONE);
    }
}