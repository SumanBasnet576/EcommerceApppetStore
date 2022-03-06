package com.sumanBasnet.pet_store.view_controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.adapter.CheckoutAdapter;
import com.sumanBasnet.pet_store.module.Cart;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.ProductRow;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;
import com.sumanBasnet.pet_store.view_controller.activities.UserDasboard;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Checkout extends Fragment {
    RecyclerView recyclerView;
    APIInterface apiInterface;
    LoginResponse lr;
    RecyclerView.LayoutManager layoutManager;
    CheckoutAdapter checkoutAdapter;
    Button confirm;
    TextView textfinalsum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.checkout, container, false);
        recyclerView = view.findViewById(R.id.checkoutRecycler);
        textfinalsum=view.findViewById(R.id.textfinalsum);
        confirm=view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.frame_container, new Verify(), "Verify").addToBackStack("Verify").commit();

            }
        });
        apiInterface = APIClient.getClient().create(APIInterface.class);
        lr = ((UserDasboard) getActivity()).getMyData();

        Call<Cart> mycart = apiInterface.getmycart("Bearer " + lr.getToken());
        mycart.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                recyclerView = view.findViewById(R.id.checkoutRecycler);
                layoutManager = new GridLayoutManager(getContext(), 1);
                recyclerView.setLayoutManager(layoutManager);
                checkoutAdapter = new CheckoutAdapter(response.body());
                recyclerView.setAdapter(checkoutAdapter);
                recyclerView.setHasFixedSize(true);
                float sum=sumtotal(response.body());
                textfinalsum.setText(""+sum);
                ((UserDasboard) getActivity()).setFinalSum(sum);

            }

            @Override
            public void onFailure(Call<Cart> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong. Try again", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    public float sumtotal(Cart cart){
        float total=0;

        List<ProductRow> products=cart.getProduct();
        for(int i=0;i<products.size();i++){
           total=total+Float.parseFloat(products.get(i).getTotal());
        }
        return  total;
    }
}
