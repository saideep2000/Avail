package com.example.avail;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class account extends Fragment implements View.OnClickListener {

    static Button logout;
    Button added;
    Button change;
    ImageView imageView;
    EditText textView;

    Uri imageUri;
    StorageReference storageReference;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        storageReference= FirebaseStorage.getInstance().getReference();

        View view = inflater.inflate(R.layout.account, container, false);
        logout = (Button) view.findViewById(R.id.logout);
        added = (Button) view.findViewById(R.id.added);
        change = (Button) view.findViewById(R.id.change);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        textView = (EditText) view.findViewById(R.id.username);

        try
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
        catch (NullPointerException e){}


        logout.setOnClickListener(this);
        added.setOnClickListener(this);
        change.setOnClickListener(this);
        imageView.setOnClickListener(this);

        SharedPreferences yes = getActivity().getSharedPreferences("dataremeber", Context.MODE_PRIVATE);
        String hey=yes.getString("user","");
        textView.setText(hey);

        final StorageReference profileRef=storageReference.child(hey).child("profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
            }
        });




        return view;

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.logout:
                Toast.makeText(getActivity(), "hi...", Toast.LENGTH_LONG).show();
                SharedPreferences pref = getActivity().getSharedPreferences("Myprefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("login", false);
                editor.commit();
                Intent i=new Intent(getActivity(),SplashScreen.class);
                startActivity(i);
                Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_LONG).show();
                break;
            case R.id.added:
                Toast.makeText(getActivity(), "Renamed...!", Toast.LENGTH_LONG).show();
                break;
            case R.id.imageView:
                Toast.makeText(getActivity(), "Your profile pic!", Toast.LENGTH_LONG).show();
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,1000);
                break;
            case R.id.change:
                Toast.makeText(getActivity(), "please confirm the password", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000 && resultCode== Activity.RESULT_OK){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            uploadImageToFirebase(imageUri);

        }
    }
    private void uploadImageToFirebase(Uri imageUri){
        SharedPreferences yes = getActivity().getSharedPreferences("dataremeber", Context.MODE_PRIVATE);
        String hey=yes.getString("user","");
        textView.setText(hey);

        StorageReference fileRef = storageReference.child(hey).child("profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(),"Image uploaded",Toast.LENGTH_SHORT).show();
            }
        });
    }
}


