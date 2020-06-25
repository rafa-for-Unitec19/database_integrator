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
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Faith
 */
public class OracleDriver {
    private JTextField instancia, baseDeDatos, puerto, usuario, contrasenia;
    private JTextArea bitacora;
    private String cadenaConexion;
    private boolean isPrueba;
    private Connection connection;

    public OracleDriver(JTextField instancia, JTextField baseDeDatos, JTextField puerto, JTextField usuario, JTextField contrasenia, JTextArea bitacora) {
        this.instancia = instancia;
        this.baseDeDatos = baseDeDatos;
        this.puerto = puerto;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.bitacora = bitacora;
    }
    
    private void crearCadenaConexion(){
        cadenaConexion = String.format("jdbc:oracle:thin:@//%s:%s/%s", instancia.getText(), puerto.getText(), baseDeDatos.getText());
    }
    
    public void crearConexion(){
        try {
            crearCadenaConexion();
            connection = DriverManager.getConnection(cadenaConexion, usuario.getText(), contrasenia.getText());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select msg from hr.welcomes");
            while(resultSet.next()){
            bitacora.append("\n" + resultSet.getString(1));
            bitacora.append("\n---------------------------------------------------------------------------");
            }
        }catch (SQLException ex) {
            
            if (ex.getCause() == null) {
                bitacora.append("\nERROR: " + ex.getMessage());
            }else{
                bitacora.append("\nERROR: " + ex.getMessage() + ex.getCause().toString());
            }
            bitacora.append("\n---------------------------------------------------------------------------");
        }finally{
            if (isPrueba) {
                cerrarConexion();
                bitacora.append("\nPrueba Terminada - Cerrando Conexion");
                bitacora.append("\n---------------------------------------------------------------------------");
            }else{
                bitacora.append("\nGuardando Bases de Datos de Destino");
                bitacora.append("\n---------------------------------------------------------------------------");
            }
        }
        
    }
    
    public void cerrarConexion(){
        try {
            if (!connection.isClosed()) {
                connection.close();
                if (!isPrueba) {
                    bitacora.append("\nCerrando Conexion");
                    bitacora.append("\n---------------------------------------------------------------------------");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            bitacora.append("ERROR: " + ex.getMessage() + ex.getCause().toString());
            bitacora.append("\n---------------------------------------------------------------------------");
        }
    }
    
    public boolean isIsPrueba() {
        return isPrueba;
    }
    
    public void setIsPrueba(boolean isPrueba) {
        this.isPrueba = isPrueba;
    }
}
