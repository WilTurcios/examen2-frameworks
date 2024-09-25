<%-- 
    Document   : Estudiantes.jsp
    Created on : 23 sep 2024, 1:59:07 p.m.
    Author     : Wilber
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Curso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tabla de Personas</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Listado de Cursos</h2>
        <table class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Curso</th>
                </tr>
            </thead>
            <tbody>
                
                <%
                    ArrayList<Curso> cursos = (ArrayList<Curso>) request.getAttribute("cursos");
                    for(Curso estudiante : cursos ){
                %>
                    <tr>
                        <td><%= estudiante.getId()%></td>
                        <td><%= estudiante.getNombre()%></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <!-- Bootstrap JS (opcional, para componentes que requieran JavaScript) -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

