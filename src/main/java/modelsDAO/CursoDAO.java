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

/**
 *
 * @author Wilber's-Laptop
 */
public class CursoDAO {
   private DBConnection dBConnection;
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public CursoDAO() throws ClassNotFoundException  {
        dBConnection = new DBConnection();
    }
    
    private void cerrarRecursos() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
    
    public List<Curso> getAll(){
         List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos";

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getInt("id"),
                        rs.getString("curso")
                );
                
                lista.add(curso);
            }
            System.out.println("Tamaño de la lista en cursos: " + lista.size());
        } catch (Exception e) {
            System.err.println("Error al listar cursos: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }

        return lista;
    }
    
    public Curso getById(int id_curso){
         Curso curso = new Curso();
        String sql = "SELECT * FROM cursos WHERE id = ?";

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                curso = new Curso(
                        rs.getInt("id"),
                        rs.getString("curso")
                );
            }
        } catch (Exception e) {
            System.err.println("Error al listar cursos: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }

        return curso;
    }
    
    public Curso insert(Curso curso){
        String sql = "INSERT INTO cursos(curso) VALUES (?)";
        boolean agregado = false;

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, curso.getNombre());
            
            int filasAfectadas = ps.executeUpdate();
            
            agregado = filasAfectadas > 0;
            
            rs = ps.getGeneratedKeys();
            
            int lastGeneratedId = rs.getInt(1);
            
            curso.setId(lastGeneratedId);
            
            System.out.println(agregado ? "curso agregado exitosamente." : "No se pudo agregar el curso.");
        } catch (Exception e) {
            System.err.println("Error al agregar curso: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        
        return curso;
    }
    
    public Curso update(Curso curso){
        String sql = "UPDATE cursos SET curso = ? WHERE id = ?";
        boolean actualizado = false;

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
         
            ps.setString(1, curso.getNombre());
            ps.setInt(2, curso.getId());
            
            int filasAfectadas = ps.executeUpdate();
            actualizado = filasAfectadas > 0;
            
            System.out.println(actualizado ? "curso actualizado exitosamente." : "No se pudo actualizar el curso.");
        } catch (Exception e) {
            System.err.println("Error al actualizar curso: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        
        return curso;
    }
    
    public boolean delete(int id_curso){
        String sql = "DELETE FROM cursos WHERE id = ?";
        boolean eliminado = false;

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_curso);
            
            int filasAfectadas = ps.executeUpdate();
            eliminado = filasAfectadas > 0;
            System.out.println(eliminado ? "Curso eliminado exitosamente." : "No se pudo eliminar el curso.");
        } catch (Exception e) {
            System.err.println("Error al eliminar curso: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return eliminado;
    }
}
