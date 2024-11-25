package com.example.tp_integrador_grupo7.entidades;

public class Propietarios {
    private int id;
    private String nombre;
    private String telefono;
    private String mail;
    private String dni;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String toString(){
        return nombre;
    }

    public Propietarios() {
    }

    public Propietarios(String nombre, String telefono, String mail, String dni) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.mail = mail;
        this.dni = dni;
    }
}
