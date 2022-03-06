package com.sumanBasnet.pet_store.view_controller.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.Product;
import com.sumanBasnet.pet_store.module.Sales;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;
import com.sumanBasnet.pet_store.view_controller.fragments.AddPet;
import com.sumanBasnet.pet_store.view_controller.fragments.AdminHome;
import com.google.gson.Gson;

public class AdminDashboard extends AppCompatActivity implements AdminHome.addpetButtonClicked, AddPet.buttonclicked{

    AdminHome adminhome;
    private long backpresedtime;
    private Toast backtoast;
    Bundle bundle;
    LoginResponse response;
    private Product selectedProduct;
    Sales sales;
    APIInterface apiInterface;


    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        bundle = getIntent().getExtras();
        apiInterface= APIClient.getClient().create(APIInterface.class);
        Gson gson = new Gson();
        response = gson.fromJson(getIntent().getStringExtra("user_details"), LoginResponse.class);
        getSupportFragmentManager().beginTransaction().replace(R.id.admincontainer, new AdminHome(),"AdminHome").addToBackStack("AdminHome").setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();


    }

    @Override
    public void onBackPressed() {

            String active=getActiveFragment();
            if(active.equals("AdminHome")){


                if (backpresedtime + 2000 > System.currentTimeMillis()) {
                    backtoast.cancel();
                    moveTaskToBack(true);
                    super.onBackPressed();
                    return;
                } else {

                    backtoast = Toast.makeText(AdminDashboard.this, "Press back again to exit", Toast.LENGTH_LONG);
                    backtoast.show();
                }
                backpresedtime = System.currentTimeMillis();

            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.admincontainer, new AdminHome(),"AdminHome").addToBackStack("AdminHome").setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
            }
        }




    @Override
    public void createpet(String[] details) {
        getSupportFragmentManager().beginTransaction().replace(R.id.admincontainer, new AddPet(),"AddPet").addToBackStack("AddPet").setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();

    }


    @Override
    public void movetonext() {

    }


    public LoginResponse getMyData() {
        return response;
    }

    public LoginResponse setMyData(LoginResponse lr){
        this.response=lr;
        return this.response;
    }


    public String getActiveFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return tag;
    }


    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }
}