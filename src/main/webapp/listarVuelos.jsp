<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="java.util.List" %>
    <%@ page import="com.alonso.webapp.proyecto.models.Vuelo" %>
        <%@ page import="java.time.format.DateTimeFormatter" %>
            <%@ page import="java.time.LocalDate" %>
                <% List<Vuelo> vuelos = (List<Vuelo>) request.getAttribute("vuelos");
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                 %>
              <!DOCTYPE html>
              <html lang="es">

              <head>
                <meta charset="UTF-8">
                <title>Tabla de Vuelos</title>
                <!-- Enlace a Bootstrap desde CDN -->
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
                  <h2 class="mb-4">Lista de Vuelos</h2>
                  <table class="table table-striped table-bordered">
                    <thead class="table-dark">
                    <tbody>
                        <tr>
                        <th>ID</th>
                        <th>Codigo de Vuelo</th>
                        <th>Avion</th>
                        <th>Lugar de Origen</th>
                        <th>Lugar de Destino</th>
                        <th>Fecha Primer Vuelo</th>
                        <th>Estatus</th>
                        <th>Editar</th>
                        <th>Eliminar</th>
                        </tr>
                      <%
                      for(Vuelo a: vuelos){
                        LocalDate getFechaSalida=a.getFechaSalida();
                        String fechaFormateada=getFechaSalida.format(dateFormatter);
                        %>
                        <tr>
                          <td><%= a.getId() %></td>
                          <td style="display: none;"><%= a.getId() %></td>
                          <td><%=a.getCodigoVuelo()%></td>
                          <td><%=a.getAvion().getTipo()%> <%=a.getAvion().getCodigoModelo()%></td>
                          <td>Aeropueto <%=a.getOrigen().getNombre()%></td>
                          <td>Aeropueto <%=a.getDestino().getNombre()%></td>
                          <td><%=fechaFormateada%></td>
                          <td><%=a.getEstatus().getEstatusEnum()%></td>
                          <td>
                            <form action="<%=request.getContextPath()%>/vuelos/actualizar" method="get" style="display: inline;">
                              <input type="hidden" name="id" value="<%=a.getId()%>"/>
                              <button type="submit" class="btn btn-primary btn-sm me-1">
                                <i class="fas fa-edit">Editar</i>
                              </button>
                            </form>
                          </td>
                          <td>
                            <form action="<%=request.getContextPath()%>/vuelos/eliminar" method="post" style="display: inline;" class="delete-form">
                              <input type="hidden" name="id" value="<%=a.getId()%>"/>
                              <button type="button" class="btn btn-danger btn-sm ms-1 delete-btn">
                                <i class="fas fa-trash-alt">Eliminar</i>
                              </button>
                            </form>
                          </td>
                        </tr>
                        <% } %>
                      </tbody>
                    </thead>
                  </table>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.1/dist/sweetalert2.all.min.js"></script>
                <script>
                  document.querySelectorAll('.delete-btn').forEach(button => {
                    button.addEventListener('click', function (event) {
                      const form = this.closest('form');
                      Swal.fire({
                        title: 'Estas Seguro que deseas eliminar el Vuelo?',
                        text: 'No podras revertir esta accion!',
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Si, eliminar!',
                        cancelButtonText: 'Cancelar'
                      }).then((result) =>{
                        if (result.isConfirmed) {
                          form.submit();
                        }
                      });
                    });
                  });
                </script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
              </body>

              </html>