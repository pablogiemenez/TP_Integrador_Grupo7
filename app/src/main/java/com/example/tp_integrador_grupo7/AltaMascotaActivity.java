package com.example.tp_integrador_grupo7;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AltaMascotaActivity extends AppCompatActivity {
    private EditText etNombre, etNacimiento, etTipo, etRaza;
    private Spinner spinnerDuenio;
    private Button btnGuardar;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_alta_mascota);
       innitVar();

       btnGuardar.setOnClickListener(view -> {
           guardarMascota();
       });
   }

   private void innitVar(){
       etNombre = findViewById(R.id.etNombre);
       etNacimiento =findViewById(R.id.etNacimiento);
       etTipo = findViewById(R.id.etTipo);
       etRaza = findViewById(R.id.etRaza);
       spinnerDuenio = findViewById(R.id.spinnerDueño);
       btnGuardar = findViewById(R.id.btnGuardar);
   }

   public void guardarMascota(){
       AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"consultorioVeterinario",null,1);
       SQLiteDatabase BaseDeDatos=admin.getWritableDatabase();

       String nombre = etNombre.getText().toString();
       String nacimiento = etNacimiento.getText().toString();
       String tipo = etTipo.getText().toString();
       String raza = etRaza.getText().toString();
       String duenio = spinnerDuenio.getSelectedItem().toString();

       if(!nombre.isEmpty()&&!nacimiento.isEmpty()&&!tipo.isEmpty()&&!raza.isEmpty()&&!duenio.isEmpty()) {
           ContentValues registro = new ContentValues();
           registro.put("nombre", nombre);
           registro.put("fecha_nac", nacimiento);
           registro.put("tipo", tipo);
           registro.put("raza", raza);
           registro.put("idPropietario", buscarIdPropietario());

           long idRegistro= BaseDeDatos.insert("propietarios",null,registro);

           BaseDeDatos.close();
           etNombre.setText("");
           etNacimiento.setText("");
           etTipo.setText("");
           etRaza.setText("");
           spinnerDuenio.setSelection(0);

           if(idRegistro!=-1){
               Toast.makeText(this, "Mascota agregada con exito", Toast.LENGTH_SHORT).show();
           }else{
               Toast.makeText(this, "No se pudo agregar", Toast.LENGTH_SHORT).show();
           }
       }
       else{
           Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
        }
   }

   public int buscarIdPropietario(){
       AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
       SQLiteDatabase db = admin.getWritableDatabase();
       int id = 0;

       String nombre = etNombre.getText().toString();

       if(!nombre.isEmpty()){
           Cursor fila = db.rawQuery("select id from propietarios where nombre = "+ nombre, null);

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
           Toast.makeText(this, "Introduzca un nombre", Toast.LENGTH_SHORT).show();
       }
       return id;
   }
}
