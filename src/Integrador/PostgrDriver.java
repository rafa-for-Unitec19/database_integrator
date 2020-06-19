/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Integrador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PostgrDriver {
    private JTextField instancia, baseDeDatos, puerto, usuario, contrasenia;
    private JTextArea bitacora;
    private String cadenaConexion;
    private boolean isPrueba;
    private Connection connection;
    private ArrayList<String> diferencias;
    private Date refDate;

    public PostgrDriver(JTextField instancia, JTextField baseDeDatos, JTextField puerto, JTextField usuario, JTextField contrasenia, JTextArea bitacora) {
        this.instancia = instancia;
        this.baseDeDatos = baseDeDatos;
        this.puerto = puerto;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.bitacora = bitacora;
    }
    
    private void crearCadenaConexion(){
        cadenaConexion = String.format("jdbc:postgresql://%s:%s/%s", instancia.getText(), puerto.getText(), baseDeDatos.getText());
    }
    
    public void crearConexion(){
        try {
            crearCadenaConexion();
            connection = DriverManager.getConnection(cadenaConexion, usuario.getText(), contrasenia.getText());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select datname, description from pg_shdescription join pg_database on objoid = pg_database.oid where datname = 'HR'");
            resultSet.next();
            bitacora.append("\nConexion Exitosa a:");
            bitacora.append("\n" + resultSet.getString(1) + " - " + resultSet.getString(2));
            bitacora.append("\n---------------------------------------------------------------------------");
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
                bitacora.append("\nGuardando Bases de Datos de Origen");
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
    
    public boolean obtenerDiferencias(String condiciones){
        String query = "SELECT * FROM audit.bitacora "+ obtenerCondicionTiempo() + condiciones;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            //Verificador de Sincornizacion
            if (resultSet.next()) {
                do {
                    diferencias.add(resultSet.getString("operacion"));
                } while (resultSet.next());
            } else {
                bitacora.append("INFORMACION: Las Bases de Datos estan actualizadas");
                bitacora.append("\n---------------------------------------------------------------------------");
            }
        } catch (SQLException ex) {
            bitacora.append("ERROR: " + ex.getMessage() + ex.getCause().toString());
            bitacora.append("\n---------------------------------------------------------------------------");
            return false;
        } finally {
            cerrarConexion();
        }
        refDate = new Date();
        return true;
    }
    
    private String obtenerCondicionTiempo(){
        if (cargarMarcaDeTiempo()) {
            return "WHERE fecha_hora >= '" + refDate + "' ";
        }else{
            return "WHERE ";
        }
        
    }
    
    public boolean cargarMarcaDeTiempo(){
        boolean estado = true;
        FileInputStream fis = null;
        DataInputStream entrada = null;
        try {
            fis = new FileInputStream("/tiempo.raffles");
            entrada = new DataInputStream(fis);
            while (true) {   
                refDate = new Date(entrada.readLong());
            }
        } catch (FileNotFoundException e) {
            estado = false;
        } catch (EOFException e) {
            System.out.println("Fin de fichero");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return estado;
    }
    
    public void guardarMarcaDeTiempo(){
        FileOutputStream fos = null;
        DataOutputStream salida = null;
        try {
            fos = new FileOutputStream("/tiempo.raffles");
            salida = new DataOutputStream(fos);
            salida.writeLong(refDate.getTime());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (salida != null) {
                    salida.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean isIsPrueba() {
        return isPrueba;
    }
    
    public void setIsPrueba(boolean isPrueba) {
        this.isPrueba = isPrueba;
    }
    
    public ArrayList<String> getDiferencias() {
        return diferencias;
    }

    
    
}
