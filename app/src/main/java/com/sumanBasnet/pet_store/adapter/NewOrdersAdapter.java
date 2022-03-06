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
import com.sumanBasnet.pet_store.module.Sales;

class NewOrdersAdapter extends RecyclerView.Adapter<NewOrdersAdapter.MyviewHolder>
{
    Sales sales;


    public NewOrdersAdapter(Sales sales) {
        this.sales = sales;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartcustomitem, parent, false);
        NewOrdersAdapter.MyviewHolder myviewHolder = new MyviewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
//        sales.ProductAdminRows row=sales.getProduct().get(position);
//        holder.productName.setText(row.getName());
//        holder.price.setText(row.getPrice());
//        holder.quantity.setText(row.getAvailablepcs());
//        holder.total.setVisibility(View.GONE);
//        //holder.quantity.setText(row.get());
//        // holder.total.setText(row.getTotal());
//        String modified = row.getImage().replace("public", APIClient.BASE_URL);
//        Picasso.get().load(modified).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        ImageButton add,sub,delete;
        TextView price,total,quantity,productName;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView5);
            add=itemView.findViewById(R.id.imageButton);
            sub=itemView.findViewById(R.id.imageButton4);
            delete=itemView.findViewById(R.id.imageButton5);
            price=itemView.findViewById(R.id.price);
            total=itemView.findViewById(R.id.price7);
            quantity=itemView.findViewById(R.id.price4);
            productName=itemView.findViewById(R.id.productname);
           // price6=itemView.findViewById(R.id.price6);
            add.setVisibility(View.GONE);
            sub.setVisibility(View.GONE);
         //   price6.setVisibility(View.GONE);
        }
    }


}
