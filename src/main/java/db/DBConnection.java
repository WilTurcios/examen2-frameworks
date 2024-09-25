/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Wilber's-Laptop
 */
public class DBConnection {
    Connection con;

    public DBConnection() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver cargado correctamente");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/evaluaciones?useSSL=false&serverTimezone=UTC&connectTimeout=10000&socketTimeout=10000", "root", "12345");
            System.out.println("Coneccion establecida correctamente");
        } catch (Exception e) {
            System.err.println("Error" + e);
        }
    }

    public Connection getConnection() {
        return con;
    }
}
