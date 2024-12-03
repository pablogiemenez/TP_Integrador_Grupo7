package com.example.tp_integrador_grupo7.datos;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.example.tp_integrador_grupo7.entidades.Tratamiento;

public class DataTratamientos {
    private static final String URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10748679";
    private static final String USER = "sql10748679";
    private static final String PASSWORD = "2WA7RsMaaK";

    private Context context;

    // Constructor
    public DataTratamientos(Context context) {
        this.context = context;
    }

    // Método para insertar un tratamiento
    public void insertarTratamiento(Tratamiento tratamiento) {
        new Thread(() -> {
            try {
                // Cargar el driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establecer conexión
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                // Consulta SQL para insertar un tratamiento
                String query = "INSERT INTO tratamientos (nombre_medicamento, dosis, duracion, observaciones, mascota_id) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, tratamiento.getNombreMedicamento());
                pst.setString(2, tratamiento.getDosis());
                pst.setString(3, tratamiento.getDuracion());
                pst.setString(4, tratamiento.getObservaciones());
                pst.setInt(5, tratamiento.getMascotaId());

                // Ejecutar la consulta
                pst.executeUpdate();

                // Cerrar conexión
                pst.close();
                con.close();

                // Mostrar mensaje de éxito en el hilo principal
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(context, "Tratamiento guardado correctamente", Toast.LENGTH_SHORT).show()
                );
            } catch (Exception e) {
                e.printStackTrace();
                // Mostrar mensaje de error en el hilo principal
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(context, "Error al guardar el tratamiento", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
}
