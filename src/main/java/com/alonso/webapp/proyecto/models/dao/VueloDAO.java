package com.alonso.webapp.proyecto.models.dao;

import com.alonso.webapp.proyecto.models.Aerolinea;
import com.alonso.webapp.proyecto.models.Aeropuerto;
import com.alonso.webapp.proyecto.models.Avion;
import com.alonso.webapp.proyecto.models.Vuelo;
import com.alonso.webapp.proyecto.models.enums.Estatus;
import org.xml.sax.SAXException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VueloDAO implements IDAO {
    private final Connection conn;

    public VueloDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Vuelo> listar() throws SQLException {
        List<Vuelo> vuelos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT *FROM VUELO")){
            while (rs.next()){
                vuelos.add(obtenerDato(rs));
            }
        }
        return vuelos;
    }

    @Override
    public Optional buscarPorId(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void insertar(Object elemento) throws SQLException {

    }

    @Override
    public void editor(Object elemento) throws SQLException {

    }

    @Override
    public void eliminar(Long id) throws SQLException {

    }

    @Override
    public Vuelo obtenerDato(ResultSet rs) throws SQLException {
        AvionDAO avionDAO = new AvionDAO(conn);
        AeropuertoDAO aeropuertoDAO = new AeropuertoDAO(conn);
        Optional<Avion> avionOtp = avionDAO.buscarPorId(rs.getLong("ID_AVION"));
        Optional<Aeropuerto> origenOtp = aeropuertoDAO.buscarPorId(rs.getLong("ID_ORIGEN"));
        Optional<Aeropuerto> destinoOtp = aeropuertoDAO.buscarPorId(rs.getLong("ID_DESTINO"));

        Vuelo vuelo = new Vuelo();
        vuelo.setId(rs.getLong("ID_VUELO"));
        vuelo.setCodigoVuelo("CODIGO_VUELO");
        vuelo.setFechaSalida(rs.getDate("FECHA_SALIDA").toLocalDate());
        vuelo.setEstatus(rs.getLong("ID_ESTATUS") == 1 ?
                Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE);

        Avion avion;
        if (avionOtp.isPresent()){
            avion = avionOtp.get();
            vuelo.setAvion(avion);
        }

        Aeropuerto origen;
        if (origenOtp.isPresent()){
            origen = origenOtp.get();
            vuelo.setOrigen(origen);
        }

        Aeropuerto destino;
        if (destinoOtp.isPresent()){
            destino = destinoOtp.get();
            vuelo.setDestino(destino);
        }

        return vuelo;
    }
}
