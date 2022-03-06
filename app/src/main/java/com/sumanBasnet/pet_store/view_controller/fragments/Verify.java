package com.sumanBasnet.pet_store.view_controller.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.Order;
import com.sumanBasnet.pet_store.module.ProductRow;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;
import com.sumanBasnet.pet_store.view_controller.activities.UserDasboard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Verify extends Fragment {
    RadioButton pickfromOffice, delivertoAddress, payCOD, payFromWallet;
    LoginResponse lr;
    TextView myBalance;
    Button confirm;
    String deliverto, payment;
    APIInterface apiInterface;
    boolean proceed=false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verify, container, false);
        pickfromOffice = view.findViewById(R.id.radioButton3);
        confirm = view.findViewById(R.id.confirm);
        delivertoAddress = view.findViewById(R.id.radioButton5);
        payCOD = view.findViewById(R.id.radioButton7);
        payFromWallet = view.findViewById(R.id.radioButton8);
        myBalance = view.findViewById(R.id.textView30);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        lr = ((UserDasboard) getActivity()).getMyData();
        delivertoAddress.setText("Deliver to " + lr.getAddress().replace("%", " , "));
        myBalance.setText("Nrs " + lr.getMyBalance());
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (delivertoAddress.isSelected()) {
                    deliverto = lr.getAddress().replace("%", " , ");
                } else {
                    deliverto = "pickFormOffice";
                }

                if (myBalance.isSelected()) {
                    payment = "fromWallet";
                    float finalTotal=((UserDasboard) getActivity()).getFinalSum();
                    String finalbalance;
                    if(Float.parseFloat(lr.getMyBalance())-finalTotal>0){
                        finalbalance="Are you sure? Your new balance will be"+(Float.parseFloat(lr.getMyBalance())-finalTotal);
                        makeDialog(finalbalance);
                        proceed=true;
                    }else{
                        finalbalance="You dont have sufficient balance. You cannot pay from your wallet";
                        makeDialog(finalbalance);
                    }
                } else {
                    payment = "COD";
                    makecall();
                }



            }
        });


        return view;
    }


    public void makecall() {
        ProductRow pr = new ProductRow();
        pr.setPayment(payment);
        pr.setDeliverTo(lr.getAddress());
        Call<Order> order = apiInterface.confirmOrder("Bearer " + lr.getToken(), pr);
        order.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Toast.makeText(getContext(), "Successfully ordered", Toast.LENGTH_LONG).show();
                getParentFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentOrder(), "FragmentOrder").addToBackStack("FragmentOrder").commit();


            }

            @Override
            public void onFailure(Call<Order> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void makeDialog(String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Confirm");
        builder.setMessage(message);
        if(proceed){
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    makecall();
                }
            });
        }else{
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    makecall();
                }
            });

        }

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
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
