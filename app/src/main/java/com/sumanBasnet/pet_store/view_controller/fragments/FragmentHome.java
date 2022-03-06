package com.sumanBasnet.pet_store.view_controller.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.adapter.RecommendedAdapter;
import com.sumanBasnet.pet_store.module.Cart;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.Product;
import com.sumanBasnet.pet_store.module.ProductRow;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;
import com.sumanBasnet.pet_store.view_controller.activities.UserDasboard;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment implements RecommendedAdapter.onProductListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecommendedAdapter recommendedAdapter;
    List<Product> names = new ArrayList<>();
    APIInterface apiInterface;
    List<Product> recommendedProducts;
    LoginResponse lr;
    ProductRow pr;
    NavigationView navigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<Product>> recommendedList = apiInterface.getallProducts();
        lr = ((UserDasboard) getActivity()).getMyData();
        recommendedList.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                names = response.body();
                ((UserDasboard) getActivity()).setAllproducts(names);

                recyclerView = view.findViewById(R.id.recommended_products);
                layoutManager = new GridLayoutManager(getContext(), 2);
                recyclerView.setLayoutManager(layoutManager);
                recommendedAdapter = new RecommendedAdapter(names, FragmentHome.this::onProductClick, FragmentHome.this::onAddtoCartClicked);
                recyclerView.setAdapter(recommendedAdapter);
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

    @Override
    public void onProductClick(int position) {
        Product selectedProduct = names.get(position);
        boolean res = ((UserDasboard) getActivity()).setSelectedProduct(selectedProduct);
        if (res) {
            getParentFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentProductView(), "FragmentProductView").addToBackStack("FragmentProductView").commit();
        }


    }


    public void onAddtoCartClicked(int position) {

        Call<Cart> fetchMyCart = apiInterface.getmycart("Bearer " + lr.getToken());
        fetchMyCart.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {

                Cart myCart = response.body();
                List<ProductRow> productsincart = myCart.getProduct();
                if (myCart.getProduct().size() > 0) {
                    for (int i = 0; i < productsincart.size(); i++) {
//                       String Idofproductincart=productsincart.get(i).getId();
//                       String IdofSelectedProduct=names.get(position).getId();
                        if (productsincart.get(i).getProductId().equals(names.get(position).getId())) {
                            Toast.makeText(getContext(), "Product exists in cart", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    executeCall(position);

                } else {

                    executeCall(position);

                }

            }

            @Override
            public void onFailure(Call<Cart> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void executeCall(int position) {
        ProductRow pr = new ProductRow();
        pr.setProductId(names.get(position).getId());
        pr.setProductName(names.get(position).getName());
        pr.setProductPrice(names.get(position).getPrice());
        pr.setQuantity("1");
        pr.setSellerId(names.get(position).getSellerId());
        pr.setTotal(names.get(position).getPrice());
        pr.setProductImage(names.get(position).getImage());
        Call<Cart> addtoCart = apiInterface.posttoCart("Bearer " + lr.getToken(), pr);
        addtoCart.enqueue(new Callback<Cart>() {
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
}
