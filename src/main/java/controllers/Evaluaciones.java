/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.Gson;
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
import models.Evaluacion;
import modelsDAO.EstudianteDAO;
import modelsDAO.EvaluacionDAO;

/**
 *
 * @author Wilber's-Laptop
 */
@WebServlet(name = "Evaluaciones", urlPatterns = {"/evaluaciones", "/evaluaciones/*"})
public class Evaluaciones extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            try {

                EvaluacionDAO evaluacionDAO = new EvaluacionDAO();
                RequestDispatcher dispatcher = null;

                
                
                if (request.getParameter("list") != null) {
                    List<Evaluacion> consulta = evaluacionDAO.getAll();

                    Gson gson = new Gson();
                    String evaluacionesJson = gson.toJson(consulta);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    response.getWriter().write(evaluacionesJson);
                } else if(request.getParameter("estudiante") != null) {
                    int studentId = Integer.parseInt(request.getParameter("estudiante"));
                    List<Evaluacion> consulta = evaluacionDAO.getEvaluationsByStudentId(studentId);

                    Gson gson = new Gson();
                    String studentEvaluationsJson = gson.toJson(consulta);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    response.getWriter().write(studentEvaluationsJson); 
                } else {
                    List<Evaluacion> consulta = evaluacionDAO.getAll();
                    request.setAttribute("evaluaciones", consulta);
                    dispatcher = request.getRequestDispatcher("Evaluaciones/evaluaciones.jsp");
                    
                    dispatcher.forward(request, response);
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Mensaje de error detallado: " + e.getMessage());
            }
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            EvaluacionDAO evaluacionDAO = new EvaluacionDAO();
            
            Evaluacion newEvaluation = gson.fromJson(request.getReader(), Evaluacion.class);
            /*Evaluacion evaluation = */evaluacionDAO.insert(newEvaluation);
            
            response.setStatus(HttpServletResponse.SC_CREATED); 
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
    }   
    
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            EvaluacionDAO evaluationDAO = new EvaluacionDAO();
            
            Evaluacion evaluation = evaluationDAO.getById(id);

            if (evaluation == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            evaluationDAO.delete(id);
            
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
//        if (pathInfo == null || pathInfo.equals("/")) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
        
        try {
            EvaluacionDAO evaluationDAO = new EvaluacionDAO();
            
            int id = Integer.parseInt(pathInfo.substring(1));
            Evaluacion evaluationExists = evaluationDAO.getById(id);

            if (evaluationExists == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Evaluacion updatedEvaluation = gson.fromJson(request.getReader(), Evaluacion.class);
            updatedEvaluation.setId(id);
            evaluationDAO.update(updatedEvaluation);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            System.out.println("Evalucion agregada correctamente");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


}
