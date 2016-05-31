package com.example.usuario.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.usuario.avsanmarcos.R;
import com.example.usuario.model.AdministradorModel;
import com.example.usuario.model.FacultadModel;
import com.example.usuario.model.FacultadModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config); // Do it on Application start

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

                List<FacultadModel> FacultadModelList = new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    FacultadModel FacultadModel = new FacultadModel();
                    JSONObject finalJSONObject = parentArray.getJSONObject(i);
                    FacultadModel.setId(finalJSONObject.getString("Id"));
                    FacultadModel.setNombre(finalJSONObject.getString("Nombre"));
                    FacultadModel.setAutoridad(finalJSONObject.getString("Autoridad"));
                    FacultadModel.setUrlFoto(finalJSONObject.getString("URLFoto"));
                    FacultadModelList.add(FacultadModel);
                }

                return FacultadModelList;

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
            ViewHolder viewHolder = null;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView =inflater.inflate(resource,null);
                viewHolder.ivfacultad = (ImageView)convertView.findViewById(R.id.ivFacultad);
                viewHolder.tvnombre = (TextView)convertView.findViewById(R.id.tvFacultadNombre);
                viewHolder.tvautoridad = (TextView)convertView.findViewById(R.id.tvFacultadAutoridad);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }

            String url = "http://" + facultadModelList.get(position).getUrlFoto();

            final ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);
            ImageLoader.getInstance().displayImage(url, viewHolder.ivfacultad, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            });

            viewHolder.tvnombre.setText(facultadModelList.get(position).getNombre());
            viewHolder.tvautoridad.setText(facultadModelList.get(position).getAutoridad());

            //Picasso.with(getActivity().getApplicationContext()).load(FacultadModelList.get(position).getImagenEncode()).into(ivubicacion);
            //Picasso.with(getActivity().getApplicationContext()).load("http://52.36.38.235/Huaca%20San%20Marcos.jpg").into(ivubicacion);

            return convertView;
        }

        class ViewHolder{
            private ImageView ivfacultad;
            private TextView tvnombre;
            private TextView tvautoridad;
        }
    }
}
