<%@page import="java.util.ArrayList"%>
<%@page import="models.Estudiante"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Estudiantes</title>
</head>
<body>
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
            <button type="submit" class="btn btn-primary">Enviar</button>
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
                            <button class="btn btn-warning btn-sm" onclick="editarEstudiante(<%= estudiante.getId() %>)">Editar</button>
                            <button class="btn btn-danger btn-sm" onclick="eliminarEstudiante(<%= estudiante.getId() %>)">Eliminar</button>
                        </td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script>
        // Función para agregar nuevo estudiante
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

        // Función para editar estudiante
        function editarEstudiante(id) {
            const nombre = prompt('Ingrese el nuevo nombre');
            const edad = prompt('Ingrese la nueva edad');
            const telefono = prompt('Ingrese el nuevo teléfono');

            const estudianteActualizado = {
                nombre: nombre,
                edad: edad,
                telefono: telefono
            };

            fetch(`/WGEvaluacionPractica2/estudiantes/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(estudianteActualizado)
            })
            .then(response => {
                if (response.ok) {
                    alert('Estudiante actualizado exitosamente');
                    window.location.reload();
                }
            })
            .catch(error => console.error('Error:', error));
        }

        // Función para eliminar estudiante
        function eliminarEstudiante(id) {
            if (confirm('¿Está seguro de que desea eliminar este estudiante?')) {
                fetch(`/WGEvaluacionPractica2/estudiantes/${id}`, {
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
