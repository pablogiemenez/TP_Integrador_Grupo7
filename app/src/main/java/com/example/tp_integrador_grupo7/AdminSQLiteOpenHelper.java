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

        if(verificarRegistros(sqLiteDatabase, "veterinarios")==0){
            sqLiteDatabase.execSQL("INSERT INTO veterinarios (nombre, mail, telefono, contrasenia, nombre_usuario) VALUES "
                    + "('Martín Gómez', 'martin.gomez@veterinaria.com', '1123456789', 'password1', 'martingomez'), "
                    + "('Claudia Pérez', 'claudia.perez@veterinaria.com', '1198765432', '1234', 'claudiaperez'), "
                    + "('Ricardo Sánchez', 'ricardo.sanchez@veterinaria.com', '1154321678', 'qwerty', 'ricardosanchez'), "
                    + "('Ana Rodríguez', 'ana.rodriguez@veterinaria.com', '1167894531', 'contraseña123', 'anarodriguez'), "
                    + "('Pablo López', 'pablo.lopez@veterinaria.com', '1135792468', 'letmein', 'pablolopez'), "
                    + "('Isabel Fernández', 'isabel.fernandez@veterinaria.com', '1124681357', 'password1234', 'isabelfernandez'), "
                    + "('Sergio Martínez', 'sergio.martinez@veterinaria.com', '1136925814', '12345', 'sergiomartinez'), "
                    + "('Mariana Díaz', 'mariana.diaz@veterinaria.com', '1145678912', 'admin123', 'marianadiaz'), "
                    + "('Javier Torres', 'javier.torres@veterinaria.com', '1156789043', 'admin1234', 'javiertorres'), "
                    + "('Carolina García', 'carolina.garcia@veterinaria.com', '1167890123', 'pass123', 'carolinagarcia'), "
                    + "('Manuel Ruiz', 'manuel.ruiz@veterinaria.com', '1178901234', 'root123', 'manuelruiz'), "
                    + "('Marta Jiménez', 'marta.jimenez@veterinaria.com', '1189012345', 'qazwsx', 'martajimenez')");
        }

        sqLiteDatabase.execSQL("CREATE TABLE propietarios (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, mail TEXT NOT NULL, telefono TEXT NOT NULL, dni TEXT NOT NULL)");

        if(verificarRegistros(sqLiteDatabase,"propietarios")==0){
            sqLiteDatabase.execSQL("INSERT INTO propietarios (nombre, mail, telefono, DNI) VALUES "
                    + "('Juan Pérez', 'juan.perez@mail.com', '1123456789', '20345678'), "
                    + "('Ana Gómez', 'ana.gomez@mail.com', '1198765432', '23456789'), "
                    + "('Carlos López', 'carlos.lopez@mail.com', '1154321678', '34567890'), "
                    + "('Laura Martínez', 'laura.martinez@mail.com', '1167894531', '43678901'), "
                    + "('Pedro Sánchez', 'pedro.sanchez@mail.com', '1135792468', '32789012'), "
                    + "('María Fernández', 'maria.fernandez@mail.com', '1124681357', '18890123'), "
                    + "('Javier García', 'javier.garcia@mail.com', '1136925814', '33901234'), "
                    + "('Lucía Rodríguez', 'lucia.rodriguez@mail.com', '1145678912', '21012345'), "
                    + "('Sergio Díaz', 'sergio.diaz@mail.com', '1156789043', '40123456'), "
                    + "('Isabel Hernández', 'isabel.hernandez@mail.com', '1167890123', '44234567'), "
                    + "('Ricardo Pérez', 'ricardo.perez@mail.com', '1178901234', '43345678'), "
                    + "('Raquel García', 'raquel.garcia@mail.com', '1189012345', '26456789')");
        }
        sqLiteDatabase.execSQL("CREATE TABLE mascotas (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, tipo TEXT NOT NULL, raza TEXT NOT NULL, fecha_nac TEXT NOT NULL, idPropietario INTEGER NOT NULL" +
                ", FOREIGN KEY (idPropietario) REFERENCES propietarios(id) ON DELETE CASCADE)");

        if(verificarRegistros(sqLiteDatabase,"mascotas")==0){
            sqLiteDatabase.execSQL("INSERT INTO mascotas (nombre, tipo, raza, fecha_nac, idPropietario) VALUES "
                    + "('Fido', 'Perro', 'Labrador', '2021-03-14', 1), "
                    + "('Miau', 'Gato', 'Siames', '2020-08-22', 2), "
                    + "('Rocky', 'Perro', 'Pitbull', '2022-02-05', 3), "
                    + "('Luna', 'Gato', 'Persa', '2021-07-10', 4), "
                    + "('Max', 'Perro', 'Beagle', '2023-01-15', 5), "
                    + "('Bella', 'Perro', 'Bulldog', '2020-10-30', 6), "
                    + "('Simba', 'Gato', 'Maine Coon', '2021-05-19', 7), "
                    + "('Chester', 'Perro', 'Golden Retriever', '2022-04-25', 8), "
                    + "('Coco', 'Perro', 'Chihuahua', '2021-11-12', 9), "
                    + "('Nina', 'Gato', 'Ragdoll', '2020-12-01', 10), "
                    + "('Zeus', 'Perro', 'Husky', '2022-09-17', 11), "
                    + "('Daisy', 'Conejo', 'Angora', '2023-06-30', 12)");
        }
        sqLiteDatabase.execSQL("CREATE TABLE citas (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " fecha DATE NOT NULL, motivo TEXT NOT NULL, id_mascota INTEGER NOT NULL," +
                "id_veterinario INTEGER NOT NULL, FOREIGN KEY (id_veterinario)REFERENCES veterinarios(id) ON DELETE CASCADE)");

        if(verificarRegistros(sqLiteDatabase, "citas")==0) {
            sqLiteDatabase.execSQL("INSERT INTO citas (fecha, motivo, id_mascota, id_veterinario) VALUES" +
                    "('2025-01-10', 'Revisión general', 1, 5)," +
                    "('2025-01-16', 'Vacunación', 2, 8)," +
                    "('2025-03-15', 'Chequeo anual', 3, 1)," +
                    "('2025-04-22', 'Tratamiento para alergias', 4, 1)," +
                    "('2025-05-03', 'Dolor de estomago', 5, 6)," +
                    "('2025-06-11', 'Pulgas', 6, 10)," +
                    "('2025-07-02', 'Control postoperatorio', 7, 2)," +
                    "('2025-01-18', 'Consulta por dolor en las patas', 8, 11)," +
                    "('2025-02-20', 'Revisión de piel', 9, 3)," +
                    "('2025-03-25', 'Vacunas de refuerzo', 10, 1)," +
                    "('2025-04-30', 'Control de peso', 11, 4)," +
                    "('2025-05-20', 'Chequeo de salud', 12, 12)");
        }
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

    private int verificarRegistros(SQLiteDatabase sqLiteDatabase, String nombreTabla){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM " + nombreTabla, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
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
        Cursor fila= baseDeDatos.rawQuery("SELECT nombre, mail, dni, telefono,id FROM propietarios", null);
        ArrayList<Propietarios> listaPropietarios= new ArrayList<>();
        if(fila.moveToFirst()){
            do{
                String nombre=fila.getString(0);
                String mail=fila.getString(1);
                String dni=fila.getString(2);
                String tel=fila.getString(3);
                Integer id=fila.getInt(4);
                Propietarios prop= new Propietarios(nombre,tel,mail,dni);
                prop.setId(id);
                listaPropietarios.add(prop);

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
