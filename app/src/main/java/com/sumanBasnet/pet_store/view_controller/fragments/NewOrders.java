package com.sumanBasnet.pet_store.view_controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.Sales;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;
import com.sumanBasnet.pet_store.view_controller.activities.AdminDashboard;

public class NewOrders extends Fragment {

    LoginResponse loginResponse;
    APIInterface apiInterface;
    Sales sales;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.new_orders,container,false);
        loginResponse=((AdminDashboard) getActivity()).getMyData();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        sales=((AdminDashboard) getActivity()).getSales();








        return view;
    }
}
