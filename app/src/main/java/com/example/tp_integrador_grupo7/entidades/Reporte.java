package com.example.tp_integrador_grupo7.entidades;

import com.example.tp_integrador_grupo7.AdminSQLiteOpenHelper;

import java.sql.Date;

public class Reporte {
    private int id;
    private String diagnostico;
    private String hallazgos;
    private int idCita;

    public Reporte() {}

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getHallazgos() {
        return hallazgos;
    }

    public void setHallazgos(String hallazgos) {
        this.hallazgos = hallazgos;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    @Override
    public String toString() {

        return "Diagnóstico: " + diagnostico +
                "\nHallazgos: " + hallazgos +
                "\nCita ID: " + idCita;
    }

}

