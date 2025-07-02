package com.alonso.webapp.proyecto.models;



import com.alonso.webapp.proyecto.models.enums.Estatus;

import java.time.LocalDate;

public class Avion {

    private Long id;
    private Long numeroRegistro;
    private String tipo;
    private String codigoModelo;
    private int capacidad;
    private LocalDate fechaPrimerVuelo;
    private Estatus estatus;
    private Aerolinea aerolinea;
    //LA COMPARTEN TODAS
    private static Long lastId = 1L;

    public Avion(){

    }

    public Avion(Long id, Long numeroRegistro, String tipo, String codigoModelo, int capacidad, LocalDate fechaPrimerVuelo, Estatus estatus, Aerolinea aerolinea) {
        this.id = id;
        this.numeroRegistro = numeroRegistro;
        this.tipo = tipo;
        this.codigoModelo = codigoModelo;
        this.capacidad = capacidad;
        this.fechaPrimerVuelo = fechaPrimerVuelo;
        this.estatus = estatus;
        this.aerolinea = aerolinea;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(Long numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodigoModelo() {
        return codigoModelo;
    }

    public void setCodigoModelo(String codigoModelo) {
        this.codigoModelo = codigoModelo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public LocalDate getFechaPrimerVuelo() {
        return fechaPrimerVuelo;
    }

    public void setFechaPrimerVuelo(LocalDate fechaPrimerVuelo) {
        this.fechaPrimerVuelo = fechaPrimerVuelo;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }

    public static Long getLastId() {
        return lastId;
    }

    public static void setLastId(Long lastId) {
        Avion.lastId = lastId;
    }

    @Override
    public String toString() {
        return "Avion{" +
                "id=" + id +
                ", numeroRegistro=" + numeroRegistro +
                ", tipo='" + tipo + '\'' +
                ", codigoModelo='" + codigoModelo + '\'' +
                ", capacidad=" + capacidad +
                ", fechaPrimerVuelo=" + fechaPrimerVuelo +
                ", estatus=" + estatus +
                ", aerolinea=" + aerolinea +
                '}';
    }
}
