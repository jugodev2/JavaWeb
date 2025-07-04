package com.alonso.webapp.proyecto.controllers;

import com.alonso.webapp.proyecto.models.Aerolinea;
import com.alonso.webapp.proyecto.models.Aeropuerto;
import com.alonso.webapp.proyecto.models.Avion;
import com.alonso.webapp.proyecto.models.Vuelo;
import com.alonso.webapp.proyecto.models.dao.AerolineaDAO;
import com.alonso.webapp.proyecto.models.dao.AeropuertoDAO;
import com.alonso.webapp.proyecto.models.dao.AvionDAO;
import com.alonso.webapp.proyecto.models.dao.VueloDAO;
import com.alonso.webapp.proyecto.models.enums.Estatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/vuelos/actualizar")
public class EditarVuelosServlet extends HttpServlet {

    private Logger log = Logger.getLogger(EditarVuelosServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");
        VueloDAO dao = new VueloDAO(con);
        AvionDAO avionDAO = new AvionDAO(con);
        List<Avion> avions;

        AeropuertoDAO aeropuertoDAO = new AeropuertoDAO(con);
        List<Aeropuerto> aeroOrigen;
        List<Aeropuerto> aeroDestino;

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
            Optional<Vuelo> vuelo = dao.buscarPorId(id);
            aeroOrigen = aeropuertoDAO.listar();
            aeroDestino = aeropuertoDAO.listar();
            avions = avionDAO.listar();
            if (vuelo.isPresent()){
                req.setAttribute("aviones", avions);
                req.setAttribute("origenes", aeroOrigen);
                req.setAttribute("destinos", aeroDestino);
                req.setAttribute("vuelo", vuelo.get());
                getServletContext().getRequestDispatcher("/editarVuelos.jsp").forward(req, resp);
            } else {
                log.log(Level.WARNING, "EL Vuelo CON EL ID: " + id + "NO EXISTE");
                req.setAttribute("excepcion", new Exception("NO EXISTEN EL VUELO CON EL ID: " + id));
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getServletContext().getRequestDispatcher("/editarAVuelo.jsp").forward(req, resp);
}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");
        VueloDAO dao = new VueloDAO(con);

        String codigo = req.getParameter("codigo");
        String avion = req.getParameter("avion");
        String origen = req.getParameter("origen");
        String destino = req.getParameter("destino");
        String estatus = req.getParameter("estatus");
        String salida = req.getParameter("salida");

        long idAvion = 0;
        long idOri = 0;
        long idDest = 0;

        long idVue = 0;

        try {
            idVue = Long.parseLong(req.getParameter("id"));

        } catch (NumberFormatException e) {
            req.setAttribute("exception", e);
            log.log(Level.SEVERE, "ERROR AL OBTENER EL ID DEL VUELO: " + e.getMessage(),e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }

        List<String> errores = new ArrayList<>();
        if (avion == null || avion.isBlank() ) {
            errores.add("El AVION de Registro es obligatorio");
        }else {
            idAvion = Long.parseLong(avion);
        }
        if (origen == null || origen.isBlank()) {
            errores.add("El Origen es obligatorio");
        }else {
            idOri = Long.parseLong(origen);
        }
        if (estatus == null || (!estatus.equals("1") && !estatus.equals("2"))) {
            errores.add("El estatus obligatorio");
        }
        if (codigo == null || codigo.isBlank()) {
            errores.add("El codigo es obligatorio");
        }
        if (destino == null || destino.isBlank()) {
            errores.add("El id no debe ser vacio.");
        } else {
            idDest = Long.parseLong(destino);
        }

        LocalDate fechaSalida = null;
        if (salida == null || salida.isBlank()) {
            errores.add("La fecha de Salida es obligatoria");
        } else {
            try {
                fechaSalida = LocalDate.parse(salida);
                if (!fechaSalida.isAfter(LocalDate.now())) {
                    errores.add("La fecha de Salida NO debe ser anterior a la fecha actal");
                }
            } catch (DateTimeParseException e) {
                errores.add("La fecha de salida vuelo es invalida");
            }
        }

        if (!errores.isEmpty()) {
            req.setAttribute("errores", errores);
            doGet(req, resp);
            return;
        }

        AvionDAO avionDAO = new AvionDAO(con);
        Optional<Avion> avionOptional = Optional.empty();
        try {
            avionOptional = avionDAO.buscarPorId(idAvion);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "ERROR AL BUSCAR AVION" + e.getMessage(),e);
        }

        AeropuertoDAO aeropuertoDAO = new AeropuertoDAO(con);

        Optional<Aeropuerto> aeropuertoOriOpt = Optional.empty();
        try {
            aeropuertoOriOpt = aeropuertoDAO.buscarPorId(idOri);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "ERROR AL BUSCAR EL ORIGEN" + e.getMessage(),e);
        }

        Optional<Aeropuerto> aeropuertoDesOpt = Optional.empty();
        try {
            aeropuertoDesOpt = aeropuertoDAO.buscarPorId(idDest);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "ERROR AL BUSCAR EL DESTINO" + e.getMessage(),e);
        }

        Avion avionSave = null;
        if (avionOptional.isPresent()) {
            avionSave = avionOptional.get();
        }

        Aeropuerto origeAeropuerto = null;
        if (aeropuertoOriOpt.isPresent()) {
            origeAeropuerto = aeropuertoOriOpt.get();
        }

        Aeropuerto destinoAeropuerto = null;
        if (aeropuertoDesOpt.isPresent()) {
            destinoAeropuerto = aeropuertoDesOpt.get();
        }

        Vuelo vuelo = new Vuelo(idVue,codigo,avionSave,origeAeropuerto,destinoAeropuerto,
                Integer.parseInt(estatus) == 1 ? Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE,
                fechaSalida);

        try {
            dao.editor(vuelo);
            log.info("NUEVO AVION REGISTRADO: "+req.getParameter("aerolinea"));
            resp.sendRedirect(req.getContextPath()+"/vuelos/listar");
        }catch (SQLException e){
            req.setAttribute("exception", e);
            log.log(Level.SEVERE, "ERROR AL CREAR AVION" + e.getMessage(),e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }

    }



}
