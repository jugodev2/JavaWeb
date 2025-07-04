<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="java.util.List" %>
    <%@ page import="com.alonso.webapp.proyecto.models.Avion" %>
    <%@ page import="com.alonso.webapp.proyecto.models.Aeropuerto" %>

      <!DOCTYPE html>
      <html lang="es">

      <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Formulario de Vuelos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
      </head>

      <body>
                    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                        <a class="navbar-brand" href="#">
                            <img src="https://cdn-icons-png.flaticon.com/512/3324/3324544.png" width="30" height="30" class="d-inline-block align-top" alt="">
                            Trivago
                        </a>
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarText">
                            <ul class="navbar-nav mr-auto">
                                <li class="nav-item active">
                                    <a class="nav-link" href="<%= request.getContextPath() %>/index.jsp">Home <span class="sr-only"></span></a>
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="<%= request.getContextPath() %>/JavaWeb/index.jsp" id="aerolineas" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        Aerolineas
                                    </a>
                                    <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/aerolineas/listar">Listar</a></li>
                                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/aerolineas/alta">Agregar</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="aeropuertos" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        Aeropuertos
                                    </a>
                                    <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/aeropuertos/listar">Listar</a></li>
                                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/aeropuertos/alta">Agregar</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="aviones" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        Aviones
                                    </a>
                                    <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/aviones/listar">Listar</a></li>
                                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/aviones/alta">Agregar</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="vuelos" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        Vuelos
                                    </a>
                                    <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/vuelos/listar">Listar</a></li>
                                        <li><a class="dropdown-item" href="<%= request.getContextPath() %>/vuelos/alta">Agregar</a></li>
                                    </ul>
                                </li>

                            </ul>
                            <span class="navbar-text">

                        </span>
                        </div>
                    </nav>
        <div class="container mt-5">
          <h2 class="mb-4">Registro de Vuelos</h2>
          <% List<String> errores = (List<String>) request.getAttribute("errores");
              if (errores != null && !errores.isEmpty()) {

              %>
              <div class="alert alert-danger">
                <ul>
                  <% for (String error : errores){ %>
                    <li>
                      <%= error %>
                    </li>
                    <% } %>
                </ul>
              </div>
              <% } %>
                <form action="<%=request.getContextPath()%>/vuelos/alta" method="post">
                <div class="mb-3">
                    <label for="codigo" class="form-label">Codigo de Vuelo</label>
                    <input type="text" class="form-control" id="codigo" name="codigo" value="${param.codigo}" pattern=".*\S.*"
                      required>
                  </div>
                   <label for="avion" class="form-label">Aviones</label>
                   <% List<Avion> avions = (List<Avion>) request.getAttribute("aviones"); %>
                    <select class="form-select" id="avion" name="avion" required>
                     <% for (Avion  a : avions){ %>
                      <option value="<%=a.getId()%>"><%=a.getTipo()%> <%=a.getCodigoModelo()%></option>
                      <% } %>
                    </select>

                    <label for="origen" class="form-label">Aeropuerto Origen</label>
                   <% List<Aeropuerto> origenes = (List<Aeropuerto>) request.getAttribute("origenes"); %>
                    <select class="form-select" id="origen" name="origen" required>
                     <% for (Aeropuerto  a : origenes){ %>
                      <option value="<%=a.getId()%>"><%=a.getNombre()%> - <%=a.getPais()%></option>
                      <% } %>
                    </select>

                    <label for="destino" class="form-label">Aeropuerto Destino</label>
                   <% List<Aeropuerto> destinos = (List<Aeropuerto>) request.getAttribute("destinos"); %>
                    <select class="form-select" id="destino" name="destino" required>
                     <% for (Aeropuerto  a : destinos){ %>
                      <option value="<%=a.getId()%>"><%=a.getNombre()%> - <%=a.getPais()%></option>
                      <% } %>
                    </select>

                  <div class="mb-3">
                    <label for="estatus" class="form-label">Estatus</label>
                    <select class="form-select" id="estatus" name="estatus" required>
                      <option value="1">Disponible</option>
                      <option value="2">No Disponible</option>
                    </select>
                  </div>

                  <div class="mb-3">
                  </div>
                  <div class="mb-3">
                    <label for="salida" class="form-label">Fecha de Salida del Vuelo</label>
                    <input type="date" class="form-control" id="salida" name="salida"
                      value="${param.salida}" pattern=".*\S.*" required>
                  </div>
                  <div class="d-flex justify-content-between">
                    <a href="<%=request.getContextPath()%>/vuelos/listar" class="btn btn-secondary">Volver</a>
                    <button type="submit" class="btn btn-primary">Registrar Vuelo</button>
                  </div>
                </form>
        </div>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
              const today = new Date();
              const minDate = today.toISOString().split('T')[0];
              const primerInput = document.getElementById('salida');
              primerInput.min = minDate;
            });
        </script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
      </body>

      </html>
