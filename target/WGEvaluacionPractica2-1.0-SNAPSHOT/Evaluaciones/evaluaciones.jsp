<%-- 
    Document   : Evaluaciones.jsp
    Created on : 23 sep 2024, 1:59:07 p.m.
    Author     : Wilber
--%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.math.BigDecimal"%>
<%@ page import="java.util.List" %>
<%@ page import="models.Evaluacion" %>
<%@ page import="models.Estudiante" %>
<%@ page import="models.Curso" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Evaluaciones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
    <jsp:include page="../components/menu.jsp" />
    <div class="container mt-5">
       <h1 class="text-center">Lista de Evaluaciones</h1>
       <h2>Agregar nueva evaluación</h2>
       <form id="form-evaluacion" class="mb-4">
            <div class="mb-3">
                <label for="estudiante" class="form-label">Seleccionar Estudiante</label>
                <select class="form-control" id="estudiante" required>
                    <option value="">Selecciona un estudiante</option>
                    <!-- Aquí se llenarán las opciones dinámicamente -->
                </select>
            </div>

            <div class="mb-3">
                <label for="curso" class="form-label">Seleccionar Curso</label>
                <select class="form-control" id="curso" required>
                    <option value="">Selecciona un curso</option>
                    <!-- Aquí se llenarán las opciones dinámicamente -->
                </select>
            </div>

            <div class="mb-3">
                <label for="fecha" class="form-label">Fecha de la Evaluación</label>
                <input type="date" class="form-control" id="fecha" required>
            </div>

            <div class="mb-3">
                <label for="calificacion" class="form-label">Calificación</label>
                <input type="number" class="form-control" id="calificacion" step="0.01" min="0" max="10" placeholder="Ejemplo: 8.5" required>
            </div>

            <button type="button" class="btn btn-success" onclick="agregarEvaluacion()">Agregar Evaluación</button>
        </form>

        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Estudiante</th>
                    <th>Curso</th>
                    <th>Calificación</th>
                    <th>Fecha</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Evaluacion> evaluaciones = (List<Evaluacion>) request.getAttribute("evaluaciones");
                    if (evaluaciones != null) {
                        for (Evaluacion evaluacion : evaluaciones) {
                            Estudiante estudiante = evaluacion.getEstudiante();
                            Curso curso = evaluacion.getCurso();
                            
                            BigDecimal bd = new BigDecimal(evaluacion.getCalificacion());
                            BigDecimal redondeado = bd.setScale(1, RoundingMode.HALF_UP);
                %>
                <tr>
                    <td><%= evaluacion.getId() %></td>
                    <td><%= estudiante.getNombre() %></td>
                    <td><%= curso.getNombre() %></td>
                    <td><%= redondeado %></td>
                    <td><%= evaluacion.getFecha()%></td>
                    <td>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#miModal" onclick="seleccionarEvaluacion(<%= evaluacion.getId() %>)">
                          Editar
                        </button>
                        <button class="btn btn-danger" onclick="eliminarEvaluacion(<%= evaluacion.getId() %>)">Eliminar</button>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="4" class="text-center">No hay evaluaciones registradas.</td>
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
              <h5 class="modal-title" id="modalLabel">Editar Evaluación</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="form-update-evaluacion" class="mb-4">
                    <input type="hidden" id="evaluacion-id"/>

                    <div class="mb-3">
                        <label for="estudiante-update" class="form-label">Seleccionar Estudiante</label>
                        <select class="form-control" id="estudiante-update" required>
                            <option value="">Selecciona un estudiante</option>
                            <!-- Aquí se llenarán las opciones dinámicamente -->
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="curso-update" class="form-label">Seleccionar Curso</label>
                        <select class="form-control" id="curso-update" required>
                            <option value="">Selecciona un curso</option>
                            <!-- Aquí se llenarán las opciones dinámicamente -->
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="fecha-update" class="form-label">Fecha de la Evaluación</label>
                        <input type="date" class="form-control" id="fecha-update" required>
                    </div>

                    <div class="mb-3">
                        <label for="calificacion-update" class="form-label">Calificación</label>
                        <input type="number" class="form-control" id="calificacion-update" step="0.01" min="0" max="10" required>
                    </div>

                    <button type="button" class="btn btn-success" onclick="editarEvaluacion()">Guardar Cambios</button>
                </form>
            </div>
          </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script>
        
        function seleccionarEvaluacion(id) {
            let url = "/WGEvaluacionPractica2/evaluaciones/" + id; 
            fetch(url)
                .then(response => response.json())
                .then(evaluacion => {
                    // Rellena los campos del modal con los datos de la evaluación.
                    document.getElementById("evaluacion-id").value = evaluacion.id;
                    document.getElementById("estudiante-update").value = evaluacion.estudiante.id;
                    document.getElementById("curso-update").value = evaluacion.curso.id;
                    document.getElementById("fecha-update").value = evaluacion.fecha;
                    document.getElementById("calificacion-update").value = evaluacion.calificacion;
                    
                })
                .catch(error => {
                    console.error("Error al cargar la evaluación seleccionada: ", error);
                    alert("Error al cargar la evaluación seleccionada");
                });
        }

        function agregarEvaluacion() {
            const estudianteId = document.getElementById('estudiante').value;
            const cursoId = document.getElementById('curso').value;
            const fecha = document.getElementById('fecha').value;
            const calificacion = document.getElementById('calificacion').value;

            if (!estudianteId) {
                alert("Por favor, selecciona un estudiante.");
                return;
            }
            if (!cursoId) {
                alert("Por favor, selecciona un curso.");
                return;
            }
            if (!fecha) {
                alert("Por favor, selecciona una fecha.");
                return;
            }
            if (!calificacion || calificacion < 0 || calificacion > 10) {
                alert("Por favor, ingresa una calificación válida (0-10).");
                return;
            }

            fetch('/WGEvaluacionPractica2/evaluaciones', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    estudiante: {
                        id: estudianteId
                    },
                    curso: {
                        id: cursoId
                    },
                    fecha: fecha,
                    calificacion: parseFloat(calificacion)
                })
            }).then(response => {
                if (response.ok) {
                    alert("Evaluación agregada exitosamente");
                    location.reload();
                } else {
                    alert("Error al agregar evaluación");
                }
            }).catch(error => {
                console.error("Error en la petición:", error);
                alert("Error al agregar evaluación");
            });
        }

        function cargarEstudiantes(target) {
            fetch('/WGEvaluacionPractica2/estudiantes?list')
            .then(response => response.json())
            .then(estudiantes => {
                estudiantesDB = estudiantes;
                const selectEstudiante = document.getElementById(target);
                estudiantes.forEach(estudiante => {
                    const option = document.createElement('option');
                    option.value = estudiante.id;
                    option.innerText = estudiante.nombre;
                    selectEstudiante.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Error al cargar estudiantes: ", error);
            });
            
            
        }
        
        function cargarCursos(target) {
            fetch('/WGEvaluacionPractica2/cursos?list')
            .then(response => response.json())
            .then(cursos => {
                cursosDB = cursos;
        
                const selectCursos = document.getElementById(target);
                cursos.forEach(curso => {
                    const option = document.createElement('option');
                    option.value = curso.id;
                    option.innerText = curso.nombre;
                    selectCursos.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Error al cargar cursos ", error);
            });
        }

        window.onload = function() {
            cargarEstudiantes('estudiante');
            cargarCursos('curso');
            cargarEstudiantes('estudiante-update');
                    cargarCursos('curso-update');
        };

        function editarEvaluacion() {
            const id = document.getElementById('evaluacion-id').value;
            const estudianteId = document.getElementById('estudiante-update').value;
            const cursoId = document.getElementById('curso-update').value;
            const fecha = document.getElementById('fecha-update').value;
            const calificacion = document.getElementById('calificacion-update').value;
            
            const url = "/WGEvaluacionPractica2/evaluaciones/" + id;

            if (!estudianteId || !cursoId || !fecha || calificacion < 0 || calificacion > 10) {
                alert("Por favor, completa correctamente todos los campos.");
                return;
            }

            const evaluacionActualizada = {
                id: id,
                estudiante: { id: estudianteId },
                curso: { id: cursoId },
                fecha: fecha,
                calificacion: parseFloat(calificacion)
            };

            fetch(url, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(evaluacionActualizada)
            })
            .then(response => {
                if (response.ok) {
                    alert("Evaluación editada exitosamente");
                    location.reload();
                } else {
                    alert("Error al editar la evaluación");
                }
            });
        }
        
        function eliminarEvaluacion(id) {
            const url = "/WGEvaluacionPractica2/evaluaciones/" + id;
            if (confirm("¿Estás seguro de que deseas eliminar esta evaluación?")) {
                fetch(url, {
                    method: 'DELETE'
                }).then(response => {
                    if (response.ok) {
                        alert("Evaluación eliminada exitosamente");
                        location.reload();
                    } else {
                        alert("Error al eliminar la evaluación");
                    }
                });
            }
        }
    </script>
</body>
</html>
