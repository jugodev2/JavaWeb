package com.alonso.webapp.proyecto.models.dao;

import com.alonso.webapp.proyecto.models.Aerolinea;
import com.alonso.webapp.proyecto.models.Aeropuerto;
import com.alonso.webapp.proyecto.models.enums.Estatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AeropuertoDAO implements IDAO<Aeropuerto> {

    private final Connection conn;

    public AeropuertoDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Aeropuerto> listar() throws SQLException {
        List<Aeropuerto> aeropuertos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT *FROM AEROPUERTO")){
            while (rs.next()){
                aeropuertos.add(obtenerDato(rs));
            }
        }
        return aeropuertos;
    }

    @Override
    public Optional<Aeropuerto> buscarPorId(Long id) throws SQLException {
        Aeropuerto aeropuerto = null;
        String query = "SELECT *FROM AEROPUERTO WHERE ID_AEROPUERTO = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setLong(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    aeropuerto = obtenerDato(rs);
                }
            }
        }
        return Optional.ofNullable(aeropuerto);
    }

    @Override
    public void insertar(Aeropuerto elemento) throws SQLException {

    }

    @Override
    public void editor(Aeropuerto elemento) throws SQLException {

    }

    @Override
    public void eliminar(Long id) throws SQLException {

    }

    @Override
    public Aeropuerto obtenerDato(ResultSet rs) throws SQLException {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setId(rs.getLong("ID_AEROPUERTO"));
        aeropuerto.setNombre(rs.getString("NOMBRE"));
        aeropuerto.setCodigo(rs.getString("CODIGO"));
        aeropuerto.setLatitud(rs.getString("LATITUD"));
        aeropuerto.setLongitud(rs.getString("LONGITUD"));
        aeropuerto.setPais(rs.getString("PAIS"));
        aeropuerto.setEstatus(rs.getLong("ID_ESTATUS") == 1 ?
                Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE);
        return aeropuerto;
    }
}
