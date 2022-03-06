package com.sumanBasnet.pet_store.view_controller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sumanBasnet.pet_store.R;

public class registerSecond extends Fragment {
    RegisterFinish listener;
    Button register;
    EditText password,city,state,locality,postalcode;
    RadioButton buyer,admin;

    public interface RegisterFinish{
        void createUser(String[] details);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_regsecond,container,false);
        register=view.findViewById(R.id.buttonRegister);
        password=view.findViewById(R.id.editTextTextPersonName3);
        city=view.findViewById(R.id.editTextTextPersonName4);
        state=view.findViewById(R.id.editTextTextPersonName);
        locality=view.findViewById(R.id.editTextTextPersonName2);
        postalcode=view.findViewById(R.id.editTextPostalcode);
        buyer=view.findViewById(R.id.radioButton6);
        admin=view.findViewById(R.id.radioButton9);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] finaldata=new String[6];
                finaldata[0]=password.getText().toString();

                    finaldata[1]=city.getText().toString();
                              finaldata[2]=state.getText().toString();

                                  finaldata[3]=locality.getText().toString();
                                           finaldata[4]=postalcode.getText().toString();
                                           if(buyer.isChecked()){
                                               finaldata[5]="buyer";
                                           }
                                           else{
                                               finaldata[5]="admin";
                                           }

                listener.createUser(finaldata);
            }
        });
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof RegisterFinish){
            listener=(RegisterFinish) context;
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
