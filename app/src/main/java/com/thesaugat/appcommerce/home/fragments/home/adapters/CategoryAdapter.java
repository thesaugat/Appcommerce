package com.thesaugat.appcommerce.home.fragments.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thesaugat.appcommerce.R;
import com.thesaugat.appcommerce.api.response.Category;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<Category> categories;
    LayoutInflater inflater;
    Context context;

    public CategoryAdapter(List<Category> categories,Context context) {
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(inflater.inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.catNameTV.setText(categories.get(position).getName());
        Picasso.get().load(categories.get(position).getCategoryImage()).into(holder.catIV);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        CircleImageView catIV;
        TextView catNameTV;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catIV = itemView.findViewById(R.id.catIV);
            catNameTV = itemView.findViewById(R.id.catNameTV);
        }
    }
}
