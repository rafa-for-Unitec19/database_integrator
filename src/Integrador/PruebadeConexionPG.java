/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Integrador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Faith
 */
public class PruebadeConexionPG {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://database-1.ck0fj32e7uo3.us-east-1.rds.amazonaws.com:5432/HR", "postPower", "waySecure1")) {
 
            System.out.println("Java JDBC PostgreSQL Example");
            // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within 
            // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
//          Class.forName("org.postgresql.Driver"); 
 
            System.out.println("Connected to PostgreSQL database!");
            Statement statement = connection.createStatement();
            System.out.println("Reading log records...");
            System.out.printf("%-30.30s  %-30.30s%n", "Tabla", "Operacion");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM audit.bitacora");
            while (resultSet.next()) {
                System.out.printf("%-30.30s  %-30.30s%n", resultSet.getString("tabla"), resultSet.getString("operacion"));
            }
 
        } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }
    
}
