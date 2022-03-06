package com.sumanBasnet.pet_store.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.module.Product;
import com.sumanBasnet.pet_store.retrofit.APIClient;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    List<Product> favourites=null;
    ondeletePressed ondeletePressed;

    public FavouriteAdapter(List<Product> favourites,ondeletePressed ondeletePressed) {
        this.favourites = favourites;
        this.ondeletePressed=ondeletePressed;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartcustomitem, parent, false);
        FavouriteViewHolder myviewHolder = new FavouriteViewHolder(view,ondeletePressed);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {

        Product row=favourites.get(position);
        holder.productName.setText(row.getName());
        holder.price.setText(row.getPrice());
        holder.quantity.setVisibility(View.GONE);
        //holder.quantity.setText(row.getAvailablepcs());
        holder.total.setVisibility(View.GONE);
        //holder.quantity.setText(row.get());
       // holder.total.setText(row.getTotal());
        String modified = row.getImage().replace("public", APIClient.BASE_URL);
        Picasso.get().load(modified).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        Button add,sub,delete;
        TextView price,total,quantity,productName,price6;
        ondeletePressed ondeletePressed;

        public FavouriteViewHolder(@NonNull View itemView,ondeletePressed ondeletePressed) {
            super(itemView);
            this.ondeletePressed= ondeletePressed;
            imageView=itemView.findViewById(R.id.imageView5);
            add=itemView.findViewById(R.id.imageButton);
            sub=itemView.findViewById(R.id.imageButton4);
            delete=itemView.findViewById(R.id.imageButton5);
            price=itemView.findViewById(R.id.price);
            total=itemView.findViewById(R.id.price7);
            quantity=itemView.findViewById(R.id.price4);
            productName=itemView.findViewById(R.id.productname);
            price6=itemView.findViewById(R.id.price6);
            add.setVisibility(View.GONE);
            sub.setVisibility(View.GONE);
            price6.setVisibility(View.GONE);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ondeletePressed.DeleteFromFaourite(getAdapterPosition());

        }


    }

    public interface ondeletePressed{
        void DeleteFromFaourite(int position);
    }


}
