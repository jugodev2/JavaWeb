package com.alonso.webapp.proyecto.controllers;

import com.alonso.webapp.proyecto.models.Vuelo;
import com.alonso.webapp.proyecto.models.dao.VueloDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/vuelos/listar")
public class ListarVuelosServlet extends HttpServlet {
    private Logger log = Logger.getLogger(ListarAvionServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");

        VueloDAO dao = new VueloDAO(con);
        List<Vuelo> vuelos;

        try{
            vuelos = dao.listar();
            log.info("VUELOS LISTADOS CORRECTAMENTE, TOTAL: " + vuelos.size());
            req.setAttribute("vuelos", vuelos);
            getServletContext().getRequestDispatcher("/listarVuelos.jsp").forward(req, resp);

        }catch (SQLException e){
            req.setAttribute("exception", e); //COMENTAR ESTO SI VA A PRODUCCION
            log.log(Level.SEVERE, "ERROR AL LISTAR VUELOS" + e.getMessage(),e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);

        }
    }
}
