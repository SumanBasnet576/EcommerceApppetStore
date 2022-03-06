package com.sumanBasnet.pet_store.retrofit;

import com.sumanBasnet.pet_store.module.Cart;
import com.sumanBasnet.pet_store.module.Category;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.Order;
import com.sumanBasnet.pet_store.module.Product;
import com.sumanBasnet.pet_store.module.ProductRow;
import com.sumanBasnet.pet_store.module.Sales;
import com.sumanBasnet.pet_store.module.UploadResponse;
import com.sumanBasnet.pet_store.module.User;

import java.util.List;
import java.util.Objects;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIInterface {

    @GET("/user")
    Call<List<User>> getallusers();

    @POST("/user/register")
    Call<User> register(@Body User user);

    @POST("/user/login")
    Call<LoginResponse> login(@Body User user);

@GET("/category/")
    Call<List<Category>> getallcategory();

@POST("/category/")
    Call<Category> postcategory(@Body Category category);

@GET("/product/")
    Call<List<Product>> getallProducts();

@POST("/product")
    Call<Product> postProduct( @Header("Authorization") String token,@Body Product product );

@GET("/user/product")
    Call<List<String>> getmyproductlist(@Header("Authorization") String token);

@Multipart
@POST("/upload")
Call<UploadResponse> uploadFile( @Part MultipartBody.Part imageFile);

@GET("/product")
    Call<List<Product>> getrecommendedProducts();

    @GET("/product/{product_id}")
    Call<Product> getProductbyId(@Path("product_id") String id);

    @PUT("/cart/row/{product_id}")
    Call<Cart> modifyProductinCart(@Header("Authorization") String token,@Path("product_id") String id,@Body ProductRow pr);

    @DELETE("/cart/row/{product_id}")
    Call<Cart> deleteProductinCart(@Header("Authorization") String token,@Path("product_id") String id);

    @GET("/cart")
    Call<Cart> getmycart(@Header("Authorization") String token);

    @POST("/cart")
    Call<Cart> posttoCart(@Header("Authorization") String token, @Body ProductRow productRow);

@GET("/order")
    Call<Order> getMyorders(@Header("Authorization") String token);

@POST("/order")
    Call<Order> confirmOrder(@Header( "Authorization") String token,@Body ProductRow productRow);

    @GET("/user/favourite")
    Call<List<Objects>> getFavourite(@Header( "Authorization") String token);

    @POST("/user/favourite")
    Call<LoginResponse> addFavourite(@Header( "Authorization") String token, @Body Product p);

    @DELETE("/user/favourite")
    Call<User> deleteFavs(@Header( "Authorization") String token);

    @DELETE("/user/favourite/{product_id}")
    Call<LoginResponse> deleteFavourite(@Header( "Authorization") String token, @Path("product_id") String id);

    @GET("/user/product")
    Call<LoginResponse> getmyProducts(@Header( "Authorization") String token);

    @GET("/mysales")
    Call<Sales> getmysales(@Header("Authorization") String token);



}
