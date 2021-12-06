package com.thesaugat.appcommerce.singleProductPage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.thesaugat.appcommerce.R;
import com.thesaugat.appcommerce.api.response.Product;

public class SingleProductActivity extends AppCompatActivity {
  public   static String key ="pKey";
  Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        if(getIntent().getSerializableExtra(key)!= null){
            product = (Product) getIntent().getSerializableExtra(key) ;
            Toast.makeText(this, product.getName(), Toast.LENGTH_SHORT).show();
        }

    }
}