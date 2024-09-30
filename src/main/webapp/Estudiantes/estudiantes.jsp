<%@page import="java.util.ArrayList"%>
<%@page import="models.Estudiante"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Estudiantes</title>
</head>
<body>
    <jsp:include page="../components/menu.jsp" />
    <div class="container mt-5">
        <!-- Formulario para agregar estudiantes -->
        <h2>Formulario de Registro de Estudiantes</h2>
        <form id="formAgregarEstudiante" method="POST">
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" placeholder="Ingrese su nombre" required>
            </div>
            <div class="mb-3">
                <label for="edad" class="form-label">Edad</label>
                <input type="number" class="form-control" id="edad" placeholder="Ingrese su edad" required>
            </div>
            <div class="mb-3">
                <label for="telefono" class="form-label">Teléfono</label>
                <input type="tel" class="form-control" id="telefono" placeholder="Ingrese su número de teléfono" required>
            </div>
            <button type="submit" class="btn btn-success">Enviar</button>
        </form>

        <!-- Tabla con los estudiantes -->
        <h2 class="mt-5">Datos de Estudiantes</h2>
        <table class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Teléfono</th>
                    <th>Edad</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<Estudiante> estudiantes = (ArrayList<Estudiante>) request.getAttribute("estudiantes");
                    for(Estudiante estudiante : estudiantes) {
                %>
                    <tr id="fila-<%= estudiante.getId() %>">
                        <td><%= estudiante.getId() %></td>
                        <td><%= estudiante.getNombre() %></td>
                        <td><%= estudiante.getTelefono() %></td>
                        <td><%= estudiante.getEdad() %></td>
                        <td>
                            <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#miModal" onclick="seleccionarEstudiante(<%= estudiante.getId() %>)">
                                Editar
                            </button>
                            <button class="btn btn-danger btn-sm" onclick="eliminarEstudiante(<%= estudiante.getId() %>)">Eliminar</button>
                            <form action="/WGEvaluacionPractica2/estudiantes" method="GET" class="d-inline-block">
                                <input type="hidden" name="evaluaciones" value="<%= estudiante.getId() %>">
                                <button class="btn btn-success btn-sm">Ver evaluaciones</button>
                            </form>
                        </td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
            
    <div class="modal fade" id="miModal" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="modalLabel">Editar Estudiante</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="form-update-student" class="mb-4">
                    <input type="hidden" id="estudiante-id"/>
                    <div class="mb-3">
                        <label for="nombre-update" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="nombre-update" placeholder="Ingrese su nombre" required>
                    </div>
                    <div class="mb-3">
                        <label for="edad-update" class="form-label">Edad</label>
                        <input type="number" class="form-control" id="edad-update" placeholder="Ingrese su edad" required>
                    </div>
                    <div class="mb-3">
                        <label for="telefono-update" class="form-label">Teléfono</label>
                        <input type="tel" class="form-control" id="telefono-update" placeholder="Ingrese su número de teléfono" required>
                    </div>
                    <button type="button" class="btn btn-success" onclick="editarEstudiante()">Editar Estudiante</button>
                </form>
            </div>
          </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script>
        function seleccionarEstudiante(id) {
            let url = "/WGEvaluacionPractica2/estudiantes/" + id; 
            fetch(url)
                .then(response => response.json())
                .then(estudiante => {
                    // Rellena los campos del modal con los datos de la evaluación.
                    document.getElementById("estudiante-id").value = estudiante.id;
                    document.getElementById("nombre-update").value = estudiante.nombre;
                    document.getElementById("edad-update").value = estudiante.edad;
                    document.getElementById("telefono-update").value = estudiante.telefono;
                    
                })
                .catch(error => {
                    console.error("Error al cargar el estudiante: ", error);
                    alert("Error al cargar el estudiante seleccionado");
                });
        }
        
        document.getElementById('formAgregarEstudiante').addEventListener('submit', function(e) {
            e.preventDefault();

            const nombre = document.getElementById('nombre').value;
            const edad = document.getElementById('edad').value;
            const telefono = document.getElementById('telefono').value;

            const nuevoEstudiante = {
                nombre: nombre,
                edad: edad,
                telefono: telefono
            };

            fetch('/WGEvaluacionPractica2/estudiantes', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(nuevoEstudiante)
            })
            .then(response => {
                if (response.ok) {
                    alert('Estudiante agregado exitosamente');
                    window.location.reload();
                }
            })
            .catch(error => console.error('Error:', error));
        });

        function editarEstudiante() {
            const id = document.getElementById('estudiante-id').value;
            const nombre = document.getElementById('nombre-update').value;
            const edad = document.getElementById('edad-update').value;
            const telefono = document.getElementById('telefono-update').value;
            
            const url = "/WGEvaluacionPractica2/estudiantes/" + id;

            if (!nombre || !edad || !telefono) {
                alert("Por favor, completa correctamente todos los campos.");
                return;
            }

            const estudianteActualizado = {
                id: id,
                nombre: nombre,
                edad: edad,
                telefono: telefono
            };

            fetch(url, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(estudianteActualizado)
            })
            .then(response => {
                if (response.ok) {
                    alert("Estudiante editado exitosamente");
                    location.reload();
                } else {
                    alert("Error al editar el estudiante ");
                }
            });
        }

        function eliminarEstudiante(id) {
            if (confirm('¿Está seguro de que desea eliminar este estudiante?')) {
                const url = "/WGEvaluacionPractica2/estudiantes/" + id;
                
                fetch(url, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (response.ok) {
                        alert('Estudiante eliminado exitosamente');
                        document.getElementById('fila-' + id).remove();
                    }
                })
                .catch(error => console.error('Error:', error));
            }
        }
    </script>
</body>
</html>
