package com.alonso.webapp.proyecto.controllers;

import com.alonso.webapp.proyecto.models.Aeropuerto;
import com.alonso.webapp.proyecto.models.Avion;
import com.alonso.webapp.proyecto.models.dao.AeropuertoDAO;
import com.alonso.webapp.proyecto.models.dao.AvionDAO;
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

@WebServlet("/aviones/listar")
public class ListarAvionServlet extends HttpServlet {

    private Logger log = Logger.getLogger(ListarAvionServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");

        AvionDAO dao = new AvionDAO(con);
        List<Avion> avions;

        try{
            avions = dao.listar();
            log.info("AVIONES LISTADOS CORRECTAMENTE, TOTAL: " + avions.size());
            req.setAttribute("avions", avions);
            getServletContext().getRequestDispatcher("/listarAviones.jsp").forward(req, resp);

        }catch (SQLException e){
            req.setAttribute("exception", e); //COMENTAR ESTO SI VA A PRODUCCION
            log.log(Level.SEVERE, "ERROR AL LISTAR AVIONES" + e.getMessage(),e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);

        }
    }
}
