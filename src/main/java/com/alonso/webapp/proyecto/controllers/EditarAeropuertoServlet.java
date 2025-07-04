package com.alonso.webapp.proyecto.controllers;

import com.alonso.webapp.proyecto.models.Aeropuerto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/aeropuertos/actualizar")
public class EditarAeropuertoServlet extends HttpServlet {
    private Logger log = Logger.getLogger(EditarAeropuertoServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");
        AeropuertoDAO dao = new AeropuertoDAO(con);

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
            Optional<Aeropuerto> aeropuerto = dao.buscarPorId(id);

            if (aeropuerto.isPresent()){
                req.setAttribute("aeropuerto", aeropuerto.get());
                getServletContext().getRequestDispatcher("/editarAeropuerto.jsp").forward(req, resp);
            } else {
                log.log(Level.WARNING, "EL AEROPUERTO CON EL ID: " + id + "NO EXISTE");
                req.setAttribute("excepcion", new Exception("NO EXISTEN LA AEROLINEA CON EL ID " + id));
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("excepcion", e);
            log.log(Level.SEVERE, "VALOR DE ID INVALIDO " + e.getMessage(), e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("excepcion", e);
            log.log(Level.SEVERE, "Error al reperar el aeropuerto" + e.getMessage(), e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }
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
        long id = 0;

        try {
            id = Long.valueOf(req.getParameter("id"));

        } catch (NumberFormatException e) {
            req.setAttribute("exception", e);
            log.log(Level.SEVERE, "ERROR AL OBTENER EL ID DEL AEROPUERTO: " + e.getMessage(),e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }

        List<String> errores = new ArrayList<>();
        if (nombre == null || nombre.isBlank()){
            errores.add("El Nombre es obligatorio");
        }if (codigo == null || codigo.isBlank()){
            errores.add("El codigo es obligatorio");
        }if (estatus == null || (!estatus.equals("1") && !estatus.equals("2") )){
            errores.add("El estatus es obligatorio");
        }if (pais == null || pais.isBlank()){
            errores.add("El Pais es obligatoria");
        }if (longitud == null || longitud.isBlank()){
            errores.add("La latitud es obligatoria");
        }if (latitud == null || latitud.isBlank()){
            errores.add("La longitud es obligatoria");
        }

        if (!errores.isEmpty()){
            Aeropuerto aeropuerto = new Aeropuerto(id, nombre, codigo,
                    latitud, longitud, pais, "1".equals(estatus) ? Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE);
            req.setAttribute("aeropuerto",aeropuerto);
            req.setAttribute("errores", errores);
            doGet(req, resp);
            return;
        }

        Aeropuerto aeropuerto = new Aeropuerto(id, nombre, codigo,
                latitud, longitud, pais,
                Integer.parseInt(estatus) == 1 ? Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE);
        try {
            dao.editor(aeropuerto);
            log.info("AEROPUERTO ACTUALIZADO: "+aeropuerto);
            resp.sendRedirect(req.getContextPath()+"/aeropuertos/listar");
        }catch (SQLException e){
            req.setAttribute("exception", e);
            log.log(Level.SEVERE, "ERROR AL ACTUALIZAR AEROPUERTO" + e.getMessage(),e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
