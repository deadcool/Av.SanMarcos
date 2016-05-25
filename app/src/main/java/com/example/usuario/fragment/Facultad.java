package com.example.usuario.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.usuario.avsanmarcos.R;
import com.example.usuario.model.AdministradorModel;
import com.example.usuario.model.FacultadModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 14/05/2016.
 */
public class Facultad extends Fragment {
    ListView miListaFacultades;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_facultad,container,false);
        miListaFacultades = (ListView)rootView.findViewById(R.id.listaFacultad);
        new JSONTask().execute("http://52.36.38.235:9988/facultades");

        return rootView;
    }

    public class JSONTask extends AsyncTask<String, String,List<FacultadModel>> {

        @Override
        protected List<FacultadModel> doInBackground(String... params) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();

                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    stringBuffer.append(line);
                }

                String finalJSON = stringBuffer.toString();
                JSONArray parentArray = new JSONArray(finalJSON);

                List<FacultadModel> facultadModelList = new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    FacultadModel facultadModel = new FacultadModel();
                    JSONObject finalJSONObject = parentArray.getJSONObject(i);
                    facultadModel.setId(finalJSONObject.getString("Id"));
                    facultadModel.setNombre(finalJSONObject.getString("Nombre"));
                    facultadModel.setIdAdministrador(finalJSONObject.getString("IdAdministrador"));
                    facultadModel.setIdAutoridad(finalJSONObject.getString("IdAutoridad"));
                    facultadModel.setIdUbicacion(finalJSONObject.getString("IdUbicacion"));
                    facultadModel.setIdUniversidad(finalJSONObject.getString("IdUniversidad"));
                    facultadModelList.add(facultadModel);
                }

                return facultadModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
                try {
                    if (bufferedReader != null){
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<FacultadModel>result){
            super.onPostExecute(result);
            FacultadAdaptor adaptor = new FacultadAdaptor(getActivity().getApplicationContext(), R.layout.filas_facultad, result);
            miListaFacultades.setAdapter(adaptor);
        }
    }

    public class FacultadAdaptor extends ArrayAdapter {
        private List<FacultadModel> facultadModelList;
        private int resource;
        LayoutInflater inflater;

        public FacultadAdaptor(Context context, int resource, List objects) {
            super(context, resource, objects);
            facultadModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView =inflater.inflate(resource,null);
            }
            ImageView ivfacultad;
            TextView tvid;
            TextView tvnombre;
            TextView tvidautoridad;
            TextView tviduniversidad;
            TextView tvidubicacion;
            TextView tvidadministrador;

            ivfacultad = (ImageView)convertView.findViewById(R.id.ivfacultad);
            tvid = (TextView)convertView.findViewById(R.id.tvidfacultad);
            tvnombre = (TextView)convertView.findViewById(R.id.tvnombrefacultad);
            tvidautoridad = (TextView)convertView.findViewById(R.id.tvidautoridad);
            tviduniversidad = (TextView)convertView.findViewById(R.id.tviduniversidad);
            tvidubicacion = (TextView)convertView.findViewById(R.id.tvidubicacion);
            tvidadministrador = (TextView)convertView.findViewById(R.id.tvidadministrador);

            tvid.setText(facultadModelList.get(position).getId());
            tvnombre.setText(facultadModelList.get(position).getNombre());
            tvidautoridad.setText(facultadModelList.get(position).getIdAutoridad());
            tviduniversidad.setText(facultadModelList.get(position).getIdUniversidad());
            tvidubicacion.setText(facultadModelList.get(position).getIdUbicacion());
            tvidadministrador.setText(facultadModelList.get(position).getIdAdministrador());

            return convertView;
        }
    }
}
