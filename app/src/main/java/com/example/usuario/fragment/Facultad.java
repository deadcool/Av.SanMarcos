package com.example.usuario.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.avsanmarcos.R;

/**
 * Created by USUARIO on 14/05/2016.
 */
public class Facultad extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View vistaRaiz = inflater.inflate(R.layout.fragment_universidad,container,false);
        return inflater.inflate(R.layout.fragment_facultad,container,false);
    }
}