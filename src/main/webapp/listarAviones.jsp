<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="java.util.List" %>
    <%@ page import="com.alonso.webapp.proyecto.models.Avion" %>
        <%@ page import="java.time.format.DateTimeFormatter" %>
            <%@ page import="java.time.LocalDate" %>
                <% List<Avion> avions = (List<Avion>) request.getAttribute("avions");
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                 %>
              <!DOCTYPE html>
              <html lang="es">

              <head>
                <meta charset="UTF-8">
                <title>Tabla de Aviones</title>
                <!-- Enlace a Bootstrap desde CDN -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
              </head>

              <body>
                <div class="container mt-5">
                  <h2 class="mb-4">Lista de Aviones</h2>
                  <table class="table table-striped table-bordered">
                    <thead class="table-dark">
                    <tbody>
                        <tr>
                        <th>ID</th>
                        <th>Num. Registro</th>
                        <th>Tipo</th>
                        <th>Codigo Modelo</th>
                        <th>Capacidad</th>
                        <th>Fecha Primer Vuelo</th>
                        <th>Aerolinea</th>
                        <th>Estatus</th>
                        </tr>
                      <% int contador=1;
                      for(Avion a: avions){
                        LocalDate getFechaPrimerVuelo=a.getFechaPrimerVuelo();
                        String fechaFormateada=getFechaPrimerVuelo.format(dateFormatter);
                        %>
                        <tr>
                          <td><%= a.getId() %></td>
                          <!-- Columna oculta para almacenar el ID real -->
                          <td style="display: none;"><%= a.getId() %></td>
                          <td><%=a.getNumeroRegistro()%></td>
                          <td><%=a.getTipo()%></td>
                          <td><%=a.getCodigoModelo()%></td>
                          <td><%=a.getCapacidad()%></td>
                          <td><%=fechaFormateada%></td>
                          <td><%=a.getAerolinea().getNombre()%></td>
                          <td><%=a.getEstatus().getEstatusEnum()%></td>
                        </tr>
                        <% contador++; } %>
                      </tbody>
                    </thead>
                  </table>
                </div>
              </body>

              </html>