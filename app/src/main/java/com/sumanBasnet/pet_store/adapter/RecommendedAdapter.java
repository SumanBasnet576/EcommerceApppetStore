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

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecomViewHolder> {

    List<Product> names = new ArrayList<>();
    onProductListener productListener;
    onAddtocartListener onAddtocartListener;


    public RecommendedAdapter(List<Product> names, onProductListener productListener, onAddtocartListener onAddtocartListener) {
        this.names = names;
        this.productListener = productListener;
        this.onAddtocartListener = onAddtocartListener;
    }

    @NonNull
    @Override
    public RecomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customitem, parent, false);
        RecomViewHolder myviewHolder = new RecomViewHolder(view, productListener, onAddtocartListener);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecomViewHolder holder, int position) {
        Product p = names.get(position);
        String path = p.getImage();
        String modified = path.replace("public", APIClient.BASE_URL);
        Picasso.get().load(modified).into(holder.image);
        //todo set all params
        holder.itemname.setText(p.getName());
        holder.price.setText("NRs. "+p.getPrice());
    }

    @Override
    public int getItemCount() {
        return names.size();
    }


    public class RecomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView image;
        Button addTocart;
        TextView itemname, price;
        onProductListener onProductListener;
        onAddtocartListener onAddtocartListener;
        public RecomViewHolder(@NonNull View itemView, onProductListener onProductListener, onAddtocartListener onAddtocartListener) {
            super(itemView);
            this.onProductListener = onProductListener;
            this.onAddtocartListener = onAddtocartListener;
            image = itemView.findViewById(R.id.imageView5);
            itemname = itemView.findViewById(R.id.productname);
            price = itemView.findViewById(R.id.price);
            addTocart = itemView.findViewById(R.id.addtoCart);
            addTocart.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override

        public void onClick(View view) {
            if (view == addTocart) {
                onAddtocartListener.onAddtoCartCLick(getAdapterPosition());
            } else {
                onProductListener.onProductClick(getAdapterPosition());
            }


        }
    }

    public interface onProductListener {
        void onProductClick(int position);
    }

    public interface onAddtocartListener {
        void onAddtoCartCLick(int position);
    }


}
