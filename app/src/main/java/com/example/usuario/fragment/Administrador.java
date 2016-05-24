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
 * Created by USUARIO on 24/05/2016.
 */
public class Administrador extends Fragment{
    ListView miListaAdministrador;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_administrador,container,false);
        miListaAdministrador = (ListView)rootView.findViewById(R.id.listaAdministrador);
        new JSONTask().execute("http://52.36.38.235:9988/administradores");

        return rootView;
    }

    public class JSONTask extends AsyncTask<String, String,List<AdministradorModel>> {

        @Override
        protected List<AdministradorModel> doInBackground(String... params) {
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

                List<AdministradorModel> administradorModelList = new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    AdministradorModel administradorModel = new AdministradorModel();
                    JSONObject finalJSONObject = parentArray.getJSONObject(i);
                    administradorModel.setId(finalJSONObject.getString("Id"));
                    administradorModel.setNombre(finalJSONObject.getString("Nombre"));
                    administradorModel.setCorreo(finalJSONObject.getString("Correo"));
                    administradorModel.setContraseña(finalJSONObject.getString("Contrasena"));
                    administradorModelList.add(administradorModel);
                }

                return administradorModelList;

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
        protected void onPostExecute(List<AdministradorModel>result){
            super.onPostExecute(result);
            AdministradorAdaptor adaptor = new AdministradorAdaptor(getActivity().getApplicationContext(), R.layout.filas_administrador, result);
            miListaAdministrador.setAdapter(adaptor);
        }
    }

    public class AdministradorAdaptor extends ArrayAdapter {
        private List<AdministradorModel> administradorModelList;
        private int resource;
        LayoutInflater inflater;

        public AdministradorAdaptor(Context context, int resource, List objects) {
            super(context, resource, objects);
            administradorModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView =inflater.inflate(resource,null);
            }
            ImageView ivadministrador;
            TextView tvid;
            TextView tvnombre;
            TextView tvcorreo;
            TextView tvcontraseña;

            ivadministrador = (ImageView)convertView.findViewById(R.id.ivadministrador);
            tvid = (TextView)convertView.findViewById(R.id.tvid);
            tvnombre = (TextView)convertView.findViewById(R.id.tvnombre);
            tvcorreo = (TextView)convertView.findViewById(R.id.tvcorreo);
            tvcontraseña = (TextView)convertView.findViewById(R.id.tvcontraseña);

            tvid.setText(administradorModelList.get(position).getId());
            tvnombre.setText(administradorModelList.get(position).getNombre());
            tvcorreo.setText(administradorModelList.get(position).getCorreo());
            tvcontraseña.setText(administradorModelList.get(position).getContraseña());

            return convertView;
        }
    }
}
