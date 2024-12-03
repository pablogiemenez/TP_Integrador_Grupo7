package com.example.tp_integrador_grupo7.Inserciones;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tp_integrador_grupo7.datos.DataTratamientos;
import com.example.tp_integrador_grupo7.entidades.Tratamiento;
import com.example.tp_integrador_grupo7.R;

public class AltaTratamientoActivity extends AppCompatActivity {

    private EditText etMedicamento, etDosis, etDuracion;
    private Spinner spinnerCitas;
    private Button btnGuardarTratamiento;
    private DataTratamientos dataTratamientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_tratamiento);

        // Vinculamos las vistas
        etMedicamento = findViewById(R.id.etMedicamento);
        etDosis = findViewById(R.id.etDosis);
        etDuracion = findViewById(R.id.etDuracion);
        spinnerCitas = findViewById(R.id.spinnerCitas);
        btnGuardarTratamiento = findViewById(R.id.btnGuardarTratamiento);

        // Inicializamos DataTratamientos
        dataTratamientos = new DataTratamientos(this);

        // Configuramos el botón Guardar
        btnGuardarTratamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarTratamiento();
            }
        });
    }

    private void guardarTratamiento() {
        // Obtener los valores ingresados
        String medicamento = etMedicamento.getText().toString().trim();
        String dosis = etDosis.getText().toString().trim();
        String duracion = etDuracion.getText().toString().trim();
        String numeroCita = spinnerCitas.getSelectedItem().toString();

        // Validar los datos
        if (medicamento.isEmpty() || dosis.isEmpty() || duracion.isEmpty() || numeroCita.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un objeto Tratamiento
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setNombreMedicamento(medicamento);
        tratamiento.setDosis(dosis);
        tratamiento.setDuracion(duracion);
        tratamiento.setObservaciones("Número de cita: " + numeroCita);
        tratamiento.setMascotaId(Integer.parseInt(numeroCita)); // Suponiendo que el número de cita es el ID de la mascota

        // Llamar al método para insertar tratamiento
        dataTratamientos.insertarTratamiento(tratamiento);
    }
}
