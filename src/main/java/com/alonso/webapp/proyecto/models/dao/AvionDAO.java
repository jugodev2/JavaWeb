package com.alonso.webapp.proyecto.models.dao;

import com.alonso.webapp.proyecto.models.Aerolinea;
import com.alonso.webapp.proyecto.models.Aeropuerto;
import com.alonso.webapp.proyecto.models.Avion;
import com.alonso.webapp.proyecto.models.enums.Estatus;
import org.xml.sax.SAXException;

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

    }

    @Override
    public void editor(Avion elemento) throws SQLException {

    }

    @Override
    public void eliminar(Long id) throws SQLException {

    }

    @Override
    public Avion obtenerDato(ResultSet rs) throws SQLException {
        Optional aerolinea = new AerolineaDAO(conn).buscarPorId(rs.getLong("ID_AEROLINEA"));
        Avion avion = new Avion();
        avion.setId(rs.getLong("ID_AVION"));
        avion.setNumeroRegistro(rs.getLong("NUM_REGISTRO"));
        avion.setTipo(rs.getString("TIPO"));
        avion.setCodigoModelo(rs.getString("CODIGO_MODELO"));
        avion.setCapacidad(rs.getInt("CAPACIDAD"));
        avion.setFechaPrimerVuelo(rs.getDate("FECHA_PRIMER_VUELO").toLocalDate());
        avion.setEstatus(rs.getLong("ID_ESTATUS") == 1 ?
                Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE);
        if (aerolinea.isPresent()){
            avion.setAerolinea((Aerolinea) aerolinea.get());
        }else {
            avion.setAerolinea(null);
        }
        return avion;
    }
}
