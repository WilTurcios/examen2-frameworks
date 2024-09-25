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
import models.Estudiante;

/**
 *
 * @author Wilber
 */
public class EstudianteDAO {
    private DBConnection dBConnection;
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public EstudianteDAO() throws ClassNotFoundException  {
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
    
    public List<Estudiante> getAll(){
         List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Estudiante cliente = new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("telefono"));
                lista.add(cliente);
            }
            System.out.println("Tamaño de la lista en Estudiantes: " + lista.size());
        } catch (Exception e) {
            System.err.println("Error al listar Estudiantes: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }

        return lista;
    }
    
    public Estudiante getById(int id_estudiante){
         Estudiante estudiante = new Estudiante();
        String sql = "SELECT * FROM estudiantes WHERE id = ?";

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                estudiante = new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("telefono"));
            }
        } catch (Exception e) {
            System.err.println("Error al listar Estudiantes: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }

        return estudiante;
    }
    
    public Estudiante insert(Estudiante estudiante){
        String sql = "INSERT INTO estudiantes(nombre, edad, telefono) VALUES (?, ?, ?)";
        boolean agregado = false;

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, estudiante.getNombre());
            ps.setInt(2, estudiante.getEdad());
            ps.setString(3, estudiante.getTelefono());
            
            int filasAfectadas = ps.executeUpdate();
            
            agregado = filasAfectadas > 0;
            
            rs = ps.getGeneratedKeys();
            
            int lastGeneratedId = rs.getInt(1);
            
            estudiante.setId(lastGeneratedId);
            
            System.out.println(agregado ? "Estudiante agregado exitosamente." : "No se pudo agregar el estudiante.");
        } catch (Exception e) {
            System.err.println("Error al agregar estudiante: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        
        return estudiante;
    }
    
    public Estudiante update(Estudiante estudiante){
        String sql = "UPDATE estudiantes SET nombre = ?, edad = ?, telefono = ? WHERE id = ?";
        boolean actualizado = false;

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
         
            ps.setString(1, estudiante.getNombre());
            ps.setInt(2, estudiante.getEdad());
            ps.setString(3, estudiante.getTelefono());
            ps.setInt(4, estudiante.getId());
            
            int filasAfectadas = ps.executeUpdate();
            actualizado = filasAfectadas > 0;
            
            System.out.println(actualizado ? "Estudiante actualizado exitosamente." : "No se pudo actualizar el cliente.");
        } catch (Exception e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        
        return estudiante;
    }
    
    public boolean delete(int id_estudiante){
        String sql = "DELETE FROM estudiantes WHERE id = ?";
        boolean eliminado = false;

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_estudiante);
            
            int filasAfectadas = ps.executeUpdate();
            eliminado = filasAfectadas > 0;
            System.out.println(eliminado ? "Cliente eliminado exitosamente." : "No se pudo eliminar el cliente.");
        } catch (Exception e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return eliminado;
    }
}
