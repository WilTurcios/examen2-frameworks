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

/**
 *
 * @author Wilber's-Laptop
 */
@WebServlet(name = "Estudiantes", urlPatterns = {"/estudiantes", "/estudiantes/*"})
public class Estudiantes extends HttpServlet {
    private Gson gson = new Gson();
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Estudiantes</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Estudiantes at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            EstudianteDAO estudianteDAO = new EstudianteDAO();
            RequestDispatcher dispatcher = null;

            if (request.getParameter("list") != null) {
                List<Estudiante> consulta = estudianteDAO.getAll();

                Gson gson = new Gson();
                String estudiantesJson = gson.toJson(consulta);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(estudiantesJson);
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
//        if (pathInfo == null || pathInfo.equals("/")) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Código 400 - Bad Request
//            return;
//        }

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
//        if (pathInfo == null || pathInfo.equals("/")) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
        
        try {
            EstudianteDAO estudianteDAO = new EstudianteDAO();

            int id = Integer.parseInt(pathInfo.substring(1));
            Estudiante studentExists = estudianteDAO.getById(id);

            if (studentExists == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Estudiante updatedStudent = gson.fromJson(request.getReader(), Estudiante.class);
            updatedStudent.setId(id);
            estudianteDAO.update(updatedStudent);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
