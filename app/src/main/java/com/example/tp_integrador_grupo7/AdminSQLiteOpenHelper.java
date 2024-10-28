package com.example.tp_integrador_grupo7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table veterinarios (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL, mail TEXT NOT NULL, telefono TEXT, contrasenia TEXT NOT NULL, " +
                "nombre_usuario TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

