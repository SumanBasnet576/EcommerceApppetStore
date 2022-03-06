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
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;
import com.sumanBasnet.pet_store.view_controller.activities.AdminDashboard;
import com.jjoe64.graphview.GraphView;

public class Sales extends Fragment {
    GraphView graph;
    LoginResponse loginResponse;
    APIInterface apiInterface;
    com.sumanBasnet.pet_store.module.Sales sales;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.my_sales,container,false);
         graph = (GraphView) view.findViewById(R.id.graph);
        loginResponse=((AdminDashboard) getActivity()).getMyData();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        sales=((AdminDashboard) getActivity()).getSales();

        for(int i=0;i<sales.getProduct().size();i++){
            String getorderdate=sales.getCreatedAt();




        }


    //    graph.addSeries(series);
       // graph.addSeries();





        return view;
    }
}
