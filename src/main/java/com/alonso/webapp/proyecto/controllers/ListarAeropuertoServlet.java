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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/aeropuertos/listar")
public class ListarAeropuertoServlet extends HttpServlet {

    private Logger log = Logger.getLogger(ListarAeropuertoServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");

        AeropuertoDAO dao = new AeropuertoDAO(con);
        List<Aeropuerto> aeropuertos;

        try{
            aeropuertos = dao.listar();
            log.info("AEROPUERTOS LISTADOS CORRECTAMENTE, TOTAL: " + aeropuertos.size());
            req.setAttribute("aeropuertos", aeropuertos);
            getServletContext().getRequestDispatcher("/listarAeropuertos.jsp").forward(req, resp);

        }catch (SQLException e){
            req.setAttribute("exception", e); //COMENTAR ESTO SI VA A PRODUCCION
            log.log(Level.SEVERE, "ERROR AL LISTAR AEROPUERTOS" + e.getMessage(),e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);

        }
    }
}
