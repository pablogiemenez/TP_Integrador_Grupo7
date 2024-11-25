package com.example.tp_integrador_grupo7.entidades;

import java.util.Date;

public class Citas {
    Integer id,id_mascota,id_veterinario;
    String motivo;
    Date fecha;

    public Citas(){

    }
    public Citas(Integer id, Integer id_mascota, Integer id_veterinario, String motivo, Date fecha) {
        this.id = id;
        this.id_mascota = id_mascota;
        this.id_veterinario = id_veterinario;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    public Citas(String motivo, Date fecha) {
        this.motivo = motivo;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_mascota() {
        return id_mascota;
    }

    public void setId_mascota(Integer id_mascota) {
        this.id_mascota = id_mascota;
    }

    public Integer getId_veterinario() {
        return id_veterinario;
    }

    public void setId_veterinario(Integer id_veterinario) {
        this.id_veterinario = id_veterinario;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return  "motivo='" + motivo + '\'' +
                ", fecha=" + fecha;
    }
}
