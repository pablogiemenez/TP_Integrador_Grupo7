package com.example.tp_integrador_grupo7;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AltaCitaActivity extends AppCompatActivity {
    private EditText etFecha, etMotivo;
    private Spinner spinnerMascota, spinnerVeterinario;
    private Button btnGuardarCita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_cita);
        initVar();

        btnGuardarCita.setOnClickListener(view -> guardarCita());
    }

    private void initVar() {
        etFecha = findViewById(R.id.etFecha);
        etMotivo = findViewById(R.id.etMotivo);
        spinnerMascota = findViewById(R.id.spinnerMascota);
        spinnerVeterinario = findViewById(R.id.spinnerVeterinario);
        btnGuardarCita = findViewById(R.id.btnGuardarCita);
    }

    public void guardarCita() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String fecha = etFecha.getText().toString();
        String motivo = etMotivo.getText().toString();
        String nombreMascota = spinnerMascota.getSelectedItem().toString();
        String nombreVeterinario = spinnerVeterinario.getSelectedItem().toString();

        if (!fecha.isEmpty() && !motivo.isEmpty() && !nombreMascota.isEmpty() && !nombreVeterinario.isEmpty()) {
            int idMascota = buscarIdMascota(nombreMascota);
            int idVeterinario = buscarIdVeterinario(nombreVeterinario);

            if (idMascota != 0 && idVeterinario != 0) {
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
        spinnerVeterinario.setSelection(0);
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
