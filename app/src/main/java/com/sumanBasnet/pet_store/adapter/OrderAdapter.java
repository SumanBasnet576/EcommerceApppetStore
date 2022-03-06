package com.sumanBasnet.pet_store.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.module.Order;
import com.sumanBasnet.pet_store.module.ProductRow;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {



    Order order;
    List<ProductRow> products;

    public OrderAdapter(Order order) {
        this.order = order;
        String check="Check";

    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartcustomitem, parent, false);
        OrderViewHolder myviewHolder = new OrderViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        ProductRow row=order.getProduct().get(position);
        holder.productName.setText(row.getProductName());
        holder.price.setText(row.getProductPrice());
        holder.quantity.setText(row.getQuantity());
        holder.total.setText(row.getTotal());
        String modified = row.getProductImage().replace("public", APIClient.BASE_URL);
        Picasso.get().load(modified).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return order.getProduct().size();
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        ImageButton add,sub,delete;
        TextView price,total,quantity,productName;


        public OrderViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView5);
            add=itemView.findViewById(R.id.imageButton);
            sub=itemView.findViewById(R.id.imageButton4);
            delete=itemView.findViewById(R.id.imageButton5);
            price=itemView.findViewById(R.id.price);
            total=itemView.findViewById(R.id.price7);
            quantity=itemView.findViewById(R.id.price4);
            productName=itemView.findViewById(R.id.productname);

            add.setVisibility(View.INVISIBLE);
            sub.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);



        }
    }
}
