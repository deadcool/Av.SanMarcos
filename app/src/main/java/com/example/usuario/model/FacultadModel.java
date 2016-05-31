package com.example.usuario.model;

/**
 * Created by USUARIO on 25/05/2016.
 */
public class FacultadModel {
    private String Id;
    private String Nombre;
    private String Autoridad;
    private String UrlFoto;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getAutoridad() {
        return Autoridad;
    }

    public void setAutoridad(String autoridad) {
        Autoridad = autoridad;
    }

    public String getUrlFoto() {
        return UrlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        UrlFoto = urlFoto;
    }
}
