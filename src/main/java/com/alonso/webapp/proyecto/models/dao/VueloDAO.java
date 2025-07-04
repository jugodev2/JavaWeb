package com.alonso.webapp.proyecto.models.dao;

import com.alonso.webapp.proyecto.models.Aeropuerto;
import com.alonso.webapp.proyecto.models.Avion;
import com.alonso.webapp.proyecto.models.Vuelo;
import com.alonso.webapp.proyecto.models.enums.Estatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VueloDAO implements IDAO<Vuelo> {
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
    public Optional<Vuelo> buscarPorId(Long id) throws SQLException {
        Vuelo vuelo = null;
        String query = "SELECT *FROM VUELO WHERE ID_VUELO = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setLong(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    vuelo = obtenerDato(rs);
                }
            }
        }
        return Optional.ofNullable(vuelo);
    }


    @Override
    public void insertar(Vuelo elemento) throws SQLException {
        String sql = "CALL NUEVO_VUELO(?, ?, ?, ?, ?, ?, ?)";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setLong(1, 0L);// SE TRABAAJA CON 0
            cs.setString(2, elemento.getCodigoVuelo());
            cs.setLong(3, elemento.getAvion().getId());
            cs.setLong(4, elemento.getOrigen().getId());
            cs.setLong(5, elemento.getDestino().getId());
            cs.setDate(6, Date.valueOf(elemento.getFechaSalida()));
            cs.setLong(7, elemento.getEstatus() == Estatus.DISPONIBLE ? 1 : 2);
            cs.executeUpdate();
        }
    }

    @Override
    public void editor(Vuelo elemento) throws SQLException {
        String sql = "CALL NUEVO_VUELO(?, ?, ?, ?, ?, ?, ?)";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setLong(1, elemento.getId());// SE TRABAAJA CON 0
            cs.setString(2, elemento.getCodigoVuelo());
            cs.setLong(3, elemento.getAvion().getId());
            cs.setLong(4, elemento.getOrigen().getId());
            cs.setLong(5, elemento.getDestino().getId());
            cs.setDate(6, Date.valueOf(elemento.getFechaSalida()));
            cs.setLong(7, elemento.getEstatus() == Estatus.DISPONIBLE ? 1 : 2);
            cs.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "CALL ELIMINAR_VUELO(?)";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setLong(1, id);// SE TRABAAJA CON 0
            cs.executeUpdate();
        }
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
        vuelo.setCodigoVuelo(rs.getString("CODIGO_VUELO"));
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
