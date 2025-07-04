package com.alonso.webapp.proyecto.controllers;

import com.alonso.webapp.proyecto.models.Aerolinea;
import com.alonso.webapp.proyecto.models.Aeropuerto;
import com.alonso.webapp.proyecto.models.dao.AerolineaDAO;
import com.alonso.webapp.proyecto.models.dao.AeropuertoDAO;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/aeropuertos/alta")
public class RegistrarAeropuertosServlet extends HttpServlet {
    private Logger log = Logger.getLogger(RegistrarAeropuertosServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/altaAeropuerto.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");
        AeropuertoDAO dao = new AeropuertoDAO(con);

        String nombre = req.getParameter("nombre");
        String codigo = req.getParameter("codigo");
        String latitud = req.getParameter("latitud");
        String longitud = req.getParameter("longitud");
        String pais = req.getParameter("pais");
        String estatus = req.getParameter("estatus");

        List<String> errores = new ArrayList<>();
        if (nombre == null || nombre.isBlank()) {
            errores.add("El Nombre es obligatorio");
        }
        if (codigo == null || codigo.isBlank()) {
            errores.add("El codigo es obligatorio");
        }
        if (estatus == null || (!estatus.equals("1") && !estatus.equals("2"))) {
            errores.add("El estatus es obligatorio");
        }
        if (pais == null || pais.isBlank()) {
            errores.add("El Pais es obligatoria");
        }
        if (longitud == null || longitud.isBlank()) {
            errores.add("La latitud es obligatoria");
        }
        if (latitud == null || latitud.isBlank()) {
            errores.add("La longitud es obligatoria");
        }

        if (!errores.isEmpty()) {
            Aeropuerto aeropuerto = new Aeropuerto(0L, nombre, codigo,
                    latitud, longitud, pais, "1".equals(estatus) ? Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE);
            req.setAttribute("aeropuerto", aeropuerto);
            req.setAttribute("errores", errores);
            doGet(req, resp);
            return;
        }

        Aeropuerto aeropuerto = new Aeropuerto(0L, nombre, codigo,
                latitud, longitud, pais,
                Integer.parseInt(estatus) == 1 ? Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE);
        try {
            dao.insertar(aeropuerto);
            log.info("AEROPUERTO REGISTRADO: " + aeropuerto);
            resp.sendRedirect(req.getContextPath() + "/aeropuertos/listar");
        } catch (SQLException e) {
            req.setAttribute("exception", e);
            log.log(Level.SEVERE, "ERROR AL REGISTRAR AEROPUERTO" + e.getMessage(), e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
