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
import com.sumanBasnet.pet_store.adapter.FavouriteAdapter;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.Product;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;
import com.sumanBasnet.pet_store.view_controller.activities.UserDasboard;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFavourite extends Fragment implements FavouriteAdapter.ondeletePressed{

    APIInterface apiInterface;
    LoginResponse loginResponse;
    RecyclerView recyclerFavs;
    RecyclerView.LayoutManager layoutManager;
FavouriteAdapter favouriteAdapter;List<Product> favList;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        loginResponse = ((UserDasboard) getActivity()).getMyData();
        recyclerFavs=view.findViewById(R.id.myFavouraites);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        String[] arrOfStr = loginResponse.getFavourite().split(",");
        List<Product> products=((UserDasboard) getActivity()).getAllproducts();
        favList=new ArrayList<>();
        for(int i=0;i<arrOfStr.length;i++){
            for(int j=0;j<products.size();j++){
                if(products.get(j).getId().equals(arrOfStr[i])){
                    favList.add(products.get(j));
                }
            }

        }
        layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerFavs.setLayoutManager(layoutManager);
        favouriteAdapter = new FavouriteAdapter(favList,this);
        recyclerFavs.setAdapter(favouriteAdapter);
        recyclerFavs.setHasFixedSize(true);


        return view;
    }

    @Override
    public void DeleteFromFaourite(int position) {

        Call<LoginResponse> deleteFavourite=apiInterface.deleteFavourite("Bearer "+loginResponse.getToken(),favList.get(position).getId());
        deleteFavourite.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse newlr=response.body();


                newlr.setToken(loginResponse.getToken());
                ((UserDasboard) getActivity()).setMydata(newlr);

                favList.remove(position);
                favouriteAdapter.notifyItemChanged(position);
                favouriteAdapter.notifyItemRangeChanged(position,favList.size());
                Toast.makeText(getContext(), "Successfully deleted.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
