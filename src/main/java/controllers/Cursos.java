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
import models.Curso;
import models.Estudiante;
import modelsDAO.CursoDAO;
import modelsDAO.EstudianteDAO;

/**
 *
 * @author Wilber's-Laptop
 */
@WebServlet(name = "Cursos", urlPatterns = {"/cursos", "/cursos/*"})
public class Cursos extends HttpServlet {
    Gson gson = new Gson();
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
            out.println("<title>Servlet Cursos</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Cursos at " + request.getContextPath() + "</h1>");
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
            CursoDAO courseDAO = new CursoDAO();
            RequestDispatcher dispatcher = null;

            if (request.getParameter("list") != null) {
                List<Curso> consulta = courseDAO.getAll();

                Gson gson = new Gson();
                String estudiantesJson = gson.toJson(consulta);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(estudiantesJson);
            } else {
                List<Curso> consulta = courseDAO.getAll();
                request.setAttribute("cursos", consulta);
                dispatcher = request.getRequestDispatcher("Cursos/cursos.jsp");

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
            CursoDAO cursoDAO = new CursoDAO();
            
            Curso newCourse = gson.fromJson(request.getReader(), Curso.class);
            Curso course = cursoDAO.insert(newCourse);
            
            response.setStatus(HttpServletResponse.SC_CREATED); 
        } catch(Exception e){
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
