package com.example.onlineshop.view.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityLoginSignupBinding;
import com.example.onlineshop.view.MainActivity;

public class Login_Signup_Activity extends AppCompatActivity {

    ActivityLoginSignupBinding binding;
    private static String TAG = "LoginSignup";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.logged_in_number_file),MODE_PRIVATE);

        if (sharedPreferences.getString(getString(R.string.logged_in_number_KEY), null) == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_login_signup);
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }


    }
}