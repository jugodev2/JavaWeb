package com.alonso.webapp.proyecto.models;


import com.alonso.webapp.proyecto.models.enums.Estatus;

public class Aeropuerto {

    private Long id;
    private String nombre;
    private String codigo;
    private String latitud;
    private String longitud;
    private String pais;
    private Estatus estatus;
    //LA COMPARTEN TODAS
    private static Long lastId = 1L;

    public Aeropuerto(){

    }

    public Aeropuerto(Long id, String nombre, String codigo, String latitud, String longitud, String pais, Estatus estatus) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.pais = pais;
        this.estatus = estatus;
        this.codigo = codigo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public static Long getLastId() {
        return lastId;
    }

    public static void setLastId(Long lastId) {
        Aeropuerto.lastId = lastId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Aeropuerto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                ", pais='" + pais + '\'' +
                ", estatus=" + estatus +
                '}';
    }
}
