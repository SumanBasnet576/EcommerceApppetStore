package com.sumanBasnet.pet_store.view_controller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sumanBasnet.pet_store.R;

public class Register extends Fragment {
    private RegisterStep1Clicked listener;
    Button button;
    EditText firstname,lastname,phone,email,address;



    public interface RegisterStep1Clicked{
        void ondatasent(String[] details);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_regfirst,container,false);
        button=view.findViewById(R.id.floatingActionButton);
        firstname=view.findViewById(R.id.editTextTextPersonName4);
        lastname=view.findViewById(R.id.editTextTextPersonName);
        email=view.findViewById(R.id.editTextTextPersonName3);
        phone=view.findViewById(R.id.editTextTextPersonName2);
        address=view.findViewById(R.id.editTextTextPersonName8);


        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //TODO get input and validate


                //after validation
                String[] name= new String[5];
                name[0]=firstname.getText().toString();
                name[1]=lastname.getText().toString();
                name[2]=email.getText().toString();
                name[3]=phone.getText().toString();
                name[4]=address.getText().toString();
                listener.ondatasent(name);
            }
        });

        return view;
    }



    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if(context instanceof RegisterStep1Clicked){
            listener=(RegisterStep1Clicked) context;
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
