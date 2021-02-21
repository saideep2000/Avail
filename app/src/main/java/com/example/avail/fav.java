package com.example.avail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class fav extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        View view = inflater.inflate(R.layout.fav, container, false);
        return view;
    }


    @Override
    public void onClick(View view) {

    }
}
