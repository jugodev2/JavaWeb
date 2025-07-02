package com.alonso.webapp.proyecto.controllers;

import com.alonso.webapp.proyecto.models.Aerolinea;
import com.alonso.webapp.proyecto.models.dao.AerolineaDAO;
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

@WebServlet("/aerolineas/listar")
public class ListarAerolineaServlet extends HttpServlet {

    private Logger log = Logger.getLogger(ListarAerolineaServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");

        AerolineaDAO dao = new AerolineaDAO(con);
        List<Aerolinea> aerolineas;

        try{
            aerolineas = dao.listar();
            log.info("AEROLINEAS LISTADAS CORRECTAMENTE, TOTAL: " + aerolineas.size());
            req.setAttribute("aerolineas", aerolineas);
            getServletContext().getRequestDispatcher("/listarAerolineas.jsp").forward(req, resp);

        }catch (SQLException e){
            req.setAttribute("exception", e); //COMENTAR ESTO SI VA A PRODUCCION
            log.log(Level.SEVERE, "ERROR AL LISTAR AEROLINEAS" + e.getMessage(),e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);

        }

    }
}