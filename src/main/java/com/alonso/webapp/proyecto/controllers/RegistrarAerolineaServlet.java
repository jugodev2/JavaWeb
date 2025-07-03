package com.alonso.webapp.proyecto.controllers;

import com.alonso.webapp.proyecto.models.Aerolinea;
import com.alonso.webapp.proyecto.models.dao.AerolineaDAO;
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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/aerolineas/alta")
public class RegistrarAerolineaServlet extends HttpServlet {

    private Logger log = Logger.getLogger(RegistrarAerolineaServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/altaAerolinea.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");
        AerolineaDAO dao = new AerolineaDAO(con);

        String nombre = req.getParameter("nombre");
        String iata = req.getParameter("iata");
        String estatus = req.getParameter("estatus");
        String pais = req.getParameter("pais");
        String aerolinea = req.getParameter("aerlinea");
        String fundacion = req.getParameter("fundacion");

        List<String> errores = new ArrayList<>();
        if (nombre == null || nombre.isBlank()){
            errores.add("El Nombre es obligatorio");
        }if (iata == null || iata.isBlank()){
            errores.add("LA IATA es obligatoria");
        }if (estatus == null || (!estatus.equals("1") && !estatus.equals("2") )){
            errores.add("El estatus obligatorio");
        }if (pais == null || pais.isBlank()){
            errores.add("El Pais es obligatorio");
        }if (aerolinea == null || aerolinea.isBlank()){
            errores.add("La Aaerolinea es obligatoria");
        }

        LocalDate fechaFundacion = null;
        if (fundacion == null || fundacion.isBlank()){
            errores.add("La fecha de fundacion es obligatoria");
        }else {
            try{
                fechaFundacion = LocalDate.parse(fundacion);
                if (!fechaFundacion.isBefore(LocalDate.now())){
                    errores.add("La fecha de fundacion debe ser anterior a la fecha actal");
                }
            } catch (DateTimeParseException e){
                errores.add("La fecha de fundacion es invalida");
            }
        }

        if (errores.isEmpty()){
            req.setAttribute("errores", errores);
            doGet(req, resp);
            return;
        }

        Aerolinea aerolinea1 = new Aerolinea(0L, nombre,iata,
                Integer.parseInt(estatus) == 1 ? Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE,
                pais, fechaFundacion);

        try {
            dao.insertar(aerolinea1);
            log.info("NUEVA AEROLINEA REGISTRADA: "+aerolinea1);
            resp.sendRedirect(req.getContextPath()+"/aerolineas/listar");
        }catch (SQLException e){
            req.setAttribute("exception", e);
            log.log(Level.SEVERE, "ERROR AL CREAR AEROLINEA" + e.getMessage(),e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }

    }
}
