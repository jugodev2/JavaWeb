package com.alonso.webapp.proyecto.models.dao;

import com.alonso.webapp.proyecto.models.Aerolinea;
import com.alonso.webapp.proyecto.models.Avion;
import com.alonso.webapp.proyecto.models.enums.Estatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AvionDAO implements IDAO<Avion> {

    private final Connection conn;

    public AvionDAO(Connection conn) {
        this.conn = conn;
    }


    @Override
    public List<Avion> listar() throws  SQLException {
        List<Avion> avions = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT *FROM AVION")){
            while (rs.next()){
                avions.add(obtenerDato(rs));
            }
        }
        return avions;
    }

    @Override
    public Optional<Avion> buscarPorId(Long id) throws SQLException {
        Avion avion = null;
        String query = "SELECT *FROM AVION WHERE ID_AVION = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setLong(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    avion = obtenerDato(rs);
                }
            }
        }
        return Optional.ofNullable(avion);
    }

    @Override
    public void insertar(Avion elemento) throws SQLException {
        String sql = "CALL NUEVO_AVION(?, ?, ?, ?, ?, ?, ?, ?)";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setLong(1, 0L);// SE TRABAAJA CON 0
            cs.setLong(2, elemento.getNumeroRegistro());
            cs.setString(3, elemento.getTipo());
            cs.setString(4, elemento.getCodigoModelo());
            cs.setLong(5, elemento.getCapacidad());
            cs.setDate(6, Date.valueOf(elemento.getFechaPrimerVuelo()));
            cs.setLong(7, elemento.getEstatus() == Estatus.DISPONIBLE ? 1 : 2);
            cs.setLong(8, elemento.getAerolinea().getId());
            cs.executeUpdate();
        }
    }

    @Override
    public void editor(Avion elemento) throws SQLException {
        String sql = "CALL NUEVO_AVION(?, ?, ?, ?, ?, ?, ?, ?)";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setLong(1, elemento.getId());// SE TRABAAJA CON 0
            cs.setLong(2, elemento.getNumeroRegistro());
            cs.setString(3, elemento.getTipo());
            cs.setString(4, elemento.getCodigoModelo());
            cs.setLong(5, elemento.getCapacidad());
            cs.setDate(6, Date.valueOf(elemento.getFechaPrimerVuelo()));
            cs.setLong(7, elemento.getEstatus() == Estatus.DISPONIBLE ? 1 : 2);
            cs.setLong(8, elemento.getAerolinea().getId());
            cs.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "CALL ELIMINAR_AVION(?)";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setLong(1, id);// SE TRABAAJA CON 0
            cs.executeUpdate();
        }
    }

    @Override
    public Avion obtenerDato(ResultSet rs) throws SQLException {
        AerolineaDAO aerolineaDAO = new AerolineaDAO(conn);
        Optional<Aerolinea> aerolinea = aerolineaDAO.buscarPorId(rs.getLong("ID_AEROLINEA"));

        Avion avion = new Avion();
        avion.setId(rs.getLong("ID_AVION"));
        avion.setNumeroRegistro(rs.getLong("NUM_REGISTRO"));
        avion.setTipo(rs.getString("TIPO"));
        avion.setCodigoModelo(rs.getString("CODIGO_MODELO"));
        avion.setCapacidad(rs.getInt("CAPACIDAD"));
        avion.setFechaPrimerVuelo(rs.getDate("FECHA_PRIMER_VUELO").toLocalDate());
        avion.setEstatus(rs.getLong("ID_ESTATUS") == 1 ?
                Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE);
        Aerolinea aer;
        if (aerolinea.isPresent()){
            aer = aerolinea.get();
            avion.setAerolinea(aer);
        }

        return avion;
    }
}
