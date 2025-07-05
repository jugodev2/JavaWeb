<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="java.util.List" %>
    <%@ page import="com.alonso.webapp.proyecto.models.Aeropuerto" %>
    <%@ page import="com.alonso.webapp.proyecto.models.enums.Estatus" %>
    <%
      Aeropuerto aeropuerto = (Aeropuerto) request.getAttribute("aeropuerto");
    %>
      <!DOCTYPE html>
      <html lang="es">

      <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Formulario de Aeropuerto</title>
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
          <h2 class="mb-4">Edición de Aeropuertos</h2>
          <h3>Aeropuerto <%=aeropuerto.getNombre()%></h3>
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
                <form action="<%=request.getContextPath()%>/aeropuertos/actualizar" method="post">
                  <div class="mb-3">
                    <input type="hidden" class="form-control" id="id" name="id" value="<%=aeropuerto.getId()%>">
                  </div>
                  <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" value="<%=aeropuerto.getNombre()%>"
                      pattern=".*\S.*" required>
                  </div>
                  <div class="mb-3">
                    <label for="codigo" class="form-label">Código del Aeropuerto</label>
                    <input type="text" class="form-control" id="codigo" name="codigo" value="<%=aeropuerto.getCodigo()%>" pattern=".*\S.*"
                      required>
                  </div>
                  <div class="mb-3">
                    <label for="latitud" class="form-label">Latitud</label>
                    <input type="text" class="form-control" id="latitud" name="latitud" value="<%=aeropuerto.getLatitud()%>" pattern=".*\S.*"
                      required>
                  </div>
                  <div class="mb-3">
                    <label for="longitud" class="form-label">Código </label>
                    <input type="text" class="form-control" id="longitud" name="longitud" value="<%=aeropuerto.getLongitud()%>" pattern=".*\S.*"
                      required>
                  </div>
                  <div class="mb-3">
                    <label for="pais" class="form-label">País</label>
                    <select class="form-select" id="pais" name="pais">
                     <option value="" <%=aeropuerto.getPais().equals("") ? "selected" : "" %>>Selecciona un país</option>
                      <option value="Mexico" <%=aeropuerto.getPais().equals("Mexico") ? "selected" : "" %>>México</option>
                      <option value="Argentina" <%=aeropuerto.getPais().equals("Argentina") ? "selected" : "" %>>Argentina</option>
                      <option value="Colombia" <%=aeropuerto.getPais().equals("Colombia") ? "selected" : "" %>>Colombia</option>
                      <option value="Chile" <%=aeropuerto.getPais().equals("Chile") ? "selected" : "" %>>Chile</option>
                      <option value="Peru" <%=aeropuerto.getPais().equals("Peru") ? "selected" : "" %>>Perú</option>
                      <option value="España" <%=aeropuerto.getPais().equals("España") ? "selected" : "" %>>España</option>
                      <option value="Estados Unidos" <%=aeropuerto.getPais().equals("Estados Unidos") ? "selected" : "" %>>Estados Unidos</option>
                      <option value="Canada" <%=aeropuerto.getPais().equals("Canada") ? "selected" : "" %>>Canadá</option>
                    </select>
                  </div>
                  <div class="mb-3">
                    <label for="estatus" class="form-label">Estatus</label>
                    <select class="form-select" id="estatus" name="estatus" required>
                      <option value="1" <%= aeropuerto.getEstatus() == Estatus.DISPONIBLE ? "selected" : "" %>>Disponible</option>
                      <option value="2" <%= aeropuerto.getEstatus() == Estatus.NO_DISPONIBLE ? "selected" : "" %>>No Disponible</option>
                    </select>
                  </div>
                  <div class="d-flex justify-content-between">
                    <a href="<%=request.getContextPath()%>/aeropuertos/listar" class="btn btn-secondary">Volver</a>
                    <button type="submit" class="btn btn-primary">Actualizar Aeropuerto</button>
                  </div>
                </form>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
      </body>

      </html>