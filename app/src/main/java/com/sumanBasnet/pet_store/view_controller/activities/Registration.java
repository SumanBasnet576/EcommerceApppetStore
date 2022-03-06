package com.sumanBasnet.pet_store.view_controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.module.User;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;
import com.sumanBasnet.pet_store.view_controller.fragments.Register;
import com.sumanBasnet.pet_store.view_controller.fragments.registerSecond;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity implements Register.RegisterStep1Clicked,registerSecond.RegisterFinish{
    FloatingActionButton button;
    Register register;
    registerSecond registersecond;
    String[] detailsfromfrag1;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.regholder,new Register())
                .commit();


    }

    @Override
    public void ondatasent(String[] details) {
        detailsfromfrag1=details;


//data is getting sent here on order   name[0]=firstname.getText().toString();
//                name[1]=lastname.getText().toString();
//                name[2]=email.getText().toString();
//                name[3]=phone.getText().toString();
//                name[4]=address.getText().toString();


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.regholder,new registerSecond())
                .commit();

    }

    @Override
    public void createUser(String[] details) {

//data in this order
//        finaldata[0]=password.getText().toString();
//
//        finaldata[1]=city.getText().toString();
//        finaldata[2]=state.getText().toString();
//
//        finaldata[3]=locality.getText().toString();
//        finaldata[4]=postalcode.getText().toString();
//        finaldata[5]="user";  //it is usertypes


        User user=new User(detailsfromfrag1[2],details[0]);
        user.setFname(detailsfromfrag1[0]);
        user.setLname(detailsfromfrag1[1]);
        user.setPhone(detailsfromfrag1[3]);
        user.setCity(details[1]);
        user.setLocality(details[3]);
        user.setState(details[2]);
        user.setRole("buyer");

        Call<User> register=apiInterface.register(user);
        register.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.code()==200){
                    startActivity(new Intent(Registration.this,Login.class));
                    finish();
                }
                else{
                    Toast.makeText(Registration.this, "Invalid parameters, Try Agains", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Toast.makeText(Registration.this, "Something went wrong. Try again", Toast.LENGTH_LONG).show();

            }
        });



    }
}
