package com.example.tp_integrador_grupo7;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AltaTratamientoActivity extends AppCompatActivity {

    private EditText etMedicamento, etDosis, etDuracion;
    private Spinner spinnerCitas;
    private Button btnGuardarTratamiento;

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

        // Inserta el tratamiento en la base de datos
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this, "Veterinaria", null, 1);
        boolean insertado = dbHelper.insertarTratamiento(medicamento, dosis, duracion, numeroCita);

        if (insertado) {
            Toast.makeText(this, "Tratamiento guardado correctamente", Toast.LENGTH_SHORT).show();
            // Opcionalmente, puedes limpiar los campos después de guardar
            etMedicamento.setText("");
            etDosis.setText("");
            etDuracion.setText("");
            spinnerCitas.setSelection(0); // Resetear el Spinner a su primer elemento
        } else {
            Toast.makeText(this, "Error al guardar el tratamiento", Toast.LENGTH_SHORT).show();
        }
    }
}
