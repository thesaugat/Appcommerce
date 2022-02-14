package com.thesaugat.appcommerce.home.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thesaugat.appcommerce.R;
import com.thesaugat.appcommerce.admin.AdminActivity;
import com.thesaugat.appcommerce.checkout.address.AddressActivity;
import com.thesaugat.appcommerce.userAccount.UserAccountActivity;
import com.thesaugat.appcommerce.utils.SharedPrefUtils;
import com.thesaugat.appcommerce.utils.UserInterfaceUtils;


public class ProfileFragment extends Fragment {
    TextView logOutTV, adminAreaTV, addressTV;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logOutTV = view.findViewById(R.id.logOutTV);
        adminAreaTV = view.findViewById(R.id.adminAreaTV);
        addressTV = view.findViewById(R.id.addressTV);
        checkAdmin();
        setClickListeners();
    }

    private void checkAdmin() {
        boolean is_staff = SharedPrefUtils.getBool(getActivity(), getString(R.string.staff_key), false);
        if (is_staff)
            adminAreaTV.setVisibility(View.VISIBLE);
    }

    private void setClickListeners() {
        logOutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefUtils.clear(getContext());
                Intent userAccount = new Intent(getContext(), UserAccountActivity.class);
                startActivity(userAccount);
                getActivity().finish();
            }
        });
        adminAreaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminActivity.class);
                startActivity(intent);
            }
        });
        addressTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
            }
        });
    }

}