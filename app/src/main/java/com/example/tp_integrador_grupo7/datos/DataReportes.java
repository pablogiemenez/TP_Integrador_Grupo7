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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
                PreparedStatement pst = con.prepareStatement("INSERT INTO reportes (diagnostico, hallazgos, fecha, tratamiento_id) VALUES (?, ?, ? ,?)");
                pst.setString(1, reporte.getDiagnostico());
                pst.setString(2, reporte.getHallazgos());
                pst.setString(3, reporte.getFecha());
                pst.setInt(4, reporte.getIdCita()); //CAMBIAR A TRATAMIENTOS SI ES NECESARIO, PQ LO ESTOY CONFUNDIENDO CON EL DER

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

    public ArrayList<Citas> mostrarCitas(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this.context, "consultorioVeterinario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ArrayList<Citas> listaCitas = new ArrayList<Citas>();

        try {
            Cursor filas = bd.rawQuery("SELECT  id FROM citas", null);
            if(filas.moveToFirst()){
                do{
                    Citas citas = new Citas();

                    citas.setId(filas.getInt(0));
                    listaCitas.add(citas);
                } while (filas.moveToNext());
            } else {
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return listaCitas;
    }
}
