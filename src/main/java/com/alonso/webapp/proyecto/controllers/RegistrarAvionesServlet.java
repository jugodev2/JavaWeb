package com.alonso.webapp.proyecto.controllers;

import com.alonso.webapp.proyecto.models.Aerolinea;
import com.alonso.webapp.proyecto.models.Avion;
import com.alonso.webapp.proyecto.models.dao.AerolineaDAO;
import com.alonso.webapp.proyecto.models.dao.AvionDAO;
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

@WebServlet("/aviones/alta")
public class RegistrarAvionesServlet extends HttpServlet {

    private Logger log = Logger.getLogger(RegistrarAvionesServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");
        AerolineaDAO aerolineaDAO = new AerolineaDAO(con);
        List<Aerolinea> aerolineaOptional;

        try {
            aerolineaOptional = aerolineaDAO.listar();
            req.setAttribute("aerolineas1", aerolineaOptional);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getServletContext().getRequestDispatcher("/altaAvion.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");
        AvionDAO dao = new AvionDAO(con);

        String numRegistro = req.getParameter("registro");
        String tipo = req.getParameter("tipo");
        String codigo = req.getParameter("codigo");
        String capacidad = req.getParameter("capacidad");
        String primer = req.getParameter("primer");
        String estatus = req.getParameter("estatus");
        String id = req.getParameter("aerolinea");

        long num = 0;
        int capa = 0;
        long aeroId = 0;

        List<String> errores = new ArrayList<>();
        if (numRegistro == null || numRegistro.isBlank() ) {
            errores.add("El Numero de Registro es obligatorio");
        }else {
            num = Long.parseLong(numRegistro);
        }
        if (tipo == null || tipo.isBlank()) {
            errores.add("El tipo es obligatorio");
        }
        if (estatus == null || (!estatus.equals("1") && !estatus.equals("2"))) {
            errores.add("El estatus obligatorio");
        }
        if (primer == null || primer.isBlank()) {
            errores.add("La fecha de Registro es obligatoria");
        }
        if (codigo == null || codigo.isBlank()) {
            errores.add("El codigo es obligatoria");
        }
        if (capacidad == null || capacidad.isBlank()) {
            errores.add("La capacidad es obligatoria");
        } else {
            capa = Integer.parseInt(capacidad);
        }
        if (id == null || id.isBlank()) {
            errores.add("El id no debe ser vacio.");
        } else {
            aeroId = Long.parseLong(id);
        }

        LocalDate fechaPrimerVuelo = null;
        if (primer == null || primer.isBlank()) {
            errores.add("La fecha de primer vuelo es obligatoria");
        } else {
            try {
                fechaPrimerVuelo = LocalDate.parse(primer);
                if (!fechaPrimerVuelo.isBefore(LocalDate.now())) {
                    errores.add("La fecha de primer vuelo debe ser anterior a la fecha actal");
                }
            } catch (DateTimeParseException e) {
                errores.add("La fecha de primer vuelo es invalida");
            }
        }

        if (!errores.isEmpty()) {
            req.setAttribute("errores", errores);
            doGet(req, resp);
            return;
        }

        AerolineaDAO aerolineaDAO = new AerolineaDAO(con);
        Optional<Aerolinea> aerolineaOptional = Optional.empty();
        try {
            aerolineaOptional = aerolineaDAO.buscarPorId(aeroId);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "ERROR AL BUSCAR AEROLINEA" + e.getMessage(),e);
        }

        Aerolinea aerolinea = null;
        if (aerolineaOptional.isPresent()) {
             aerolinea = aerolineaOptional.get();
        }
        Avion avion = new Avion(0L, num,tipo, codigo, capa, fechaPrimerVuelo,
                Integer.parseInt(estatus) == 1 ? Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE,
                aerolinea);
        try {
            dao.insertar(avion);
            log.info("NUEVO AVION REGISTRADO: "+req.getParameter("aerolinea"));
            resp.sendRedirect(req.getContextPath()+"/aviones/listar");
        }catch (SQLException e){
            req.setAttribute("exception", e);
            log.log(Level.SEVERE, "ERROR AL CREAR AVION" + e.getMessage(),e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }

    }
}
