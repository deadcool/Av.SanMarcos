package com.example.usuario.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.usuario.avsanmarcos.R;

/**
 * Created by USUARIO on 14/05/2016.
 */
public class Noticias extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_noticias,container,false);

        String[] noticias = new String[] { "UNMSM 465 aniversario",
                "FISI campeon de campeones","San Marcos App ahora disponible en appstore"};

        // B. Creamos un nuevo ArrayAdapter para nuestra lista
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, noticias);

        // C. Seleccionamos la lista de nuestro layout
        ListView miListaNoticias = (ListView) rootView.findViewById(R.id.listaNoticias);

        // D. Asignamos el adaptador a nuestra lista
        miListaNoticias.setAdapter(arrayAdapter);

        miListaNoticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Noticia numero" + position,
                        Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}
