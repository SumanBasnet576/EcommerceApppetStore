package com.sumanBasnet.pet_store.view_controller.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.adapter.MyPetsAdapter;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.Product;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;
import com.sumanBasnet.pet_store.view_controller.activities.AdminDashboard;
import com.sumanBasnet.pet_store.view_controller.activities.Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHome  extends Fragment implements MyPetsAdapter.onItemClick {
    FloatingActionButton addpet;
     addpetButtonClicked listener;
     RecyclerView recyclerView;
     RecyclerView.LayoutManager layoutManager;
     MyPetsAdapter myPetsAdapter;
     List<String> names=new ArrayList<>();
     ImageButton logout;
     ImageView neworders,sales,finished;
     APIInterface apiInterface;
    LoginResponse lr;
    List<Product> myproducts;
    TextView myneworders;

    @Override
    public void ClickItem(int position) {
       Product p=myproducts.get(position);

        ((AdminDashboard) getActivity()).setSelectedProduct(p);
        getParentFragmentManager().beginTransaction().replace(R.id.admincontainer, new FragmentProductView(), "FragmentProductView").addToBackStack("FragmentProductView").commit();


    }


    public interface addpetButtonClicked{
        void createpet(String[] details);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.adminhome,container,false);
        logout=view.findViewById(R.id.imageButton3);
        apiInterface= APIClient.getClient().create(APIInterface.class);
        neworders=view.findViewById(R.id.imageView10);
        sales=view.findViewById(R.id.imageView9);
        finished=view.findViewById(R.id.imageView11);
        myneworders=view.findViewById(R.id.textView53);
        myneworders.setVisibility(View.GONE);
        neworders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.admincontainer, new NewOrders(), "NewOrders").addToBackStack("NewOrders").commit();

            }
        });
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.admincontainer, new FinishedOrders(), "FinishedOrders").addToBackStack("FinishedOrders").commit();

            }
        });
        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.admincontainer, new Sales(), "Sales").addToBackStack("Sales").commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Login.class));
                getActivity().finish();

            }
        });
       lr=((AdminDashboard) getActivity()).getMyData();
        addpet=view.findViewById(R.id.addPetButton);
        addpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] datas= new String[5];
               listener.createpet(datas);
            }
        });

        Call<com.sumanBasnet.pet_store.module.Sales> salesCall=apiInterface.getmysales("Bearer "+lr.getToken());
        salesCall.enqueue(new Callback<com.sumanBasnet.pet_store.module.Sales>() {
            @Override
            public void onResponse(Call<com.sumanBasnet.pet_store.module.Sales> call, Response<com.sumanBasnet.pet_store.module.Sales> response) {
                ((AdminDashboard)getActivity()).setSales(response.body());

                if(response.body().getProduct().size()>0){
                    myneworders.setVisibility(View.VISIBLE);
                    String size=" "+response.body().getProduct().size();
                    myneworders.setText(size);
                }
            }

            @Override
            public void onFailure(Call<com.sumanBasnet.pet_store.module.Sales> call, Throwable throwable) {

                Toast.makeText(getContext(), "Something went wrong. Try again", Toast.LENGTH_SHORT).show();
            }
        });


        Call<List<Product>> getallproducts=apiInterface.getallProducts();
        getallproducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                String[] arrOfStr = lr.getMyproducts().split(",");
                List<Product> products=response.body();
                myproducts=new ArrayList<>();
                for(int i=0;i<arrOfStr.length;i++){
                    for(int j=0;j<products.size();j++){
                        if(products.get(j).getId().equals(arrOfStr[i])){
                            myproducts.add(products.get(j));
                        }
                    }

                }

                recyclerView=view.findViewById(R.id.mypetsarea);
                layoutManager=new GridLayoutManager(getContext(),2);
                recyclerView.setLayoutManager(layoutManager);
                myPetsAdapter=new MyPetsAdapter(myproducts,AdminHome.this::ClickItem);
                recyclerView.setAdapter(myPetsAdapter);
                recyclerView.setHasFixedSize(true);





            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong. Try again", Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if(context instanceof addpetButtonClicked){
            listener=(addpetButtonClicked) context;
        }else{
            throw new RuntimeException(context.toString()+"Must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }
}
