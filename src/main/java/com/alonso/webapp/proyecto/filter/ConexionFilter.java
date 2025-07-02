package com.alonso.webapp.proyecto.filter;

import com.alonso.webapp.proyecto.utils.ConexionDB;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter("/*")
public class ConexionFilter implements Filter {

    private Connection getConnection(){
        return ConexionDB.getConnection();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Connection con = null;
        try {
            con = this.getConnection();
            request.setAttribute("conn", con);
            chain.doFilter(request, response);
        }finally {
            if (con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
