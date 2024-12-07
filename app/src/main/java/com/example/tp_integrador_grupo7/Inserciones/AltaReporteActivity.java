package com.example.tp_integrador_grupo7.Inserciones;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_integrador_grupo7.AdminSQLiteOpenHelper;
import com.example.tp_integrador_grupo7.R;
import com.example.tp_integrador_grupo7.datos.DataReportes;
import com.example.tp_integrador_grupo7.entidades.Citas;
import com.example.tp_integrador_grupo7.entidades.Reporte;
import com.example.tp_integrador_grupo7.listados.ListadoReportesActivity;

import java.util.ArrayList;

public class AltaReporteActivity extends AppCompatActivity {
    private EditText etDiagnostico, etHallazgos;
    private TextView txtVolver;
    private Button btnSave;
    private Spinner spinnerIdCita;
    private DataReportes data = new DataReportes(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_reporte);

        // Inicializar variables
        initVars();

        // Configurar eventos
        txtVolver.setOnClickListener(v -> volver());
        btnSave.setOnClickListener(view -> guardarReporte());

        // Mostrar lista de citas en el Spinner
        ArrayList<Citas> listaCitas = mostrarCitas();
        if (listaCitas != null) {
            ArrayAdapter<Citas> arrayAdapter = new ArrayAdapter<>(this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaCitas);
            spinnerIdCita.setAdapter(arrayAdapter);
        }
    }

    // Método para volver a la lista de reportes
    private void volver() {
        Intent i = new Intent(this, ListadoReportesActivity.class);
        startActivity(i);
    }

    // Método para guardar un reporte
    private void guardarReporte() {
        // Crear un nuevo reporte
        Reporte rep = new Reporte();
        rep.setDiagnostico(etDiagnostico.getText().toString());
        rep.setHallazgos(etHallazgos.getText().toString());

        // Validar selección del Spinner
        if (spinnerIdCita.getSelectedItem() instanceof Citas) {
            Citas citaSeleccionada = (Citas) spinnerIdCita.getSelectedItem();
            rep.setIdCita(citaSeleccionada.getId());
        } else {
            Toast.makeText(this, "Selecciona una cita válida", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar campos obligatorios
        if (rep.getDiagnostico().isEmpty() || rep.getHallazgos().isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insertar el reporte
        data.insertarReporte(rep);
        Toast.makeText(this, "Reporte guardado correctamente", Toast.LENGTH_SHORT).show();

        // Redirigir a la lista de reportes
        volver();
    }

    // Inicializar variables de la interfaz
    private void initVars() {
        spinnerIdCita = findViewById(R.id.spinnerIdCita);
        etDiagnostico = findViewById(R.id.etDiagnostico);
        etHallazgos = findViewById(R.id.etHallazgos);
        txtVolver = findViewById(R.id.txt_volver_alta_reporte);
        btnSave = findViewById(R.id.btnSave);
    }

    // Mostrar citas en el Spinner
    private ArrayList<Citas> mostrarCitas() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ArrayList<Citas> listaCitas = new ArrayList<>();

        try {
            Cursor filas = bd.rawQuery("SELECT id, motivo FROM citas", null);
            if (filas.moveToFirst()) {
                do {
                    Citas cita = new Citas();
                    cita.setId(filas.getInt(0));
                    cita.setMotivo(filas.getString(1));
                    listaCitas.add(cita);
                } while (filas.moveToNext());
            } else {
                Toast.makeText(this, "No hay citas disponibles", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            bd.close();
        }

        return listaCitas;
    }
}
