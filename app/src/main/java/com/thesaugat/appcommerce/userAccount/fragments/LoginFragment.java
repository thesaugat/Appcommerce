package com.thesaugat.appcommerce.userAccount.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.thesaugat.appcommerce.home.MainActivity;
import com.thesaugat.appcommerce.R;
import com.thesaugat.appcommerce.utils.SharedPrefUtils;

public class LoginFragment extends Fragment implements View.OnClickListener {

    EditText emailEt, passwordET;
    LinearLayout loginBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEt = view.findViewById(R.id.emailET);
        passwordET = view.findViewById(R.id.passwordET);
        loginBtn = view.findViewById(R.id.loginLL);
        loginBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == loginBtn) {
            String email, password;
            email = emailEt.getText().toString();
            password = passwordET.getText().toString();
            if (email.isEmpty() && password.isEmpty())
                Toast.makeText(getContext(), "Email or Password is Empty!", Toast.LENGTH_LONG).show();
            else if (email.equals("thesaugatt@gmail.com") && password.equals("Pass123")) {
                SharedPrefUtils.setBoolean(getActivity(), getString(R.string.isLoggedKey), true);
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            } else
                Toast.makeText(getContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
        }

    }
}