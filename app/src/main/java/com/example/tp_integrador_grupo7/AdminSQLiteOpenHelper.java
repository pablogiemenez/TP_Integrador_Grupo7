package com.example.tp_integrador_grupo7;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE veterinarios (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, mail TEXT NOT NULL, telefono TEXT, contrasenia TEXT NOT NULL, " +
                "nombre_usuario TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE propietarios (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, mail TEXT NOT NULL, telefono TEXT NOT NULL, dni TEXT NOT NULL)");

        // Crear la tabla de tratamientos
        sqLiteDatabase.execSQL("CREATE TABLE tratamientos (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "medicamento TEXT NOT NULL, dosis TEXT NOT NULL, duracion TEXT NOT NULL, numero_cita TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Elimina las tablas si ya existen y crea de nuevo (solo en caso de actualización de versión)
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS veterinarios");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS propietarios");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tratamientos");
        onCreate(sqLiteDatabase);
    }

    // Método para insertar un tratamiento en la tabla tratamientos
    public boolean insertarTratamiento(String medicamento, String dosis, String duracion, String numeroCita) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("medicamento", medicamento);
        valores.put("dosis", dosis);
        valores.put("duracion", duracion);
        valores.put("numero_cita", numeroCita);

        long resultado = db.insert("tratamientos", null, valores);
        db.close();
        return resultado != -1; // Devuelve true si la inserción fue exitosa
    }
}
