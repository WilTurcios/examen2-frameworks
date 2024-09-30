package modelsDAO;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Estudiante;

public class EstudianteDAO {

    private DBConnection dBConnection;

    public EstudianteDAO() throws ClassNotFoundException {
        dBConnection = new DBConnection();
    }

    public List<Estudiante> getAll() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Estudiante estudiante = new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("telefono"));
                lista.add(estudiante);
            }
        } catch (Exception e) {
            System.err.println("Error al listar Estudiantes: " + e.getMessage());
        }

        return lista;
    }

    public Estudiante getById(int id_estudiante) {
        Estudiante estudiante = null;
        String sql = "SELECT * FROM estudiantes WHERE id = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_estudiante);
            rs = ps.executeQuery();

            if (rs.next()) {
                estudiante = new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("telefono"));
            }
        } catch (Exception e) {
            System.err.println("Error al obtener estudiante por ID: " + e.getMessage());
        } 
        
        return estudiante;
    }

    public Estudiante insert(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes(nombre, edad, telefono) VALUES (?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, estudiante.getNombre());
            ps.setInt(2, estudiante.getEdad());
            ps.setString(3, estudiante.getTelefono());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    estudiante.setId(rs.getInt(1));
                }
                System.out.println("Estudiante agregado exitosamente.");
            } else {
                System.out.println("No se pudo agregar el estudiante.");
            }
        } catch (Exception e) {
            System.err.println("Error al agregar estudiante: " + e.getMessage());
        }

        return estudiante;
    }

    public Estudiante update(Estudiante estudiante) {
        String sql = "UPDATE estudiantes SET nombre = ?, edad = ?, telefono = ? WHERE id = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setInt(2, estudiante.getEdad());
            ps.setString(3, estudiante.getTelefono());
            ps.setInt(4, estudiante.getId());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Estudiante actualizado exitosamente.");
            } else {
                System.out.println("No se pudo actualizar el estudiante.");
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar estudiante: " + e.getMessage());
        } 

        return estudiante;
    }

    public boolean delete(int id_estudiante) {
        String sql = "DELETE FROM estudiantes WHERE id = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = dBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_estudiante);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Estudiante eliminado exitosamente.");
                return true;
            } else {
                System.out.println("No se pudo eliminar el estudiante.");
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
        } 
        return false;
    }
}
