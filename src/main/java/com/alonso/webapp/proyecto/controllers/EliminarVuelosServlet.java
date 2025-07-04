package com.alonso.webapp.proyecto.controllers;

import com.alonso.webapp.proyecto.models.Avion;
import com.alonso.webapp.proyecto.models.Vuelo;
import com.alonso.webapp.proyecto.models.dao.AvionDAO;
import com.alonso.webapp.proyecto.models.dao.VueloDAO;
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

@WebServlet("/vuelos/eliminar")
public class EliminarVuelosServlet extends HttpServlet {

    private Logger log = Logger.getLogger(EliminarVuelosServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");
        VueloDAO dao = new VueloDAO(con);

        long id;
        try {
            id = Long.valueOf(req.getParameter("id"));
            Optional<Vuelo> vuelo = dao.buscarPorId(id);
            if (vuelo.isPresent()){
                dao.eliminar(id);
                resp.sendRedirect(req.getContextPath()+"/vuelos/listar");

            }else {
                log.log(Level.WARNING, "EL VUELO CON EL ID: " + id + "NO EXISTE");
                req.setAttribute("excepcion", new Exception("NO EXISTE EL VUELO CON EL ID " + id));
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("excepcion", e);
            log.log(Level.SEVERE, "VALOR DE ID INVALIDO " + e.getMessage(), e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("excepcion", e);
            log.log(Level.SEVERE, "Error al eliminar la VUELO" + e.getMessage(), e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
