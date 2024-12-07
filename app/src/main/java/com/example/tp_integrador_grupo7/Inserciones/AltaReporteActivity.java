package com.example.tp_integrador_grupo7.Inserciones;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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
    private TextView twIdcita, twDiagnostico, twHallazgos, txtVolver;
    private Button btnSave;
    private Spinner spinnerIdCita;
    private DataReportes data = new DataReportes(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_reporte);

        // Inicializar variables
        innitVars();

        // Configurar eventos
        txtVolver.setOnClickListener(v -> volver());
        btnSave.setOnClickListener(view -> guardarReportes());

        // Mostrar lista de citas en el Spinner
        ArrayList<Citas> listaCitas = mostrarCitas();
        if (listaCitas != null) {
            ArrayAdapter<Citas> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaCitas);
            spinnerIdCita.setAdapter(arrayAdapter);
        }
    }

    // Método para volver a la lista de reportes
    public void volver() {
        Intent i = new Intent(this, ListadoReportesActivity.class);
        startActivity(i);
    }

    // Método para guardar reportes
    private void guardarReportes() {
        // Crear un nuevo reporte
        Reporte rep = new Reporte();
        rep.setDiagnostico(etDiagnostico.getText().toString());
        rep.setHallazgos(etHallazgos.getText().toString());

        // Obtener el objeto seleccionado del Spinner
        Citas citaSeleccionada = (Citas) spinnerIdCita.getSelectedItem();
        if (citaSeleccionada != null) {
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
    private void innitVars() {
        spinnerIdCita = findViewById(R.id.spinnerIdCita);
        etDiagnostico = findViewById(R.id.etDiagnostico);
        etHallazgos = findViewById(R.id.etHallazgos);
        twIdcita = findViewById(R.id.twIdcita);
        twDiagnostico = findViewById(R.id.twDiagnostico);
        twHallazgos = findViewById(R.id.twHallazgos); // Referencia correcta
        btnSave = findViewById(R.id.btnSave);
        txtVolver = findViewById(R.id.txt_volver_alta_reporte);
    }

    // Mostrar citas en el Spinner
    public ArrayList<Citas> mostrarCitas() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ArrayList<Citas> listaCitas = new ArrayList<>();

        try {
            Cursor filas = bd.rawQuery("SELECT id, motivo FROM citas", null);
            if (filas.moveToFirst()) {
                do {
                    Citas citas = new Citas();
                    citas.setId(filas.getInt(0));
                    citas.setMotivo(filas.getString(1));
                    listaCitas.add(citas);
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
