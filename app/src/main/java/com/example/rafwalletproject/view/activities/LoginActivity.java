package com.example.rafwalletproject.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rafwalletproject.R;

public class LoginActivity extends AppCompatActivity {

    //Code
    private static String PREFERENCES_NAME = "loginPreferences";

    //Views
    EditText edtFirstName, edtPassword, edtBank, edtLastname;
    Button btnLogin;
    TextView txtInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        initView();
        initListeners();
    }

    private void doLogin(String firstName, String lastName, String bank, String password){

        if (password.equals("password")) {
            SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

            sharedPreferences.edit().putString("firstName", firstName).apply();
            sharedPreferences.edit().putString("lastName", lastName).apply();
            sharedPreferences.edit().putString("bank", bank).apply();
            sharedPreferences.edit().putBoolean("loggedIn", true).apply();

            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else
            txtInfo.setText("Invalid Credentails");
    }

    private void initListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtFirstName.getText().toString().isEmpty() || edtLastname.getText().toString().isEmpty() ||
                        edtBank.getText().toString().isEmpty() || edtPassword.getText().toString().equals("")) {
                    txtInfo.setText("All fields are required");
                } else
                    doLogin(edtFirstName.getText().toString(), edtLastname.getText().toString(),
                            edtBank.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    private void initView() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastname = findViewById(R.id.edtLastName);
        edtBank = findViewById(R.id.edtBank);
        edtPassword = findViewById(R.id.edtPassword);
        edtFirstName.setText("");
        edtLastname.setText("");
        edtBank.setText("");
        edtPassword.setText("");
        btnLogin = findViewById(R.id.btnLogin);
        txtInfo = findViewById(R.id.txtInfo);
    }
}