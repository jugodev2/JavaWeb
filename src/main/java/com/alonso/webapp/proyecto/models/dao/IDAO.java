package com.alonso.webapp.proyecto.models.dao;

import org.xml.sax.SAXException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IDAO<T>  {
    List<T> listar() throws SAXException, SQLException;
    Optional<T> buscarPorId(Long id) throws SQLException;
    void insertar(T elemento)throws SQLException;
    void editor(T elemento)throws SQLException;
    void eliminar(Long id)throws SQLException;
    T obtenerDato(ResultSet rs)throws SQLException;



}
