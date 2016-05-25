package com.example.usuario.model;

/**
 * Created by USUARIO on 25/05/2016.
 */
public class FacultadModel {
    private String Id;
    private String Nombre;
    private String IdAutoridad;
    private String IdUniversidad;
    private String IdUbicacion;
    private String IdAdministrador;

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

    public String getIdAutoridad() {
        return IdAutoridad;
    }

    public void setIdAutoridad(String idAutoridad) {
        IdAutoridad = idAutoridad;
    }

    public String getIdUniversidad() {
        return IdUniversidad;
    }

    public void setIdUniversidad(String idUniversidad) {
        IdUniversidad = idUniversidad;
    }

    public String getIdUbicacion() {
        return IdUbicacion;
    }

    public void setIdUbicacion(String idUbicacion) {
        IdUbicacion = idUbicacion;
    }

    public String getIdAdministrador() {
        return IdAdministrador;
    }

    public void setIdAdministrador(String idAdministrador) {
        IdAdministrador = idAdministrador;
    }
}
