package com.sumanBasnet.pet_store.view_controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.User;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private long backpresedtime;
    private Toast backtoast;
    TextView registerlink;
    private Button login;
    EditText email,password;
    APIInterface apiInterface;
    User user;

    @Override
    public void onBackPressed() {


        if (backpresedtime + 2000 > System.currentTimeMillis()) {
            backtoast.cancel();
            moveTaskToBack(true);
            super.onBackPressed();
            return;
        } else {

            backtoast = Toast.makeText(Login.this, "Press back again to exit", Toast.LENGTH_LONG);
            backtoast.show();
        }
        backpresedtime = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerlink = findViewById(R.id.linkRegister);
        email = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword2);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registration.class));
                finish();
            }
        });
        login = findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User(email.getText().toString(), password.getText().toString());
                Call<LoginResponse> login = apiInterface.login(user);
                login.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.code() != 200) {
                            Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                        //ROles of user admin,buyer and doctor
                            LoginResponse loginResponse = response.body();
                            Gson gson = new Gson();
                            String myJson = gson.toJson(loginResponse);
                            if (loginResponse.getRole().toString().equals("admin")) {
                                startActivity(new Intent(Login.this, AdminDashboard.class).putExtra("user_details",myJson));
                                finish();


                            } else {
                                startActivity(new Intent(Login.this, UserDasboard.class).putExtra("user_details",myJson));
                                finish();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                        Toast.makeText(Login.this, "Something went wrong. Try again", Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

    }
}