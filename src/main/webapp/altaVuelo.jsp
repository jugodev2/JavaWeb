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


      </body>

      </html>
