package com.sumanBasnet.pet_store.retrofit;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {


    public static final String BASE_URL="http://192.168.1.136:3001";
  //+++public static final String BASE_URL="http://192.168.1.69:3001";
    public static Retrofit retrofit=null;


    public  static Retrofit getClient(){
        if(retrofit==null){
            retrofit =new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }
}
