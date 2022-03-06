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
import com.sumanBasnet.pet_store.module.Cart;
import com.sumanBasnet.pet_store.module.ProductRow;
import com.sumanBasnet.pet_store.retrofit.APIClient;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.CartViewHolder> {

    Cart cart;
    List<ProductRow> products;
    onaddListener onaddListener;
    onsubListener onsubListener;
    deleteListener deleteListener;

    public MyCartAdapter(Cart cart,onaddListener onaddListener,onsubListener onsubListener,deleteListener deleteListener) {
        this.cart = cart;
       products=cart.getProduct();
       this.onaddListener=onaddListener;
       this.onsubListener=onsubListener;
       this.deleteListener=deleteListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartcustomitem, parent, false);
        CartViewHolder myviewHolder = new CartViewHolder(view,onaddListener,onsubListener,deleteListener);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ProductRow row=products.get(position);
        holder.productName.setText(row.getProductName());
        holder.price.setText(row.getProductPrice());
        holder.quantity.setText(row.getQuantity());
        holder.total.setText(row.getTotal());
    String modified = row.getProductImage().replace("public", APIClient.BASE_URL);
       Picasso.get().load(modified).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ImageView imageView;
        Button add,sub;
        Button delete;
        TextView price,total,quantity,productName;
        onaddListener onaddListener;
        onsubListener onsubListener;
        deleteListener deleteListener;


        public CartViewHolder(@NonNull View itemView,onaddListener onaddListener,onsubListener onsubListener,deleteListener deleteListener) {
            super(itemView);
            this.onaddListener=onaddListener;
            this.onsubListener=onsubListener;
            this.deleteListener=deleteListener;
            imageView=itemView.findViewById(R.id.imageView5);
            add=itemView.findViewById(R.id.imageButton);
            sub=itemView.findViewById(R.id.imageButton4);
            delete=itemView.findViewById(R.id.imageButton5);
            price=itemView.findViewById(R.id.price);
            total=itemView.findViewById(R.id.price7);
            quantity=itemView.findViewById(R.id.price4);
            productName=itemView.findViewById(R.id.productname);
            add.setOnClickListener(this);
            sub.setOnClickListener(this);
            delete.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            if (view == add) {
                onaddListener.IncreaseQuantity(getAdapterPosition());
            } else if(view==sub) {
                onsubListener.DecreaseQuantity(getAdapterPosition());
            }else{
                deleteListener.DeleteProduct(getAdapterPosition());
            }

        }
    }

    public interface onaddListener{
        void IncreaseQuantity(int position);
    }


    public interface onsubListener{
        void DecreaseQuantity(int position);
    }

    public interface deleteListener{
        void DeleteProduct(int position);
    }


}
