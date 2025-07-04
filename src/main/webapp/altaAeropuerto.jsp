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
        <div class="container mt-5">
          <h2 class="mb-4">Alta de Aeropuertos</h2>
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
                <form action="<%=request.getContextPath()%>/aeropuertos/alta" method="post">
                  <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" value="${param.nombre}"
                      pattern=".*\S.*" required>
                  </div>
                  <div class="mb-3">
                    <label for="codigo" class="form-label">Código del Aeropuerto</label>
                    <input type="text" class="form-control" id="codigo" name="codigo" value="${param.codigo}" pattern=".*\S.*"
                      required>
                  </div>
                  <div class="mb-3">
                    <label for="latitud" class="form-label">Latitud</label>
                    <input type="text" class="form-control" id="latitud" name="latitud" value="${param.latitud}" pattern=".*\S.*"
                      required>
                  </div>
                  <div class="mb-3">
                    <label for="longitud" class="form-label">Código </label>
                    <input type="text" class="form-control" id="longitud" name="longitud" value="${param.longitud}" pattern=".*\S.*"
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
                  <div class="d-flex justify-content-between">
                    <a href="<%=request.getContextPath()%>/aeropuertos/listar" class="btn btn-secondary">Volver</a>
                    <button type="submit" class="btn btn-primary">Registrar Aeropuerto</button>
                  </div>
                </form>
        </div>
      </body>

      </html>