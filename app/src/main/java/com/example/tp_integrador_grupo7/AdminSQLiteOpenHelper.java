package com.example.tp_integrador_grupo7;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import com.example.tp_integrador_grupo7.entidades.Citas;
import com.example.tp_integrador_grupo7.entidades.Mascotas;
import com.example.tp_integrador_grupo7.entidades.Propietarios;

import java.sql.Date;
import java.util.ArrayList;

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
       /* sqLiteDatabase.execSQL("CREATE TABLE tratamientos (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "medicamento TEXT NOT NULL, dosis TEXT NOT NULL, duracion TEXT NOT NULL, numero_cita TEXT NOT NULL)");*/

        sqLiteDatabase.execSQL("CREATE TABLE mascotas (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, tipo TEXT NOT NULL, raza TEXT NOT NULL, fecha_nac TEXT NOT NULL, foto BLOB, idPropietario INTEGER NOT NULL" +
                ", FOREIGN KEY (idPropietario) REFERENCES propietarios(id) ON DELETE CASCADE)");

        sqLiteDatabase.execSQL("CREATE TABLE citas (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " fecha DATE NOT NULL, motivo TEXT NOT NULL, id_mascota INTEGER NOT NULL," +
                "id_veterinario INTEGER NOT NULL, FOREIGN KEY (id_veterinario)REFERENCES veterinarios(id) ON DELETE CASCADE)");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Elimina las tablas si ya existen y crea de nuevo (solo en caso de actualización de versión)
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS veterinarios");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS propietarios");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tratamientos");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS mascotas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS citas");
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
    public ArrayList<String> obtenerListaCitasXIdVeterinario(Integer id){
        SQLiteDatabase BaseDeDatos=this.getReadableDatabase();

        ArrayList<String> listaCitas= new ArrayList<String>();
        Cursor cursor = BaseDeDatos.rawQuery(
                "SELECT c.motivo, c.fecha, m.nombre, m.tipo, m.raza, m.fecha_nac, p.nombre, p.telefono, p.dni, p.mail " +
                        "FROM citas c " +
                        "JOIN mascotas m ON c.id_mascota = m.id " +
                        "JOIN propietarios p ON m.idPropietario = p.id " +
                        "WHERE c.id_veterinario = ?",
                new String[]{String.valueOf(id)}
        );

        if(cursor.moveToFirst()){
            do{

                String motivo= cursor.getString(0);
                String fechaCita=cursor.getString(1);
                String nombreMascota=cursor.getString(2);
                String tipoMascota=cursor.getString(3);
                String razaMascota=cursor.getString(4);
                String fechaNacimiento=cursor.getString(5);
                String nombreDuenio=cursor.getString(6);
                String telefonoDuenio=cursor.getString(7);
                String dniDuenio=cursor.getString(8);
                String mailDuenio=cursor.getString(9);
                Citas cita=new Citas(motivo,Date.valueOf(fechaCita));
                Mascotas mascota= new Mascotas(nombreMascota, tipoMascota,razaMascota,Date.valueOf(fechaNacimiento));
                Propietarios prop= new Propietarios(nombreDuenio,telefonoDuenio,mailDuenio,dniDuenio);

                String index=toString(cita,mascota,prop);

                listaCitas.add(index);

            }while(cursor.moveToNext());
        }
        cursor.close();
        BaseDeDatos.close();

        return listaCitas;
    }

    public ArrayList<String> obtenerListaMascotas() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> listaMascotas = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT nombre, tipo, raza, fecha_nac FROM mascotas", null);
        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(0);
                String tipo = cursor.getString(1);
                String raza = cursor.getString(2);
                String fechaNacimiento = cursor.getString(3);

                // Formatea la información de la mascota
                String infoMascota = "Nombre: " + nombre + ", Tipo: " + tipo + ", Raza: " + raza + ", Nacido: " + fechaNacimiento;
                listaMascotas.add(infoMascota);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaMascotas;
    }
    public ArrayList<Propietarios> obtenerListaPropietarios(){
        SQLiteDatabase baseDeDatos= this.getReadableDatabase();
        Cursor fila= baseDeDatos.rawQuery("SELECT nombre, mail, dni, telefono FROM propietarios", null);
        ArrayList<Propietarios> listaPropietarios= new ArrayList<>();
        if(fila.moveToFirst()){
            do{
                String nombre=fila.getString(0);
                String mail=fila.getString(1);
                String dni=fila.getString(2);
                String tel=fila.getString(3);
                listaPropietarios.add(new Propietarios(nombre,tel,mail,dni));

            }while(fila.moveToNext());
        }
        return listaPropietarios;
    }

    //public Mascotas(String nombre, String tipo, String raza, java.util.Date fecha_nac)

    //public Propietarios(String nombre, String telefono, String mail, String dni)

    public String toString(Citas cita, Mascotas mascota, Propietarios prop){

        return "Dueño( nombre: "+prop.getNombre()+", DNI: "+prop.getDni()+", mail: "+prop.getMail()+", telefono: "+prop.getTelefono()+")"
                +" motivo: "+cita.getMotivo()+ ", fecha: "+cita.getFecha()+", mascota( nombre: "+mascota.getNombre()+ " raza: "+mascota.getRaza()+" tipo: "+
                mascota.getTipo()+" nacimiento: "+mascota.getFecha_nac()+")";
    }
}
