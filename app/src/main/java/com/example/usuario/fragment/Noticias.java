package com.example.usuario.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.avsanmarcos.R;
import com.example.usuario.model.NoticiaModel;

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
public class Noticias extends Fragment {
    ListView miListaNoticias;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_noticias,container,false);

        new JSONTask().execute("http://52.36.38.235:9988/administradores");

        /*String[] noticias = new String[] { "UNMSM 465 aniversario",
                "FISI campeon de campeones","San Marcos App ahora disponible en appstore"};

        // B. Creamos un nuevo ArrayAdapter para nuestra lista
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, noticias);*/

        // C. Seleccionamos la lista de nuestro layout
        ListView miListaNoticias = (ListView) rootView.findViewById(R.id.listaNoticias);

        // D. Asignamos el adaptador a nuestra lista
        //miListaNoticias.setAdapter(arrayAdapter);

        /*miListaNoticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Noticia numero" + position,
                        Toast.LENGTH_LONG).show();
            }
        });*/

        return rootView;
    }

    public class JSONTask extends AsyncTask<String, String,List<NoticiaModel>>{

        @Override
        protected List<NoticiaModel> doInBackground(String... params) {
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

                 StringBuffer finalBufferData = new StringBuffer();
                 String finalJSON = stringBuffer.toString();
                 JSONArray parentArray = new JSONArray(finalJSON);

                 List<NoticiaModel> noticiaModelList = new ArrayList<>();
                   for (int i = 0; i < parentArray.length(); i++) {
                       NoticiaModel noticiaModel = new NoticiaModel();
                        JSONObject finalJSONObject = parentArray.getJSONObject(i);
                        noticiaModel.setTitulo(finalJSONObject.getString("Titulo"));
                        noticiaModel.setSubtitulo(finalJSONObject.getString("Subtitulo"));
                        noticiaModel.setFecha(finalJSONObject.getString("Fecha"));
                        noticiaModel.setImagen(finalJSONObject.getString("Imagen"));
                        noticiaModel.setDescripcion(finalJSONObject.getString("Descripcion"));
                        noticiaModelList.add(noticiaModel);
                    }

                return noticiaModelList;

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
        protected void onPostExecute(List<NoticiaModel>result){
            super.onPostExecute(result);
            NoticiasAdaptor adaptor = new NoticiasAdaptor(getActivity().getApplicationContext(), R.layout.filas_noticias, result);
            miListaNoticias.setAdapter(adaptor);
        }
    }

    public class NoticiasAdaptor extends ArrayAdapter{
        private List<NoticiaModel> noticiaModelList;
        private int resource;
        LayoutInflater inflater;

        public NoticiasAdaptor(Context context, int resource, List objects) {
            super(context, resource, objects);
            noticiaModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView =inflater.inflate(resource,null);
            }
            ImageView ivnoticia;
            TextView tvnoticiatitulo;
            TextView tvnoticiasubtitulo;
            TextView tvnoticiafecha;
            TextView tvnoticiadescripcion;

            ivnoticia = (ImageView)convertView.findViewById(R.id.ivnoticia);
            tvnoticiatitulo = (TextView)convertView.findViewById(R.id.tvnoticiatitulo);
            tvnoticiasubtitulo = (TextView)convertView.findViewById(R.id.tvnoticiasubtitulo);
            tvnoticiafecha = (TextView)convertView.findViewById(R.id.tvnoticiasubtitulo);
            tvnoticiadescripcion = (TextView)convertView.findViewById(R.id.tvnoticiadescripcion);

            tvnoticiatitulo.setText(noticiaModelList.get(position).getTitulo());
            tvnoticiasubtitulo.setText(noticiaModelList.get(position).getSubtitulo());
            tvnoticiafecha.setText(noticiaModelList.get(position).getFecha());
            tvnoticiadescripcion.setText(noticiaModelList.get(position).getDescripcion());

            return convertView;
        }
    }


}
