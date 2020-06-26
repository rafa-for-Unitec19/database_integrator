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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Faith
 */
public class PruebadeConexionPG {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
//        String refDate = "2019-06-18 19:28:22.767";
//        
//        PostgreSQL Driver
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://database-1.ck0fj32e7uo3.us-east-1.rds.amazonaws.com:5432/HR", "postPower", "waySecure1")) {
 
            System.out.println("Java JDBC PostgreSQL Example");
            // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within 
            // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
//          Class.forName("org.postgresql.Driver"); 
 
            System.out.println("Connected to PostgreSQL database!");
            Statement statement = connection.createStatement();
            System.out.println("Reading log records...");
            System.out.printf("%-30.30s  %-30.30s%n", "Tabla", "Operacion");
            String query = "SELECT * FROM audit.bitacora WHERE fecha_hora >= '2020-06-25 20:23:42.78'";
            ResultSet resultSet = statement.executeQuery(query);
            
            
            //Verificador de Sincornizacion
            if (resultSet.next()) {
                do {
                System.out.printf("\n%-30.30s  %-200s", resultSet.getString("tabla"), resultSet.getString("operacion"));
                }while (resultSet.next());
            }else{
                System.out.println("Las Tablas estan sincronizadas");
            }
            
            
            
            
 
        } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        
//      Oracle Driver  
//        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//tbb2.c71kpjw2tb2c.us-east-1.rds.amazonaws.com:1521/ORCL", "HR", "hr123")) {
// 
//            System.out.println("Java JDBC Oracle Example");
//            // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within 
//            // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
////          Class.forName("org.postgresql.Driver"); 
// 
//            System.out.println("Connected to Oracle database!");
//            Statement statement = connection.createStatement();
//            System.out.println("Reading log records...");
//            System.out.printf("%-30.30s  %-30.30s%n", "Codigo", "Nombre");
//          String query = "INSERT INTO hr.welcomes VALUES ('Bienvenido a la Bases de Datos de Destino - Oracle')";
//            
//            statement.executeUpdate(query);
////
////            String QUERY = "Select * from welcomes";
////            ResultSet resultSet = statement.executeQuery(QUERY);
//            
//            
//            //Verificador de Sincornizacion
////            if (resultSet.next()) {
////                do {
////                System.out.printf("%-30.30s", resultSet.getString(1));
////                }while (resultSet.next());
////            }else{
////                System.out.println("Las Tablas estan sincronizadas");
////            }
//            
//            
//            
//            
// 
////        }catch (ClassNotFoundException e) {
////            System.out.println("PostgreSQL JDBC driver not found.");
////            e.printStackTrace();
//        } catch (SQLException e) {
//            System.out.println("Connection failure.");
//            e.printStackTrace();
//        }
        
//        try {
//            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//            Date parsedDate = new Date();
//            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
//            System.out.println(timestamp.toString());
//        } catch (Exception e) { //this generic but you can control another types of exception
//            // look the origin of excption 
//        }
//        
    }
    
    
    
}
