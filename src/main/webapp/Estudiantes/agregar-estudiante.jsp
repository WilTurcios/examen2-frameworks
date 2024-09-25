<%-- 
    Document   : agregar-estudiante
    Created on : 23 sep 2024, 3:41:39 p.m.
    Author     : Wilber's-Laptop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <title>Agregar Estudiantes</title>
    </head>
    <body>
        <div class="container mt-5">
            <h2>Formulario de Registro de Estudiantes</h2>
            <form action="/WGEvaluacionPractica2/estudiantes" method="POST">
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
                
                <input type="hidden" value="agregar" name="action" />

                <button type="submit" class="btn btn-primary">Enviar</button>
            </form>
        </div>
        
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
        <script>
            function addNewStudent(e){
                e.preventDefault();
                
                let nombre = e.target.getById();
            }
            
        </script> 
    </body>
</html>
