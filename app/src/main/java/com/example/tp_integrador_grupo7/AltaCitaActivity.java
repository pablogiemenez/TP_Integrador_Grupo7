package com.example.tp_integrador_grupo7;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.se.omapi.Session;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_integrador_grupo7.entidades.Mascotas;
import com.example.tp_integrador_grupo7.entidades.Propietarios;

import java.util.ArrayList;

public class AltaCitaActivity extends AppCompatActivity {
    private EditText etFecha, etMotivo;
    private Spinner spinnerMascota;
    private Button btnGuardarCita;
    private TextView txtVolver;
    private Integer idVeterinario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_cita);
        initVar();

        ArrayList<Mascotas> listaMascotas = mostrarMascotas();
        if(listaMascotas!=null){
            ArrayAdapter<Mascotas> arrayAdapter = new ArrayAdapter<Mascotas>(getApplicationContext(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,listaMascotas);
            spinnerMascota.setAdapter(arrayAdapter);
        }



        txtVolver.setOnClickListener(v->volverHome());
        btnGuardarCita.setOnClickListener(view -> guardarCita());
    }

    private void volverHome(){
        Intent i= new Intent(this, Home.class);
        startActivity(i);
    }

    private void initVar() {
        etFecha = findViewById(R.id.etFecha);
        etMotivo = findViewById(R.id.etMotivo);
        spinnerMascota = findViewById(R.id.spinnerMascota);
        txtVolver=findViewById(R.id.txt_volver_alta_cita);
        btnGuardarCita = findViewById(R.id.btnGuardarCita);
    }

    private ArrayList<Mascotas> mostrarMascotas(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ArrayList<Mascotas> listaMascotas = new ArrayList<Mascotas>();

        try {
            Cursor filas = bd.rawQuery("SELECT id, nombre FROM mascotas", null);
            if(filas.moveToFirst()){
                do{
                    Mascotas mascotas = new Mascotas();
                    mascotas.setId(filas.getInt(0));
                    mascotas.setNombre(filas.getString(1));
                    listaMascotas.add(mascotas);
                } while (filas.moveToNext());
            } else {
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return listaMascotas;
    }

    public void guardarCita() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String fecha = etFecha.getText().toString();
        String motivo = etMotivo.getText().toString();
        String nombreMascota = spinnerMascota.getSelectedItem().toString();


        if (!fecha.isEmpty() && !motivo.isEmpty() && !nombreMascota.isEmpty()) {
            int idMascota = buscarIdMascota(nombreMascota);
            SessionVeterinario session= new SessionVeterinario();

            idVeterinario = session.getIdSession();

            if (idMascota != 0 && idVeterinario!=null) {
                ContentValues registro = new ContentValues();
                registro.put("fecha", fecha);
                registro.put("motivo", motivo);
                registro.put("id_mascota", idMascota);
                registro.put("id_veterinario", idVeterinario);

                long idRegistro = BaseDeDatos.insert("citas", null, registro);

                BaseDeDatos.close();
                limpiarCampos();

                if (idRegistro != -1) {
                    Toast.makeText(this, "Cita agregada con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No se pudo agregar la cita", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No se encontraron IDs de mascota o veterinario", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etFecha.setText("");
        etMotivo.setText("");
        spinnerMascota.setSelection(0);

    }

    public int buscarIdMascota(String nombreMascota) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        int id = 0;

        Cursor fila = db.rawQuery("SELECT id_mascota FROM mascotas WHERE nombre = ?", new String[]{nombreMascota});

        if (fila.moveToFirst()) {
            id = fila.getInt(0);
        } else {
            Toast.makeText(this, "No se encontró la mascota", Toast.LENGTH_SHORT).show();
        }
        fila.close();
        db.close();
        return id;
    }

    public int buscarIdVeterinario(String nombreVeterinario) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        int id = 0;

        Cursor fila = db.rawQuery("SELECT id_veterinario FROM veterinarios WHERE nombre = ?", new String[]{nombreVeterinario});

        if (fila.moveToFirst()) {
            id = fila.getInt(0);
        } else {
            Toast.makeText(this, "No se encontró el veterinario", Toast.LENGTH_SHORT).show();
        }
        fila.close();
        db.close();
        return id;
    }
}
