package com.alonso.webapp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/primero")
public class ServletUno extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String metodo = req.getParameter("metodo");
        //
        if ("redirect".equalsIgnoreCase(metodo)){
            resp.sendRedirect("primero?desde=sendRequired");
        }else{
            String urlOriginal = req.getRequestURI() +
                    (req.getQueryString() != null ? "?"+req.getQueryString():"");
            req.setAttribute("urlVisible", urlOriginal);
            req.setAttribute("desde","forward");
            req.getRequestDispatcher("segundo").forward(req,resp);

        }
    }
}
