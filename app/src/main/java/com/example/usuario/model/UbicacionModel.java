package com.example.usuario.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by USUARIO on 24/05/2016.
 */
public class UbicacionModel {
    private String id;
    private String latitud;
    private String longitud;
    private String imagenEncode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getImagenEncode() {
        return imagenEncode;
    }

    public void setImagenEncode(String imagenEncode) {
        this.imagenEncode = imagenEncode;
    }
}