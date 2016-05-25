package com.example.usuario.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.usuario.model.UbicacionModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.picasso.Picasso;

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
public class Ubicacion extends Fragment {
    ListView miListaUbicacion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ubicacion,container,false);

        // Create default options which will be used for every
//  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config); // Do it on Application start

        miListaUbicacion = (ListView)rootView.findViewById(R.id.listaUbicacion);
        new JSONTask().execute("http://52.36.38.235:9988/ubicacion");

        return rootView;
    }

    public class JSONTask extends AsyncTask<String, String,List<UbicacionModel>> {

        @Override
        protected List<UbicacionModel> doInBackground(String... params) {
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

                List<UbicacionModel> ubicacionModelList = new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    UbicacionModel ubicacionModel = new UbicacionModel();
                    JSONObject finalJSONObject = parentArray.getJSONObject(i);
                    ubicacionModel.setId(finalJSONObject.getString("Id"));
                    ubicacionModel.setLatitud(finalJSONObject.getString("Latitud"));
                    ubicacionModel.setLongitud(finalJSONObject.getString("Longitud"));
                    ubicacionModel.setUrlfoto(finalJSONObject.getString("URLFoto"));
                    ubicacionModelList.add(ubicacionModel);
                }

                return ubicacionModelList;

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
        protected void onPostExecute(List<UbicacionModel>result){
            super.onPostExecute(result);
            UbicacionAdaptor adaptor = new UbicacionAdaptor(getActivity().getApplicationContext(), R.layout.filas_ubicacion, result);
            miListaUbicacion.setAdapter(adaptor);
        }
    }

    public class UbicacionAdaptor extends ArrayAdapter {
        private List<UbicacionModel> facultadModelList;
        private int resource;
        LayoutInflater inflater;

        public UbicacionAdaptor(Context context, int resource, List objects) {
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
                viewHolder.ivubicacion = (ImageView)convertView.findViewById(R.id.ivubicacion);
                viewHolder.tvid = (TextView)convertView.findViewById(R.id.tvidubicacion);
                viewHolder.tvlatitud = (TextView)convertView.findViewById(R.id.tvlatitud);
                viewHolder.tvlongitud = (TextView)convertView.findViewById(R.id.tvlongitud);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }

            final ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);
            ImageLoader.getInstance().displayImage("http://52.36.38.235/Huaca%20San%20Marcos.jpg", viewHolder.ivubicacion, new ImageLoadingListener() {
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

            viewHolder.tvid.setText(facultadModelList.get(position).getId());
            viewHolder.tvlatitud.setText(facultadModelList.get(position).getLatitud());
            viewHolder.tvlongitud.setText(facultadModelList.get(position).getLongitud());

            //Picasso.with(getActivity().getApplicationContext()).load(ubicacionModelList.get(position).getImagenEncode()).into(ivubicacion);
            //Picasso.with(getActivity().getApplicationContext()).load("http://52.36.38.235/Huaca%20San%20Marcos.jpg").into(ivubicacion);

            return convertView;
        }

        class ViewHolder{
            private ImageView ivubicacion;
            private TextView tvid;
            private TextView tvlatitud;
            private TextView tvlongitud;
        }
    }
}
