package com.sumanBasnet.pet_store.view_controller.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.sumanBasnet.pet_store.R;
import com.sumanBasnet.pet_store.module.Category;
import com.sumanBasnet.pet_store.module.LoginResponse;
import com.sumanBasnet.pet_store.module.Product;
import com.sumanBasnet.pet_store.module.UploadResponse;
import com.sumanBasnet.pet_store.retrofit.APIClient;
import com.sumanBasnet.pet_store.retrofit.APIInterface;
import com.sumanBasnet.pet_store.view_controller.activities.AdminDashboard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPet extends Fragment {
    buttonclicked listener;
    ImageButton addimage;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private File destination;
    ImageView showimage;
    String imgPath;
    Button addpet;
    EditText price, name, description, availablepcs, orderlimit;
    APIInterface apiInterface;
    Spinner spinner;
    HashMap hashMap=new HashMap();

    Bitmap bitmap;

    // regquire category variable


    public interface buttonclicked {
        void movetonext();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addpet, container, false);

        AdminDashboard activity = (AdminDashboard) getActivity();
        LoginResponse loginResponse = activity.getMyData();


        addimage = view.findViewById(R.id.imageButton2);
        showimage = view.findViewById(R.id.imageView4);
        addpet = view.findViewById(R.id.buttonAddPet);
        price = view.findViewById(R.id.editTextTextPersonName6);
        name = view.findViewById(R.id.editTextTextPersonName);
        description = view.findViewById(R.id.editTextTextMultiLine);
        availablepcs = view.findViewById(R.id.editTextTextPersonName7);
        orderlimit = view.findViewById(R.id.editTextTextPersonName9);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        spinner=view.findViewById(R.id.spinner);

        Call<List<Category>> allcategory=apiInterface.getallcategory();
        allcategory.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> cats=response.body();
                List<String> names=new ArrayList<>();
                for(int i=0;i<cats.size();i++){
                    hashMap.put(cats.get(i).getCategoryName(),cats.get(i).getId());
                    names.add(cats.get(i).getCategoryName());

                }


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, names);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable throwable) {
                Toast.makeText(getContext(), "Something went wrong. Try again", Toast.LENGTH_LONG).show();

            }
        });



        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        addpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Product p = new Product();
                p.setPrice(price.getText().toString());
                p.setName(name.getText().toString());
                p.setDescription(description.getText().toString());
                p.setAvailablepcs(availablepcs.getText().toString());
                p.setOrderlimit(orderlimit.getText().toString());
                if(imgPath==null){
                    Toast.makeText(getContext(), "Image not selected. Image is necessary", Toast.LENGTH_LONG).show();
                   return;
                }

                File f = new File(imgPath);


                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
                MultipartBody.Part body = MultipartBody.Part.createFormData("myfile", f.getName(), requestFile);
                Call<UploadResponse> responseCall = apiInterface.uploadFile(body);
                responseCall.enqueue(new Callback<UploadResponse>() {
                    @Override
                    public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                        p.setImage(response.body().getPath());

                        String selected=spinner.getSelectedItem().toString();

                        p.setCategory( hashMap.get(selected).toString());
                        String token = loginResponse.getToken();
                        Call<Product> saveproduct = apiInterface.postProduct("Bearer " + token, p);
                        saveproduct.enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if(response.code()!=201){
                           Toast.makeText(getContext(), "Error. Try again", Toast.LENGTH_LONG).show();
                           return;
                        }
                        Toast.makeText(getContext(), "Product added successfully", Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void onFailure(Call<Product> call, Throwable throwable) {
                        Toast.makeText(getContext(), "Something went wrong. Try again", Toast.LENGTH_LONG).show();
                    }
                });

                    }

                    @Override
                    public void onFailure(Call<UploadResponse> call, Throwable throwable) {

                        Toast.makeText(getContext(), "File upload failed Try again", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
        return view;
    }


    // Select image from camera and gallery
    private void selectImage() {
        try {
            PackageManager pm = getActivity().getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getActivity().getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Select file", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                        else if (options[item].equals("Select file")){
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("file/*");
                            startActivityForResult(intent, 1);

                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof buttonclicked) {
            listener = (buttonclicked) context;
        } else {
            throw new RuntimeException(context.toString() + "Must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                Log.e("Activity", "Pick from Camera::>>> ");

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File(Environment.getExternalStorageDirectory() + "/" +
                        getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgPath = destination.getAbsolutePath();
                showimage.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");

                imgPath = getRealPathFromURI(selectedImage);
                destination = new File(imgPath.toString());
                showimage.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}
