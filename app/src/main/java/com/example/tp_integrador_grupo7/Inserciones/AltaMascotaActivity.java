package com.example.tp_integrador_grupo7.Inserciones;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_integrador_grupo7.AdminSQLiteOpenHelper;
import com.example.tp_integrador_grupo7.R;
import com.example.tp_integrador_grupo7.entidades.Propietarios;

import java.util.ArrayList;

public class AltaMascotaActivity extends AppCompatActivity {
    private EditText etNombre, etNacimiento, etTipo, etRaza;
    private Spinner spinnerDuenio;
    private Button btnGuardar;
    private TextView txtMensaje;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_alta_mascota);
       innitVar();

       ArrayList<Propietarios> listaPropietarios = mostrarPropietarios();
       ArrayAdapter<Propietarios> arrayAdapter = new ArrayAdapter<Propietarios>(getApplicationContext(),
               androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,listaPropietarios);

       spinnerDuenio.setAdapter(arrayAdapter);


       btnGuardar.setOnClickListener(view -> {
           guardarMascota();
       });
   }

   private void innitVar(){
       etNombre = findViewById(R.id.etNombre);
       etNacimiento =findViewById(R.id.etNacimiento);
       etTipo = findViewById(R.id.etTipo);
       etRaza = findViewById(R.id.etRaza);
       spinnerDuenio = findViewById(R.id.spinnerDuenio);
       btnGuardar = findViewById(R.id.btnGuardar);
       txtMensaje=findViewById(R.id.txt_mensaje_alta_mascota);
   }

   private ArrayList<Propietarios> mostrarPropietarios(){
       AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
       SQLiteDatabase bd = admin.getWritableDatabase();
       ArrayList<Propietarios> listaPropietarios = new ArrayList<Propietarios>();

       try {
           Cursor filas = bd.rawQuery("SELECT  nombre FROM propietarios", null);
           if(filas.moveToFirst()){
               do{
                   Propietarios prop = new Propietarios();
                   //prop.setId(filas.getInt(0));
                   prop.setNombre(filas.getString(0));
                   listaPropietarios.add(prop);
               } while (filas.moveToNext());
           } else {
               return null;
           }
       }
       catch (Exception e){
           e.printStackTrace();
           return null;
       }
    return listaPropietarios;
   }

   public void guardarMascota(){
       AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"consultorioVeterinario",null,1);
       SQLiteDatabase BaseDeDatos=admin.getWritableDatabase();

       String nombre = etNombre.getText().toString();
       String nacimiento = etNacimiento.getText().toString();
       String tipo = etTipo.getText().toString();
       String raza = etRaza.getText().toString();
       String duenio = spinnerDuenio.getSelectedItem().toString();

       try {
           if (!nombre.isEmpty() && !nacimiento.isEmpty() && !tipo.isEmpty() && !raza.isEmpty() && !duenio.isEmpty()) {
               ContentValues registro = new ContentValues();
               registro.put("nombre", nombre);
               registro.put("fecha_nac", nacimiento);
               registro.put("tipo", tipo);
               registro.put("raza", raza);
               registro.put("idPropietario", buscarIdPropietario(duenio));
               Toast.makeText(this, "id de propietario:"+  buscarIdPropietario(duenio), Toast.LENGTH_SHORT).show();
               long idRegistro = BaseDeDatos.insert("mascotas", null, registro);

               BaseDeDatos.close();
               etNombre.setText("");
               etNacimiento.setText("");
               etTipo.setText("");
               etRaza.setText("");
               spinnerDuenio.setSelection(0);

               if (idRegistro != -1) {
                   Toast.makeText(this, "Mascota agregada con exito", Toast.LENGTH_SHORT).show();
                   txtMensaje.setTextColor(Color.parseColor("#3beb10"));
                   txtMensaje.setText("Mascota agregada con exito");
               } else {
                   Toast.makeText(this, "No se pudo agregar", Toast.LENGTH_SHORT).show();
                   txtMensaje.setTextColor(Color.parseColor("#fa1005"));
                   txtMensaje.setText("No se pudo agregar");

               }
           } else {
               Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
               txtMensaje.setTextColor(Color.parseColor("#fa1005"));
               txtMensaje.setText("Complete los campos");
           }
       }
       catch (Exception e){
           e.printStackTrace();
           txtMensaje.setTextColor(Color.parseColor("#fa1005"));
           txtMensaje.setText("Error: " +e.getMessage());

       }
   }

   public int buscarIdPropietario(String nombreProp){
       AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
       SQLiteDatabase db = admin.getWritableDatabase();
       int id = 0;


       if(!nombreProp.isEmpty()){
           Cursor fila = db.rawQuery("select id from propietarios where nombre = '"+ nombreProp+"'", null);

           if(fila.moveToFirst()){
                id = fila.getInt(0);
                db.close();
           }
           else {
               Toast.makeText(this, "No se encontro el propietario", Toast.LENGTH_SHORT).show();
               db.close();
           }
       }
       else {
           Toast.makeText(this, "Introduzca un nombreProp", Toast.LENGTH_SHORT).show();
       }

       return id;
   }
}