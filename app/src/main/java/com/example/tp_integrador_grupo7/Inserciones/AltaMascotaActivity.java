package com.example.tp_integrador_grupo7.Inserciones;

import android.content.ContentValues;
import android.content.Intent;
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
import com.example.tp_integrador_grupo7.Home;
import com.example.tp_integrador_grupo7.R;
import com.example.tp_integrador_grupo7.entidades.Propietarios;
import com.example.tp_integrador_grupo7.listados.ListadoMascotasActivity;

import java.util.ArrayList;

public class AltaMascotaActivity extends AppCompatActivity {
    private EditText etNombre, etNacimiento, etTipo, etRaza;
    private Spinner spinnerDuenio;
    private Button btnGuardar;
    private TextView txtMensaje, txtVolver;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_alta_mascota);
       innitVar();

       ArrayList<String> listaPropietarios = mostrarPropietarios();
       if(listaPropietarios!=null){
           ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                   androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,listaPropietarios);
           spinnerDuenio.setAdapter(arrayAdapter);

       }
;
        txtVolver.setOnClickListener(v->volver());

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
       txtVolver= findViewById(R.id.txt_volver_alta_mascota);
   }
    public void volver(){
        Intent i= new Intent(this, ListadoMascotasActivity.class);
        startActivity(i);

    }

   private ArrayList<String> mostrarPropietarios(){
       AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
       SQLiteDatabase bd = admin.getWritableDatabase();
       ArrayList<String> listaPropietarios = new ArrayList<String>();

       try {
           Cursor filas = bd.rawQuery("SELECT  nombre FROM propietarios", null);
           if(filas.moveToFirst()){
               do{
                   listaPropietarios.add(filas.getString(0));
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
           if (!nombre.isEmpty() || !nacimiento.isEmpty() || !tipo.isEmpty() || !raza.isEmpty() || !duenio.isEmpty()) {
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
               //Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
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
