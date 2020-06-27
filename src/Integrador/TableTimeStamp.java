/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Integrador;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Faith
 */
public class TableTimeStamp implements Serializable{
    String nombreTabla;
    Date marcaTiempo;

    public TableTimeStamp(String nombreTabla, Date marcaTiempo) {
        this.nombreTabla = nombreTabla;
        this.marcaTiempo = marcaTiempo;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public Date getMarcaTiempo() {
        return marcaTiempo;
    }

    public void setMarcaTiempo(Date marcaTiempo) {
        this.marcaTiempo = marcaTiempo;
    }
    
}
