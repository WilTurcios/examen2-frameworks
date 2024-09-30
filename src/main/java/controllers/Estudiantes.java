/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.Estudiante;
import modelsDAO.EstudianteDAO;
import com.google.gson.Gson;
import models.Evaluacion;
import modelsDAO.EvaluacionDAO;

/**
 *
 * @author Wilber's-Laptop
 */
@WebServlet(name = "Estudiantes", urlPatterns = {"/estudiantes", "/estudiantes/*"})
public class Estudiantes extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        Gson gson = new Gson();
            
        try {
            EstudianteDAO estudianteDAO = new EstudianteDAO();
            RequestDispatcher dispatcher = null;

            if (request.getParameter("list") != null) {
                List<Estudiante> consulta = estudianteDAO.getAll();

                String estudiantesJson = gson.toJson(consulta);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(estudiantesJson);
            } else if(request.getParameter("evaluaciones") != null) {
                int studentId = Integer.parseInt(request.getParameter("evaluaciones"));
                
                
                EvaluacionDAO evaluacionDAO = new EvaluacionDAO();
                
                List<Evaluacion> studentEvaluations = evaluacionDAO.getEvaluationsByStudentId(studentId);
                
                request.setAttribute("studentEvaluations", studentEvaluations);
                
                dispatcher = request.getRequestDispatcher("Estudiantes/evaluaciones_estudiante.jsp");
                
                dispatcher.forward(request, response);
                
                
            } else {
                List<Estudiante> consulta = estudianteDAO.getAll();
                request.setAttribute("estudiantes", consulta);
                dispatcher = request.getRequestDispatcher("Estudiantes/estudiantes.jsp");

                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Mensaje de error detallado: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            EstudianteDAO estudianteDAO = new EstudianteDAO();
            
            Estudiante newStudent = gson.fromJson(request.getReader(), Estudiante.class);
            Estudiante student = estudianteDAO.insert(newStudent);
            
            response.setStatus(HttpServletResponse.SC_CREATED); 
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
    }   
    
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Código 400 - Bad Request
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            EstudianteDAO studentDAO = new EstudianteDAO();
            Estudiante student = studentDAO.getById(id);

            if (student == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            studentDAO.delete(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        try {
            EstudianteDAO estudianteDAO = new EstudianteDAO();
            
            System.out.println("El id mandado por url es: " + pathInfo);
            int id = Integer.parseInt(pathInfo.substring(1));
            Estudiante studentExists = estudianteDAO.getById(id);
            System.out.println("id estudiante: " + id);

            if (studentExists == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Estudiante updatedStudent = gson.fromJson(request.getReader(), Estudiante.class);
            
            System.out.println("Nombre del estudiante actualizado: " + updatedStudent.getNombre());
            updatedStudent.setId(id);
            estudianteDAO.update(updatedStudent);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Error al actualizar estudiante: " + e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
