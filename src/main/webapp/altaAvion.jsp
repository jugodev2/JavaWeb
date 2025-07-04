<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="java.util.List" %>
    <%@ page import="com.alonso.webapp.proyecto.models.Avion" %>
    <%@ page import="com.alonso.webapp.proyecto.models.Aerolinea" %>

      <!DOCTYPE html>
      <html lang="es">

      <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Formulario de Aviones</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
      </head>

      <body>
        <div class="container mt-5">
          <h2 class="mb-4">Registro de Aviones</h2>
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
                <form action="<%=request.getContextPath()%>/aviones/alta" method="post">
                  <div class="mb-3">
                    <label for="registro" class="form-label">Numero de Registro</label>
                    <input type="number" class="form-control" id="registro" name="registro" value="${param.registro}"
                      pattern=".*\S.*" required>
                  </div>
                  <div class="mb-3">
                    <label for="tipo" class="form-label">Tipo</label>
                    <input type="text" class="form-control" id="tipo" name="tipo" value="${param.tipo}" pattern=".*\S.*"
                      required>
                  </div>
                <div class="mb-3">
                    <label for="codigo" class="form-label">Codigo</label>
                    <input type="text" class="form-control" id="codigo" name="codigo" value="${param.codigo}" pattern=".*\S.*"
                      required>
                  </div>
                 <div class="mb-3">
                    <label for="capacidad" class="form-label">Capacidad</label>
                    <input type="number" class="form-control" id="capacidad" name="capacidad" value="${param.capacidad}" pattern=".*\S.*"
                      required>
                  </div>
                  <div class="mb-3">
                    <label for="estatus" class="form-label">Estatus</label>
                    <select class="form-select" id="estatus" name="estatus" required>
                      <option value="1">Disponible</option>
                      <option value="2">No Disponible</option>
                    </select>
                  </div>
                  <div class="mb-3">

                    <label for="aerolinea" class="form-label">Aerolineas</label>
                   <% List<Aerolinea> aeros = (List<Aerolinea>) request.getAttribute("aerolineas1"); %>
                    <select class="form-select" id="aerolinea" name="aerolinea" required>
                     <% for (Aerolinea  a : aeros){ %>
                      <option value="<%=a.getId()%>"><%=a.getNombre()%></option>
                      <% } %>
                    </select>

                  </div>
                  <div class="mb-3">
                    <label for="primer" class="form-label">Fecha de Primer Vuelo</label>
                    <input type="date" class="form-control" id="primer" name="primer"
                      value="${param.primer}" pattern=".*\S.*" required>
                  </div>
                  <div class="d-flex justify-content-between">
                    <a href="<%=request.getContextPath()%>/aerolineas/listar" class="btn btn-secondary">Volver</a>
                    <button type="submit" class="btn btn-primary">Registrar Vuelo</button>
                  </div>
                </form>
        </div>
        <script>
          document.addEventListener('DOMContentLoaded', function(){
            const today = new Date;
            today.setDate(today.getDate()-1);
            const maxDate = today.toISOString().split('T')[0];
            const primerInput = document.getElementById('primer');
            primerInput.max = maxDate;

          })

        </script>


      </body>

      </html>
