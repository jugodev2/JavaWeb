<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="java.util.List" %>
    <%@ page import="com.alonso.webapp.proyecto.models.Aeropuerto" %>
          <% List<Aeropuerto> aeropuertos = (List<Aeropuerto>) request.getAttribute("aeropuertos");

              %>
              <!DOCTYPE html>
              <html lang="es">

              <head>
                <meta charset="UTF-8">
                <title>Tabla de Aeropuertos</title>
                <!-- Enlace a Bootstrap desde CDN -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
              </head>

              <body>
                <div class="container mt-5">
                  <h2 class="mb-4">Lista de Aeropuertos</h2>
                  <table class="table table-striped table-bordered">
                    <thead class="table-dark">
                    <tbody>
                        <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Codigo</th>
                        <th>Latitud</th>
                        <th>Longitud</th>
                        <th>Pais</th>
                        <th>Estatus</th>
                        </tr>
                      <% int contador=1;
                      for(Aeropuerto a: aeropuertos){

                        %>
                        <tr>
                          <td><%= a.getId() %></td>
                          <!-- Columna oculta para almacenar el ID real -->
                          <td style="display: none;"><%= a.getId() %></td>
                          <td><%=a.getNombre()%></td>
                          <td><%=a.getCodigo()%></td>
                          <td><%=a.getLatitud()%></td>
                          <td><%=a.getLongitud()%></td>
                          <td><%=a.getPais()%></td>
                          <td><%=a.getEstatus().getEstatusEnum()%></td>
                        </tr>
                        <% contador++; } %>
                      </tbody>
                    </thead>
                  </table>
                </div>
              </body>

              </html>