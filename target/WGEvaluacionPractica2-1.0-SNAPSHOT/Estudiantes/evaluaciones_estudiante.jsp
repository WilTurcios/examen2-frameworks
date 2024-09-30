<%-- 
    Document   : evaluaciones_estudiante
    Created on : 28 sep 2024, 11:13:39 p.m.
    Author     : Wilber's-Laptop
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
    <%
         List<Evaluacion> evaluaciones = (List<Evaluacion>) request.getAttribute("studentEvaluations");
         
         if(evaluaciones != null && evaluaciones.size() != 0) { 
    %>
    
    <div class="container mt-5">
        <h1 class="text-center">Lista de Evaluaciones de <%= evaluaciones.get(0).getEstudiante().getNombre() %></h1>
       
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Curso</th>
                    <th>Calificación</th>
                    <th>Fecha</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                   
                    if (evaluaciones != null) {
                        for (Evaluacion evaluacion : evaluaciones) {
                            Estudiante estudiante = evaluacion.getEstudiante();
                            Curso curso = evaluacion.getCurso();
                            
                            BigDecimal bd = new BigDecimal(evaluacion.getCalificacion());
                            BigDecimal redondeado = bd.setScale(1, RoundingMode.HALF_UP);
                %>
                <tr>
                    <td><%= evaluacion.getId() %></td>
                    <td><%= curso.getNombre() %></td>
                    <td><%= redondeado %></td>
                    <td><%= evaluacion.getFecha()%></td>
                    <td>
                        <!-- Botón para editar -->
                        <button class="btn btn-primary" onclick="editarEvaluacion(<%= evaluacion.getId() %>)">Editar</button>
                        <!-- Botón para eliminar -->
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
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script>
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

        function cargarEstudiantes() {
            fetch('/WGEvaluacionPractica2/estudiantes?list')
            .then(response => response.json())
            .then(estudiantes => {
                const selectEstudiante = document.getElementById('estudiante');
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
        
        function cargarCursos() {
            fetch('/WGEvaluacionPractica2/cursos?list')
            .then(response => response.json())
            .then(cursos => {
                const selectCursos = document.getElementById('curso');
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
            cargarEstudiantes();
            cargarCursos();
        };

        function editarEvaluacion(id) {
            
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
    
    <%
        } else {
    %>
        <h1 class="text-center">No hay evaluaciones :(</h1>
    <%
        }
    %>
</body>
</html>
