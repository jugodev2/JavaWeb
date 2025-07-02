package com.alonso.webapp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/segundo")
public class ServletDos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        boolean fueForward = req.getAttribute("desde") != null;
        String metodoUsado = fueForward ? "forward" : "sendRedirect";

        // Obtener la URL visible para el navegador
        String urlVisible;
        if (fueForward) {
            urlVisible = (String) req.getAttribute("urlVisible");
        } else {
            String uri = req.getRequestURI();
            String query = req.getQueryString();
            urlVisible = uri + (query != null ? "?" + query : "");
        }

        out.println("<html><head><title>Segundo Servlet</title>");
        out.println("<style>");
        out.println("body { font-family: Arial; margin: 40px; }");
        out.println(".box { padding: 20px; border: 2px solid #333; border-radius: 10px; }");
        out.println(".sendRedirect { background-color: #fdd; }");
        out.println(".forward { background-color: #dfd; }");
        out.println("h1 { color: #333; }");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<div class='box " + metodoUsado + "'>");
        out.println("<h1>Segundo Servlet</h1>");
        out.println("<p><strong>Método usado:</strong></p>");
        out.println("<h2>" + (fueForward ? "forward (Despacho interno)" : "sendRedirect (Redirección externa)") + "</h2>");

        out.println("<p><strong>URL visible en el navegador:</strong><br>" + urlVisible + "</p>");
        out.println("<p><strong>URL manejada por el servidor:</strong><br>" + req.getRequestURI() + "</p>");

        out.println("<p><strong>¿Fue una nueva petición HTTP?</strong> " + (fueForward ? "❌ No" : "✅ Sí") + "</p>");
        out.println("</div></body></html>");
    }
}
