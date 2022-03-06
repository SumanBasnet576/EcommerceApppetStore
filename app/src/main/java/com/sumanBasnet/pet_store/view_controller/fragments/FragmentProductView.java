package com.sumanBasnet.pet_store.view_controller.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.module.Cart;
import com.sumanBasnet.pet_store.module.Category;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.Product;
import com.sumanBasnet.pet_store.module.ProductRow;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;
import com.sumanBasnet.pet_store.view_controller.activities.AdminDashboard;
import com.sumanBasnet.pet_store.view_controller.activities.UserDasboard;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProductView extends Fragment {

    Product selectedProduct;
    ImageView imageView;
    TextView name, price, availablepcs, category, description;
    Button button, addtofav;
    APIInterface apiInterface;
    LoginResponse lr;
    HashMap hashMap=new HashMap();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentproductview, container, false);


        hashMap = new HashMap();
        imageView = view.findViewById(R.id.imageView6);
        name = view.findViewById(R.id.textView15);
        price = view.findViewById(R.id.textView17);
        availablepcs = view.findViewById(R.id.textView19);
        category = view.findViewById(R.id.textView21);
        description = view.findViewById(R.id.textView22);
        button = view.findViewById(R.id.buttonAddtoCart);
        addtofav = view.findViewById(R.id.buttonaddtofav);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<Category>> getallcategory=apiInterface.getallcategory();
        getallcategory.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> cats=response.body();
                List<String> names=new ArrayList<>();
                for(int i=0;i<cats.size();i++){
                    hashMap.put(cats.get(i).getId(),cats.get(i).getCategoryName());
                }
                String value=selectedProduct.getCategory();
               category.setText(""+hashMap.get(value));
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong. Try again", Toast.LENGTH_LONG).show();
            }
        });

        if (getActivity() instanceof UserDasboard) {
            lr = ((UserDasboard) getActivity()).getMyData();
            selectedProduct = ((UserDasboard) getActivity()).getSelectedProduct();
        }

        if (getActivity() instanceof AdminDashboard) {
            lr = ((AdminDashboard) getActivity()).getMyData();
            addtofav.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            selectedProduct = ((AdminDashboard) getActivity()).getSelectedProduct();
        }
        addtofav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addproducttofav(selectedProduct);
            }
        });
        String modified = selectedProduct.getImage().replace("public", APIClient.BASE_URL);
        Picasso.get().load(modified).into(imageView);
        //Glide.with(getContext()).load(modified).into(imageView);

        name.setText(selectedProduct.getName());
        price.setText("Rs " + selectedProduct.getPrice());
        availablepcs.setText(selectedProduct.getAvailablepcs());
        description.setText(selectedProduct.getDescription());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchMycart();
            }
        });


        return view;
    }

    public void fetchMycart() {
        Call<Cart> fetchmyCart = apiInterface.getmycart("Bearer " + lr.getToken());
        fetchmyCart.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                Cart myCart = response.body();
                List<ProductRow> productsincart = myCart.getProduct();
                if (myCart.getProduct().size() > 0) {
                    for (int i = 0; i < productsincart.size(); i++) {
                        if (productsincart.get(i).getProductId().equals(selectedProduct.getId())) {
                            Toast.makeText(getContext(), "Product exists in cart", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    makeCall();

                } else {

                    makeCall();

                }

            }

            @Override
            public void onFailure(Call<Cart> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void makeCall() {


        ProductRow pr = new ProductRow();
        pr.setProductId(selectedProduct.getId());
        pr.setProductName(selectedProduct.getName());
        pr.setProductImage(selectedProduct.getImage());
        pr.setQuantity("1");
        pr.setSellerId(selectedProduct.getSellerId());
        pr.setTotal(selectedProduct.getPrice());
        pr.setProductPrice(selectedProduct.getPrice());

        Call<Cart> addtocart = apiInterface.posttoCart("Bearer " + lr.getToken(), pr);
        addtocart.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Confirm");
                builder.setMessage("Added to cart. View cart?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        getParentFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentCart(), "FragmentCart").addToBackStack("FragmentCart").commit();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }

            @Override
            public void onFailure(Call<Cart> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void addproducttofav(Product product) {

        Call<LoginResponse> addtofav = apiInterface.addFavourite("Bearer " + lr.getToken(), product);
        addtofav.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse newlr = response.body();
                newlr.setToken(lr.getToken());
                ((UserDasboard) getActivity()).setMydata(newlr);
                Toast.makeText(getContext(), "Successfully added to favourites.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
