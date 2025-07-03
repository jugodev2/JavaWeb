package com.alonso.webapp.proyecto.models.dao;

import com.alonso.webapp.proyecto.models.Aerolinea;
import com.alonso.webapp.proyecto.models.enums.Estatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AerolineaDAO implements IDAO<Aerolinea> {

    private final Connection conn;

    public AerolineaDAO(Connection conn) {
        this.conn = conn;
    }


    @Override
    public List<Aerolinea> listar() throws SQLException {
        List<Aerolinea> aerolineas = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT *FROM AEROLINEA")){
            while (rs.next()){
                aerolineas.add(obtenerDato(rs));
            }
        }
        return aerolineas;
    }

    @Override
    public Optional<Aerolinea> buscarPorId(Long id) throws SQLException {
        Aerolinea aerolinea = null;
        String query = "SELECT *FROM AEROLINEA WHERE ID_AEROLINEA = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setLong(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    aerolinea = obtenerDato(rs);
                }
            }
        }

        return Optional.ofNullable(aerolinea);
    }

    @Override
    public void insertar(Aerolinea elemento) throws SQLException {
        String sql = "CALL NUEVA_AEROLINEA(?, ?, ?, ?, ?, ?)";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setLong(1, 0L);// SE TRABAAJA CON 0
            cs.setString(2, elemento.getNombre());
            cs.setString(3, elemento.getIata());
            cs.setString(4, elemento.getPais());
            cs.setDate(5, Date.valueOf(elemento.getFechaFundacion()));
            cs.setLong(6, elemento.getEstatus() == Estatus.DISPONIBLE ? 1 : 2);
            cs.executeUpdate();
        }


    }

    @Override
    public void editor(Aerolinea elemento) throws SQLException {
        String sql = "CALL NUEVA_AEROLINEA(?, ?, ?, ?, ?, ?)";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setLong(1, elemento.getId());// SE TRABAAJA CON 0
            cs.setString(2, elemento.getNombre());
            cs.setString(3, elemento.getIata());
            cs.setString(4, elemento.getPais());
            cs.setDate(5, Date.valueOf(elemento.getFechaFundacion()));
            cs.setLong(6, elemento.getEstatus() == Estatus.DISPONIBLE ? 1 : 2);
            cs.executeUpdate();
        }


    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "CALL ELIMINAR_AEROLINEA(?)";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setLong(1, id);// SE TRABAAJA CON 0
            cs.executeUpdate();
        }
    }

    @Override
    public Aerolinea obtenerDato(ResultSet rs) throws SQLException {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setId(rs.getLong("ID_AEROLINEA"));
        aerolinea.setNombre(rs.getString("NOMBRE"));
        aerolinea.setIata(rs.getString("IATA"));
        aerolinea.setPais(rs.getString("PAIS"));
        aerolinea.setEstatus(rs.getLong("ID_ESTATUS") == 1 ?
                Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE);
        aerolinea.setFechaFundacion(rs.getDate("FECHA_FUNDACION").toLocalDate());
        return aerolinea;
    }
}