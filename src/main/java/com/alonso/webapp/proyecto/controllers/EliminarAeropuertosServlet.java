package com.alonso.webapp.proyecto.controllers;

import com.alonso.webapp.proyecto.models.Aeropuerto;
import com.alonso.webapp.proyecto.models.dao.AeropuertoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/aeropuertos/eliminar")
public class EliminarAeropuertosServlet extends HttpServlet {

    private Logger log = Logger.getLogger(EliminarAeropuertosServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");
        AeropuertoDAO dao = new AeropuertoDAO(con);

        long id;
        try {
            id = Long.valueOf(req.getParameter("id"));
            Optional<Aeropuerto> aerolinea = dao.buscarPorId(id);
            if (aerolinea.isPresent()){
                dao.eliminar(id);
                resp.sendRedirect(req.getContextPath()+"/aeropuertos/listar");

            }else {
                log.log(Level.WARNING, "EL AEROPUERTO CON EL ID: " + id + "NO EXISTE");
                req.setAttribute("excepcion", new Exception("NO EXISTE EL AEROPUERTO CON EL ID " + id));
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("excepcion", e);
            log.log(Level.SEVERE, "VALOR DE ID INVALIDO " + e.getMessage(), e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("excepcion", e);
            log.log(Level.SEVERE, "Error al eliminar la aerolinea" + e.getMessage(), e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
