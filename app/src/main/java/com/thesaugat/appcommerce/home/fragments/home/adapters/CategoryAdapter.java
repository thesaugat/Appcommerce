package com.thesaugat.appcommerce.home.fragments.home.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thesaugat.appcommerce.R;
import com.thesaugat.appcommerce.api.response.Category;
import com.thesaugat.appcommerce.categoryActivity.CategoryActivity;
import com.thesaugat.appcommerce.utils.DataHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<Category> categories;
    LayoutInflater inflater;
    Context context;
    Boolean showImage;
    Boolean select ;
    Activity activity;

    public CategoryAdapter(List<Category> categories, Context context, Boolean showImage, Boolean select, Activity activity) {
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.showImage = showImage;
        this.select = select;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (showImage)
            return new CategoryViewHolder(inflater.inflate(R.layout.item_category, parent, false));
        else
            return new CategoryViewHolder(inflater.inflate(R.layout.item_category_no_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.catNameTV.setText(categories.get(position).getName());
        Picasso.get().load(categories.get(position).getCategoryImage()).into(holder.catIV);
        holder.categoryItemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select) {
                    DataHolder.category = categories.get(holder.getAdapterPosition());
                    activity.finish();

                } else {
                    Intent intent = new Intent(context, CategoryActivity.class);
                    intent.putExtra(CategoryActivity.CATEGORY_DATA_KEY, categories.get(holder.getAdapterPosition()));
                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        CircleImageView catIV;
        TextView catNameTV;
        LinearLayout categoryItemLL;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catIV = itemView.findViewById(R.id.catIV);
            catNameTV = itemView.findViewById(R.id.catNameTV);
            categoryItemLL = itemView.findViewById(R.id.categoryItemLL);
        }
    }
}
