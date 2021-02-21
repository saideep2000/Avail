package com.example.avail;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;


import java.util.List;
import java.util.Locale;


import static android.app.Activity.RESULT_OK;

public class textvoice extends Fragment implements View.OnClickListener {

    EditText name;
    ImageView speak;
    Button file;
    Intent myFile;
    TextToSpeech txttospeech;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.textvoice, container, false);

        try
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        txttospeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i!=TextToSpeech.ERROR){
                    txttospeech.setLanguage(Locale.ENGLISH);
                }
                else{
                    Toast.makeText(getActivity(), "error in audio", Toast.LENGTH_SHORT).show();
                }
            }
        });


        name = (EditText) view.findViewById(R.id.PersonName);
        speak = (ImageView) view.findViewById(R.id.speaking);
        file = (Button) view.findViewById(R.id.file);
        speak.setOnClickListener(this);
        file.setOnClickListener(this);







        return view;
    }

    @Override
    public void onPause() {
        if (txttospeech != null) {
            txttospeech.stop();
            txttospeech.shutdown();
        }
        super.onPause();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.file:
                Toast.makeText(getActivity(), "Filemanager is opened...!", Toast.LENGTH_LONG).show();

                myFile = new Intent(Intent.ACTION_GET_CONTENT);
                myFile.setType("*/*");
                startActivityForResult(myFile,10);

                break;
            case R.id.speaking:
//                Toast.makeText(getActivity(), "Ok I'm speaking...!", Toast.LENGTH_LONG).show();
                txttospeech.setLanguage(Locale.ENGLISH);
                String s = name.getText().toString();
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                txttospeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode)
        {
            case 10:
                if(resultCode == RESULT_OK)
                {
                    String path = data.getData().getPath();
                    Editable already = name.getText();
                    SpannableString ss1 =  new SpannableString(TextUtils.concat(already," ","hi"));
                    name.setText(ss1);
                    String message = "File has been Imported..!!";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(
                            getActivity()
                    )
                            .setSmallIcon(R.drawable.ic_baseline_favorite_24)
                            .setContentTitle("Activity")
                            .setContentText(message)
                            .setAutoCancel(true);

//                    Toast.makeText(getActivity(), "file", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
