package com.thesaugat.appcommerce.home.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thesaugat.appcommerce.R;
import com.thesaugat.appcommerce.api.ApiClient;
import com.thesaugat.appcommerce.api.response.Category;
import com.thesaugat.appcommerce.api.response.CategoryResponse;
import com.thesaugat.appcommerce.home.fragments.home.adapters.CategoryAdapter;
import com.thesaugat.appcommerce.utils.DataHolder;
import com.thesaugat.appcommerce.utils.UserInterfaceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment extends Fragment {
    RecyclerView fullCatRV;
    SwipeRefreshLayout swipeRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_category, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fullCatRV = view.findViewById(R.id.fullCatRV);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        if (DataHolder.categories != null) {
            showCategoryView(DataHolder.categories);
        } else {
            getOnline();
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOnline();
            }
        });
    }

    private void getOnline() {
        swipeRefresh.setRefreshing(true);
        Call<CategoryResponse> getCategories = ApiClient.getClient().getCategories();
        getCategories.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        DataHolder.categories = response.body().getCategories();
                        showCategoryView(response.body().getCategories());

                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void showCategoryView(List<Category> categories) {
        fullCatRV.setHasFixedSize(true);
        fullCatRV.setLayoutManager(new GridLayoutManager(getContext(), 4));
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, getContext(), true);
        fullCatRV.setAdapter(categoryAdapter);
    }
}