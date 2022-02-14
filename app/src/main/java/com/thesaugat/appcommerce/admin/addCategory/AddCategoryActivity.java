package com.thesaugat.appcommerce.admin.addCategory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.thesaugat.appcommerce.R;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "".toString());
    }
}