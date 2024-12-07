package com.example.tp_integrador_grupo7.datos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.tp_integrador_grupo7.AdminSQLiteOpenHelper;
import com.example.tp_integrador_grupo7.entidades.Citas;
import com.example.tp_integrador_grupo7.entidades.Propietarios;
import com.example.tp_integrador_grupo7.entidades.Reporte;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DataReportes {
    private static final String URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10748679";
    private static final String USER = "sql10748679";
    private static final String PASSWORD = "2WA7RsMaaK";

    private Context context;

    public DataReportes(Context context){this.context = context;}

    ExecutorService executor = Executors.newSingleThreadExecutor();

    public void insertarReporte(Reporte reporte){
        executor.execute(() ->{
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pst = con.prepareStatement("INSERT INTO reportes (diagnostico, hallazgos, fecha, cita_id) VALUES (?, ?, ? ,?)");
                pst.setString(1, reporte.getDiagnostico());
                pst.setString(2, reporte.getHallazgos());
                pst.setDate(3, reporte.getFecha());
                pst.setInt(4, reporte.getIdCita());

                pst.executeUpdate();
                pst.close();
                con.close();
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

    public ArrayList<Reporte> mostrarReportes(){
        ArrayList<Reporte> listaReportes = new ArrayList<Reporte>();
        executor.execute(() ->{
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("Select * FROM reportes");

                while(rs.next()){
                    Reporte rep = new Reporte();
                    rep.setId(rs.getInt("id"));
                    rep.setIdCita(rs.getInt("cita_id"));
                    rep.setDiagnostico(rs.getString("diagnostico"));
                    rep.setHallazgos(rs.getString("hallazgos"));
                    rep.setFecha(rs.getDate("fecha"));
                    listaReportes.add(rep);
                }
                rs.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return listaReportes;
    }
}
