package com.example.tp_integrador_grupo7.entidades;

import java.sql.Date;

public class Tratamiento {
    private int id;
    private String Medicamento;
    private String dosis;
    private String duracion;
    private int citaId;
    private Date fecha_reg;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicamento() {
        return Medicamento;
    }

    public void setMedicamento(String nombreMedicamento) {
        this.Medicamento = nombreMedicamento;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public int getcitaId() {
        return citaId;
    }

    public void setCitaId(int citaId) {this.citaId = citaId;
    }

    public Date getFecha_reg() {return fecha_reg;
    }

    public void setFecha_reg(Date fecha_reg) {this.fecha_reg = fecha_reg;
    }

}
