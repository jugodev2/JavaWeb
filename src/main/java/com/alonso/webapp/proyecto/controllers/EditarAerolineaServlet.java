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
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/aerolineas/actualizar")
public class EditarAerolineaServlet extends HttpServlet {
    private Logger log = Logger.getLogger(EditarAerolineaServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");
        AerolineaDAO dao = new AerolineaDAO(con);

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
            Optional<Aerolinea> aerolinea = dao.buscarPorId(id);

            if (aerolinea.isPresent()){
                req.setAttribute("aerolinea", aerolinea.get());
                getServletContext().getRequestDispatcher("/editarAerolinea.jsp").forward(req, resp);
            } else {
                log.log(Level.WARNING, "LA AEROLINEA CON EL ID: " + id + "NO EXISTE");
                req.setAttribute("excepcion", new Exception("NO EXISTEN LA AEROLINEA CON EL ID " + id));
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("excepcion", e);
            log.log(Level.SEVERE, "VALOR DE ID INVALIDO " + e.getMessage(), e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("excepcion", e);
            log.log(Level.SEVERE, "Error al reperar la aerolinea" + e.getMessage(), e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con = (Connection) req.getAttribute("conn");
        AerolineaDAO dao = new AerolineaDAO(con);


        String nombre = req.getParameter("nombre");
        String iata = req.getParameter("iata");
        String estatus = req.getParameter("estatus");
        String pais = req.getParameter("pais");
        String fundacion = req.getParameter("fundacion");
        long id = 0;

        try {
            id = Long.valueOf(req.getParameter("id"));

        } catch (NumberFormatException e) {
            req.setAttribute("exception", e);
            log.log(Level.SEVERE, "ERROR AL OBTENER EL ID DE LA AEROLINEA: " + e.getMessage(),e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }

        List<String> errores = new ArrayList<>();
        if (nombre == null || nombre.isBlank()){
            errores.add("El Nombre es obligatorio");
        }if (iata == null || iata.isBlank()){
            errores.add("LA IATA es obligatoria");
        }if (estatus == null || (!estatus.equals("1") && !estatus.equals("2") )){
            errores.add("El estatus obligatorio");
        }if (pais == null || pais.isBlank()){
            errores.add("El Pais es obligatorio");
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

        if (!errores.isEmpty()){
            Aerolinea aerolinea = new Aerolinea(id, nombre, iata,
                    "1".equals(estatus) ? Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE, pais, fechaFundacion);
            req.setAttribute("aerolinea",aerolinea);
            req.setAttribute("errores", errores);
            doGet(req, resp);
            return;
        }

        Aerolinea aerolinea = new Aerolinea(id, nombre,iata,
                Integer.parseInt(estatus) == 1 ? Estatus.DISPONIBLE : Estatus.NO_DISPONIBLE,
                pais, fechaFundacion);

        try {
            dao.editor(aerolinea);
            log.info("AEROLINEA ACTUALIZADO: "+aerolinea);
            resp.sendRedirect(req.getContextPath()+"/aerolineas/listar");
        }catch (SQLException e){
            req.setAttribute("exception", e);
            log.log(Level.SEVERE, "ERROR AL ACTUALIZAR AEROLINEA" + e.getMessage(),e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }


    }
}
