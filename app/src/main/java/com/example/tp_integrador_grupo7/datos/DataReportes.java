package com.example.tp_integrador_grupo7.datos;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.tp_integrador_grupo7.entidades.Reporte;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataReportes {
    private static final String URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10750432";
    private static final String USER = "sql10750432";
    private static final String PASSWORD = "F16UCnM3KU";

    private Context context;

    public DataReportes(Context context) {
        this.context = context;
    }

    ExecutorService executor = Executors.newSingleThreadExecutor();

    // Método para insertar un reporte
    public void insertarReporte(Reporte reporte) {
        executor.execute(() -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                // Inserción en la base de datos
                PreparedStatement pst = con.prepareStatement(
                        "INSERT INTO reportes (diagnostico, hallazgos, cita_id) VALUES (?, ?, ?)"
                );
                pst.setString(1, reporte.getDiagnostico());
                pst.setString(2, reporte.getHallazgos());
                pst.setInt(3, reporte.getIdCita());

                pst.executeUpdate();
                pst.close();
                con.close();

                // Mostrar mensaje en el hilo principal
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(context, "Reporte guardado correctamente", Toast.LENGTH_SHORT).show()
                );

            } catch (Exception e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(context, "Error al guardar el reporte", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    // Método para mostrar reportes
    public ArrayList<Reporte> mostrarReportes() {
        ArrayList<Reporte> listaReportes = new ArrayList<>();
        Thread thread= new Thread(() -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement st = con.createStatement();

                // Consulta para obtener todos los reportes
                ResultSet rs = st.executeQuery("SELECT id_reporte, diagnostico, hallazgos, cita_id FROM reportes");

                while (rs.next()) {
                    Reporte reporte = new Reporte();
                    reporte.setId(rs.getInt("id_reporte"));
                    reporte.setDiagnostico(rs.getString("diagnostico"));
                    reporte.setHallazgos(rs.getString("hallazgos"));
                    reporte.setIdCita(rs.getInt("cita_id"));

                    listaReportes.add(reporte);
                }

                rs.close();
                st.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(context, "Error al cargar los reportes", Toast.LENGTH_SHORT).show()
                );
            }
        });
        thread.start();
        try{
            thread.join();

        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return listaReportes;
    }
}
