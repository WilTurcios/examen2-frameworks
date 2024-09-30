/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelsDAO;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Curso;
import models.Estudiante;
import models.Evaluacion;

/**
 *
 * @author Wilber
 */
public class EvaluacionDAO {
    private DBConnection dBConnection;
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private EstudianteDAO estudianteDAO = new EstudianteDAO();
    private CursoDAO cursoDAO = new CursoDAO();

    public EvaluacionDAO() throws ClassNotFoundException {
        dBConnection = new DBConnection();
    }

    public List<Evaluacion> getAll() {
        List<Evaluacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM evaluaciones e INNER JOIN cursos c  ON e.idCurso = c.id inner join estudiantes es ON e.idEstudiante = es.id;";

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Evaluacion evaluacion = new Evaluacion(
                        rs.getInt("id"),
                        rs.getString("fecha"),
                        rs.getFloat("calificacion"),
                        new Estudiante(
                                rs.getInt("idEstudiante"),
                                rs.getString("nombre"),
                                rs.getInt("edad"),
                                rs.getString("telefono")
                        ),
                        new Curso(
                                rs.getInt("idCurso"),
                                rs.getString("curso")
                        )
                );
                lista.add(evaluacion);
            }
        } catch (Exception e) {
            System.err.println("Error al listar evaluaciones: " + e.getMessage());
        }
        return lista;
    }
    
    public List<Evaluacion> getEvaluationsByStudentId(int student_id) {
        List<Evaluacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM evaluaciones e INNER JOIN cursos c  ON e.idCurso = c.id inner join estudiantes es ON e.idEstudiante = es.id WHERE es.id = ?;";

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, student_id);
            
            rs = ps.executeQuery();

            while (rs.next()) {
                Evaluacion evaluacion = new Evaluacion(
                        rs.getInt("id"),
                        rs.getString("fecha"),
                        rs.getFloat("calificacion"),
                        new Estudiante(
                                rs.getInt("idEstudiante"),
                                rs.getString("nombre"),
                                rs.getInt("edad"),
                                rs.getString("telefono")
                        ),
                        new Curso(
                                rs.getInt("idCurso"),
                                rs.getString("curso")
                        )
                );
                lista.add(evaluacion);
            }
        } catch (Exception e) {
            System.err.println("Error al listar evaluaciones: " + e.getMessage());
        }
        return lista;
    }

    public Evaluacion getById(int id_evaluacion) {
        Evaluacion evaluacion = null;
        String sql = "SELECT * FROM evaluaciones e INNER JOIN cursos c  ON e.idCurso = c.id inner join estudiantes es ON e.idEstudiante = es.id WHERE e.id = ?;";

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_evaluacion); 
            rs = ps.executeQuery();

            if (rs.next()) {
                evaluacion = new Evaluacion(
                        rs.getInt("id"),
                        rs.getString("fecha"),
                        rs.getFloat("calificacion"),
                        new Estudiante(
                                rs.getInt("idEstudiante"),
                                rs.getString("nombre"),
                                rs.getInt("edad"),
                                rs.getString("telefono")
                        ),
                        new Curso(
                                rs.getInt("idCurso"),
                                rs.getString("curso")
                        )
                );
            }
        } catch (Exception e) {
            System.err.println("Error al obtener estudiante por ID: " + e.getMessage());
        } 

        return evaluacion;
    }

    public Evaluacion insert(Evaluacion evaluacion) {
        String sql = "INSERT INTO evaluaciones(fecha, calificacion, idEstudiante, idCurso) VALUES (?, ?, ?, ?)";

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, evaluacion.getFecha());
            ps.setDouble(2, evaluacion.getCalificacion());
            ps.setInt(3, evaluacion.getEstudiante().getId());
            ps.setInt(4, evaluacion.getCurso().getId());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {  // Verificar si existe la clave generada
                    evaluacion.setId(rs.getInt(1));
                }
                System.out.println("Estudiante agregado exitosamente.");
            }
        } catch (Exception e) {
            System.err.println("Error al agregar estudiante: " + e.getMessage());
        }
        
        return evaluacion;
    }

    public Evaluacion update(Evaluacion evaluacion) {
        String sql = "UPDATE evaluaciones SET calificacion = ?, idEstudiante = ?, idCurso = ?, fecha = ? WHERE id = ?";

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDouble(1, evaluacion.getCalificacion());
            ps.setInt(2, evaluacion.getEstudiante().getId());
            ps.setInt(3, evaluacion.getCurso().getId());
            ps.setString(4, evaluacion.getFecha());
            ps.setInt(5, evaluacion.getId());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Evaluacion actualizada exitosamente.");
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar evaluacion: " + e.getMessage());
        } 
        
        return evaluacion;
    }

    public boolean delete(int id_evaluacion) {
        String sql = "DELETE FROM evaluaciones WHERE id = ?";

        boolean eliminado = false;

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_evaluacion);

            int filasAfectadas = ps.executeUpdate();
            eliminado = filasAfectadas > 0;

            if (eliminado) {
                System.out.println("Evaluacion eliminada exitosamente.");
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar evaluacion: " + e.getMessage());
        } 

        return eliminado;
    }
}
