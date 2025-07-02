<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="java.util.List" %>
    <%@ page import="com.alonso.webapp.proyecto.models.Aerolinea" %>
      <%@ page import="java.time.format.DateTimeFormatter" %>
        <%@ page import="java.time.LocalDate" %>
          <% List<Aerolinea> aerolineas = (List<Aerolinea>) request.getAttribute("aerolineas");
              DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
              %>
              <!DOCTYPE html>
              <html lang="es">

              <head>
                <meta charset="UTF-8">
                <title>Tabla de Aerolineas</title>
                <!-- Enlace a Bootstrap desde CDN -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
              </head>

              <body>
                <div class="container mt-5">
                  <h2 class="mb-4">Lista de Aerolineas</h2>
                  <table class="table table-striped table-bordered">
                    <thead class="table-dark">
                    <tbody>
                        <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>IATA</th>
                        <th>País</th>
                        <th>Fecha Fundación</th>
                        <th>Estatus</th>
                        </tr>
                      <% int contador=1;
                      for(Aerolinea a: aerolineas){
                        LocalDate fechaFundacion=a.getFechaFundacion();
                        String fechaFormateada=fechaFundacion.format(dateFormatter);
                        %>
                        <tr>
                          <td><%= a.getId() %></td>
                          <!-- Columna oculta para almacenar el ID real -->
                          <td style="display: none;"><%= a.getId() %></td>
                          <td><%=a.getNombre()%></td>
                          <td><%=a.getIata()%></td>
                          <td><%=a.getPais()%></td>
                          <td><%=fechaFormateada%></td>
                          <td><%=a.getEstatus().getEstatusEnum()%></td>
                        </tr>
                        <% contador++; } %>
                      </tbody>
                    </thead>
                  </table>
                </div>
              </body>

              </html>