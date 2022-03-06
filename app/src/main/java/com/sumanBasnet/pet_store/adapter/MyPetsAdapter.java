package com.sumanBasnet.pet_store.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.module.Product;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyPetsAdapter extends RecyclerView.Adapter<MyPetsAdapter.MyviewHolder> {

    List<Product> names=new ArrayList<>();
    onItemClick onItemClick;

    public MyPetsAdapter(List<Product> names,onItemClick onItemClick) {
        this.names = names;
        this.onItemClick=onItemClick;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customitem,parent,false);
        MyviewHolder myviewHolder=new MyviewHolder(view,onItemClick);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.image.setImageResource(R.drawable.paw);
        holder.itemname.setText(names.get(position).getName());
        holder.price.setText("Nrs "+names.get(position).getPrice());
        String modified = names.get(position).getImage().replace("public", APIClient.BASE_URL);
        Picasso.get().load(modified).into(holder.image);
    }
//test push
    @Override
    public int getItemCount() {
        return names.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView itemname,price;
        Button addtoCart;
        onItemClick onItemClick;

        public MyviewHolder(@NonNull View itemView,onItemClick onItemClick) {
            super(itemView);
            this.onItemClick=onItemClick;
            image=itemView.findViewById(R.id.imageView5);
            itemname=itemView.findViewById(R.id.productname);
            price=itemView.findViewById(R.id.price);
            addtoCart=itemView.findViewById(R.id.addtoCart);
            addtoCart.setVisibility(View.GONE);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            onItemClick.ClickItem(getAdapterPosition());
        }
    }

    public interface onItemClick{
        void ClickItem(int position);
    }
}
