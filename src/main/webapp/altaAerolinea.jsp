<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="java.util.List" %>
    <%@ page import="com.alonso.webapp.proyecto.models.Aerolinea" %>

      <!DOCTYPE html>
      <html lang="es">

      <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Formulario de Aerolínea</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet"> <!-- Para los íconos -->
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
          <h2 class="mb-4">Registro de Aerolínea</h2>
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
                <form action="<%=request.getContextPath()%>/aerolineas/alta" method="post">
                  <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" value="${param.nombre}"
                      pattern=".*\S.*" required>
                  </div>
                  <div class="mb-3">
                    <label for="iata" class="form-label">Código IATA</label>
                    <input type="text" class="form-control" id="iata" name="iata" value="${param.iata}" pattern=".*\S.*"
                      required>
                  </div>
                  <div class="mb-3">
                    <label for="pais" class="form-label">País</label>
                    <select class="form-select" id="pais" name="pais">
                      <option value="">Selecciona un país</option>
                      <option value="Mexico">México</option>
                      <option value="Argentina">Argentina</option>
                      <option value="Colombia">Colombia</option>
                      <option value="Chile">Chile</option>
                      <option value="Peru">Perú</option>
                      <option value="España">España</option>
                      <option value="Estados Unidos">Estados Unidos</option>
                      <option value="Canada">Canadá</option>
                    </select>
                  </div>
                  <div class="mb-3">
                    <label for="estatus" class="form-label">Estatus</label>
                    <select class="form-select" id="estatus" name="estatus" required>
                      <option value="1">Disponible</option>
                      <option value="2">No Disponible</option>
                    </select>
                  </div>
                  <div class="mb-3">
                    <label for="fundacion" class="form-label">Fecha de Fundación</label>
                    <input type="date" class="form-control" id="fundacion" name="fundacion"
                      value="${param.fundacion}" pattern=".*\S.*" required>
                  </div>
                  <div class="d-flex justify-content-between">
                    <a href="<%=request.getContextPath()%>/aerolineas/listar" class="btn btn-secondary">Volver</a>
                    <button type="submit" class="btn btn-primary">Registrar Aerolinea</button>
                  </div>
                </form>
        </div>
        <script>
          document.addEventListener('DOMContentLoaded', function(){
            const today = new Date;
            today.setDate(today.getDate()-1);
            const maxDate = today.toISOString().split('T')[0];
            const fundacionInput = document.getElementById('fundacion');
            fundacionInput.max = maxDate;

          })

        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
      </body>

      </html>