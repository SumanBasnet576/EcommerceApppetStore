package com.sumanBasnet.pet_store.view_controller.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.adapter.MyCartAdapter;
import com.sumanBasnet.pet_store.module.Cart;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.Product;
import com.sumanBasnet.pet_store.module.ProductRow;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;
import com.sumanBasnet.pet_store.view_controller.activities.UserDasboard;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCart extends Fragment implements MyCartAdapter.deleteListener, MyCartAdapter.onaddListener, MyCartAdapter.onsubListener {

    APIInterface apiInterface;
    Cart cart;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyCartAdapter myCartAdapter;
    List<ProductRow> products;
    LoginResponse lr;
    Button checkout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        checkout=view.findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.frame_container, new Checkout(), "Checkout").addToBackStack("Checkout").commit();


            }
        });
        lr = ((UserDasboard) getActivity()).getMyData();


        Call<Cart> cartCall = apiInterface.getmycart("Bearer " + lr.getToken());
        cartCall.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                cart = response.body();
                products = cart.getProduct();
                recyclerView = view.findViewById(R.id.mycart);
                layoutManager = new GridLayoutManager(getContext(), 1);
                recyclerView.setLayoutManager(layoutManager);
                myCartAdapter = new MyCartAdapter(cart, FragmentCart.this::IncreaseQuantity, FragmentCart.this::DecreaseQuantity, FragmentCart.this::DeleteProduct);
                recyclerView.setAdapter(myCartAdapter);
                recyclerView.setHasFixedSize(true);
            }

            @Override

            public void onFailure(Call<Cart> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong. Try again", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void IncreaseQuantity(int position) {
        ProductRow pr = products.get(position);
        String productId = pr.getProductId();
        Call<Product> getproduct = apiInterface.getProductbyId(productId);
        getproduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (Integer.parseInt(pr.getQuantity()) < Integer.parseInt(response.body().getOrderlimit())) {

                    Integer newQuantity = Integer.parseInt(pr.getQuantity()) + 1;
                    Float newtotal = newQuantity * Float.parseFloat(pr.getProductPrice());
                    cart.getProduct().get(position).setQuantity(newQuantity + "");
                    cart.getProduct().get(position).setTotal(newtotal + "");
                    ProductRow pr = new ProductRow();
                    pr.setQuantity(newQuantity + "");
                    pr.setTotal(newtotal + "");


                    Call<Cart> updatedcart = apiInterface.modifyProductinCart("Bearer " + lr.getToken(), cart.getProduct().get(position).getId(), pr);
                    updatedcart.enqueue(new Callback<Cart>() {
                        @Override
                        public void onResponse(Call<Cart> call, Response<Cart> response) {
                            myCartAdapter = new MyCartAdapter(response.body(), FragmentCart.this::IncreaseQuantity, FragmentCart.this::DecreaseQuantity, FragmentCart.this::DeleteProduct);
                            recyclerView.setAdapter(myCartAdapter);
                        }

                        @Override
                        public void onFailure(Call<Cart> call, Throwable throwable) {
                            Toast.makeText(getContext(), "Something went wrong" + response.body().getOrderlimit(), Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    Toast.makeText(getContext(), "Cannot order more than" + response.body().getOrderlimit(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void DecreaseQuantity(int position) {
        ProductRow pr = products.get(position);
        String productId = pr.getProductId();
        Call<Product> getproduct = apiInterface.getProductbyId(productId);
        getproduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (Integer.parseInt(pr.getQuantity()) > 1) {

                    Integer newQuantity = Integer.parseInt(pr.getQuantity()) - 1;
                    Float newtotal = newQuantity * Float.parseFloat(pr.getProductPrice());
                    cart.getProduct().get(position).setQuantity(newQuantity + "");
                    cart.getProduct().get(position).setTotal(newtotal + "");
                    ProductRow pr = new ProductRow();
                    pr.setQuantity(newQuantity + "");
                    pr.setTotal(newtotal + "");


                    Call<Cart> updatedcart = apiInterface.modifyProductinCart("Bearer " + lr.getToken(), cart.getProduct().get(position).getId(), pr);
                    updatedcart.enqueue(new Callback<Cart>() {
                        @Override
                        public void onResponse(Call<Cart> call, Response<Cart> response) {
                            myCartAdapter = new MyCartAdapter(response.body(), FragmentCart.this::IncreaseQuantity, FragmentCart.this::DecreaseQuantity, FragmentCart.this::DeleteProduct);
                            recyclerView.setAdapter(myCartAdapter);
                        }

                        @Override
                        public void onFailure(Call<Cart> call, Throwable throwable) {
                            Toast.makeText(getContext(), "Something went wrong" + response.body().getOrderlimit(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    // Call<Cart>

                } else {
                    Toast.makeText(getContext(), "Quantity needs to be at least one", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void DeleteProduct(int position) {
        //   Toast.makeText(getContext(), "Delete Product", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                ProductRow pr = products.get(position);

                Call<Cart> deleteFromcart = apiInterface.deleteProductinCart("Bearer " + lr.getToken(), cart.getProduct().get(position).getId());
                deleteFromcart.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        dialog.dismiss();
                        myCartAdapter = new MyCartAdapter(response.body(), FragmentCart.this::IncreaseQuantity, FragmentCart.this::DecreaseQuantity, FragmentCart.this::DeleteProduct);
                        recyclerView.setAdapter(myCartAdapter);
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable throwable) {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                //  dialog.dismiss();
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
}
