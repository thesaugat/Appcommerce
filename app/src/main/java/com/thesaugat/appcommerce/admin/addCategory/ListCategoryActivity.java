package com.thesaugat.appcommerce.admin.addCategory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.thesaugat.appcommerce.R;
import com.thesaugat.appcommerce.api.ApiClient;
import com.thesaugat.appcommerce.api.response.Category;
import com.thesaugat.appcommerce.api.response.CategoryResponse;
import com.thesaugat.appcommerce.api.response.RegisterResponse;
import com.thesaugat.appcommerce.home.fragments.home.adapters.CategoryAdapter;
import com.thesaugat.appcommerce.utils.DataHolder;
import com.thesaugat.appcommerce.utils.SharedPrefUtils;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCategoryActivity extends AppCompatActivity {
    RecyclerView fullCatRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("All Categories");
        setContentView(R.layout.activity_add_category);
        fullCatRV = findViewById(R.id.fullCatRV);
        getOnline();
    }


    private void getOnline() {
//        swipeRefresh.setRefreshing(true);
        Call<CategoryResponse> getCategories = ApiClient.getClient().getCategories();
        getCategories.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
//                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        DataHolder.categories = response.body().getCategories();
                        showCategoryView(response.body().getCategories());

                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
//                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void showCategoryView(List<Category> categories) {
        fullCatRV.setHasFixedSize(true);
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this, false);
        fullCatRV.setLayoutManager(new GridLayoutManager(this, 1));
        fullCatRV.setAdapter(categoryAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Remove item from backing list here
                if (swipeDir > 0) {
                    String key = SharedPrefUtils.getString(ListCategoryActivity.this, getString(R.string.api_key));
                    Call<RegisterResponse> responseCall = ApiClient.getClient().deleteCategory(key, categories.get(viewHolder.getAdapterPosition()).getId());
                    responseCall.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            categories.remove(viewHolder.getAdapterPosition());
                            categoryAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {

                        }
                    });
                }


            }
        });
        itemTouchHelper.attachToRecyclerView(fullCatRV);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}