package com.sumanBasnet.pet_store.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.module.Cart;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckViewHolder> {


    Cart mycart;
    public CheckoutAdapter(Cart mycart) {
        this.mycart = mycart;

    }

    @NonNull
    @Override
    public CheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkoutcustomitem, parent, false);
        CheckViewHolder myviewHolder = new CheckViewHolder(view);
        return myviewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CheckViewHolder holder, int position) {
        holder.name.setText(mycart.getProduct().get(position).getProductName());
        holder.price.setText(mycart.getProduct().get(position).getProductPrice());
        holder.quantity.setText(mycart.getProduct().get(position).getQuantity());
        holder.total.setText(mycart.getProduct().get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return mycart.getProduct().size();
    }

    public class CheckViewHolder extends RecyclerView.ViewHolder{

        TextView name,price,quantity,total;


        public CheckViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.productname);
            price=itemView.findViewById(R.id.price);
            quantity=itemView.findViewById(R.id.price4);
            total=itemView.findViewById(R.id.price7);
        }
    }
}
