package com.alonso.webapp.proyecto.models;

import com.alonso.webapp.proyecto.models.enums.Estatus;

import java.time.LocalDate;

public class Aerolinea {

    private Long id;
    private String nombre;
    private String iata;
    private Estatus estatus;
    private String pais;
    private LocalDate fechaFundacion;
    //LA COMPARTEN TODAS
    private static Long lastId = 1L;

    public Aerolinea(){

    }

    public Aerolinea(Long id, String nombre, String iate, Estatus estatus, String pais, LocalDate fechaFundacion) {
        this.id = id;
        this.nombre = nombre;
        this.iata = iate;
        this.estatus = estatus;
        this.pais = pais;
        this.fechaFundacion = fechaFundacion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(LocalDate fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public static Long getLastId() {
        return lastId;
    }

    public static void setLastId(Long lastId) {
        Aerolinea.lastId = lastId;
    }

    @Override
    public String toString() {
        return "Aerolinea{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", iata='" + iata + '\'' +
                ", estatus=" + estatus +
                ", pais='" + pais + '\'' +
                ", fechaFundacion=" + fechaFundacion +
                '}';
    }
}
