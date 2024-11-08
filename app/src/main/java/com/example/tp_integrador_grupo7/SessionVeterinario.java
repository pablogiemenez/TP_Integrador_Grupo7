package com.example.tp_integrador_grupo7;

import android.app.Application;

public class SessionVeterinario extends Application {
    private Integer idSession;
    private String nombreUsuarioSession;

    public SessionVeterinario() {
    }

    public SessionVeterinario(Integer idSession, String nombreUsuarioSession) {
        this.idSession = idSession;
        this.nombreUsuarioSession = nombreUsuarioSession;
    }

    public Integer getIdSession() {
        return idSession;
    }

    public void setIdSession(Integer idSession) {
        this.idSession = idSession;
    }

    public String getNombreUsuarioSession() {
        return nombreUsuarioSession;
    }

    public void setNombreUsuarioSession(String nombreUsuarioSession) {
        this.nombreUsuarioSession = nombreUsuarioSession;
    }
}
