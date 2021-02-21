package com.example.avail;

import android.Manifest;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class camera extends Fragment implements View.OnClickListener {

    private static final int PERMISSION_CODE = 1000;
    private static final Object IMAGE_CAPTURE_CODE = 1001;
    ImageView img;
    Intent myFile;
    Uri image_uri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera, container, false);

        try
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        img = (ImageView) view.findViewById(R.id.img);
        img.setOnClickListener(this);

        Button clr = view.findViewById(R.id.clrr);
        clr.setOnClickListener(this);

        Button co = view.findViewById(R.id.coyy);
        co.setOnClickListener(this);

        ImageView btn = (ImageView)  view.findViewById(R.id.btnSpk);
        btn.setOnClickListener(this);

        ImageView cam = (ImageView)  view.findViewById(R.id.cam);
        cam.setOnClickListener(this);





        return view;
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img:
                Toast.makeText(getActivity(), "Filemanager is opened...!", Toast.LENGTH_LONG).show();
                TextView txt = camera.this.getView().findViewById(R.id.imgtext);
                txt.setVisibility(View.INVISIBLE);

                myFile = new Intent(Intent.ACTION_GET_CONTENT);
                myFile.setType("*/*");
                startActivityForResult(myFile,10);
                break;
            case R.id.clrr:
                Toast.makeText(getActivity(), "Image cleared..!", Toast.LENGTH_LONG).show();
                img.setImageResource(android.R.color.transparent);

                TextView txxxt = camera.this.getView().findViewById(R.id.imgtext);
                txxxt.setVisibility(View.VISIBLE);

//                txvResult.setText("");
                break;
            case R.id.coyy:
                Toast.makeText(getActivity(), "Text copied..!", Toast.LENGTH_LONG).show();
//                String txt= txvResult.getText().toString();
//
//                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//                ClipData clip = ClipData.newPlainText("label", txt);
//                clipboard.setPrimaryClip(clip);
//                txvResult.setText("");
                break;
            case R.id.btnSpk:
                Toast.makeText(getActivity(), "Speaking..!", Toast.LENGTH_LONG).show();
                openDialog();
                break;
            case R.id.cam:
                Toast.makeText(getActivity(), "Opening Camera..!", Toast.LENGTH_LONG).show();

                TextView txxt = camera.this.getView().findViewById(R.id.imgtext);
                txxt.setVisibility(View.INVISIBLE);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String [] permission = {Manifest.permission.CAMERA , Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else{
                        openCamera();
                    }
                }
                else {
                    openCamera();
                }


        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");

        ContentResolver resolver = Objects.requireNonNull(getActivity()).getContentResolver();

        image_uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent, (Integer) IMAGE_CAPTURE_CODE);

    }

    private void openDialog() {
        Dialog dialog = new Dialog();
        dialog.show(getFragmentManager(),"dialog");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else {
                    Toast.makeText(getActivity(), "Permissions denied..!", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
//                    String path = data.getData().getPath();
                    img.setImageURI(data.getData());

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "Avail")
                            .setSmallIcon(R.drawable.notification_icon)
                            .setContentTitle("IMAGE")
                            .setContentText("Image is being Imported")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                    NotificationManager notificationManager = ( NotificationManager ) getActivity().getSystemService( getActivity().NOTIFICATION_SERVICE );
                    notificationManager.notify(100, builder.build());
                }
                break;
            case 1001:
                if (resultCode == RESULT_OK) {
                    img.setImageURI(image_uri);
                }
        }
    }
}
