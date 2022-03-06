package com.sumanBasnet.pet_store.view_controller.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.Product;
import com.sumanBasnet.pet_store.view_controller.fragments.FragmentCart;
import com.sumanBasnet.pet_store.view_controller.fragments.FragmentFavourite;
import com.sumanBasnet.pet_store.view_controller.fragments.FragmentHome;
import com.sumanBasnet.pet_store.view_controller.fragments.FragmentOrder;
import com.sumanBasnet.pet_store.view_controller.fragments.FragmentProfile;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.List;

public class UserDasboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    Bundle bundle;
    LoginResponse response;
   public Product selectedProduct=null;
    private long backpresedtime;
    public List<Product> allproducts=null;
    private Toast backtoast;
    private float finalSum;

    public float getFinalSum() {
        return finalSum;
    }

    public void setFinalSum(float finalSum) {
        this.finalSum = finalSum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dasboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.UserMenu);
        bundle = getIntent().getExtras();

        Gson gson = new Gson();
       response = gson.fromJson(getIntent().getStringExtra("user_details"), LoginResponse.class);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentHome(),"FragmentHome").addToBackStack("FragmentHome").setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
            navigationView.setCheckedItem(R.id.home);
        }

    }

    @Override
    public void onBackPressed() {




        if (drawerLayout.isDrawerOpen((GravityCompat.START))) {

            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

String active=getActiveFragment();
if(active.equals("FragmentHome")){


            if (backpresedtime + 2000 > System.currentTimeMillis()) {
                backtoast.cancel();
                moveTaskToBack(true);
                super.onBackPressed();
                return;
            } else {

                backtoast = Toast.makeText(UserDasboard.this, "Press back again to exit", Toast.LENGTH_LONG);
                backtoast.show();
            }
            backpresedtime = System.currentTimeMillis();

}else{
    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentHome(),"FragmentHome").addToBackStack("FragmentHome").setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();

}


        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentHome(),"FragmentHome").addToBackStack("FragmentHome").setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
                navigationView.setCheckedItem(R.id.home);
                break;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentProfile(),"FragmentProfile").addToBackStack("FragmentProfile").setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
                navigationView.setCheckedItem(R.id.profile);
                break;
            case R.id.cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentCart(),"FragmentCart").addToBackStack("FragmentCart").setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
                navigationView.setCheckedItem(R.id.cart);
                break;

            case R.id.favourite:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentFavourite(),"FragmentFavourite").addToBackStack("FragmentFavourite").setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
                navigationView.setCheckedItem(R.id.favourite);
                break;

            case R.id.orders:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentOrder(),"FragmentOrder").addToBackStack("FragmentOrder").setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
                navigationView.setCheckedItem(R.id.orders);

                break;

            case R.id.menuAbout:
                AlertDialog.Builder builder = new AlertDialog.Builder(UserDasboard.this);

                builder.setTitle("About Pet Store");
                builder.setMessage("Developed By team LastMinute"+"\r\n"+
                        "Online Pet Store App"+"\r\n"+
                        "Team Members"+"\r\n"
                        +"Prakash Niraula"+"\r\n"+"Sangam Shrestha"+"\r\n"+"Salu Dahal"+"\r\n"+"Rabina Ale"+"\r\n"+"Prestha Ghimire"+"\r\n"+"Tooga Magar");

                AlertDialog alert = builder.create();
                alert.show();


                break;

            case R.id.menuMessage:


                break;

            case R.id.Logout:
                startActivity(new Intent(UserDasboard.this, Login.class) );
                finish();


                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    public LoginResponse getMyData() {
        return response;
    }

    public void setMydata(LoginResponse loginResponse) {
        this.response=loginResponse;
    }

    public boolean setSelectedProduct( Product p){
        selectedProduct=p;
        return true;
    }

    public List<Product> getAllproducts() {
        return allproducts;
    }

    public void setAllproducts(List<Product> allproducts) {
        this.allproducts = allproducts;
    }

    public Product getSelectedProduct(){
        return selectedProduct;
    }

    public String getActiveFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return tag;
    }



}