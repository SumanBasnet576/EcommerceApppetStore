package com.sumanBasnet.pet_store.view_controller.fragments;

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
import com.sumanBasnet.pet_store.adapter.OrderAdapter;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.Order;
import com.sumanBasnet.pet_store.module.ProductRow;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;
import com.sumanBasnet.pet_store.view_controller.activities.UserDasboard;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOrder extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    LoginResponse lr;
    APIInterface apiInterface;
    Order order;
List<ProductRow> products;
OrderAdapter orderAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        recyclerView = view.findViewById(R.id.myOrders);
        lr = ((UserDasboard) getActivity()).getMyData();
        apiInterface= APIClient.getClient().create(APIInterface.class);

        Call<Order> orderCall=apiInterface.getMyorders("Bearer "+lr.getToken());
        orderCall.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {

                order = response.body();
                products = order.getProduct();
                recyclerView = view.findViewById(R.id.myOrders);
                layoutManager = new GridLayoutManager(getContext(), 1);
                recyclerView.setLayoutManager(layoutManager);
                orderAdapter = new OrderAdapter(order);
                recyclerView.setAdapter(orderAdapter);
                recyclerView.setHasFixedSize(true);

            }

            @Override
            public void onFailure(Call<Order> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong. Try again", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
