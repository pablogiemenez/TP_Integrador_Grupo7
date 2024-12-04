package com.example.tp_integrador_grupo7.Inserciones;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_integrador_grupo7.R;

public class AltaReporteActivity extends AppCompatActivity {
    private EditText etIdcita, etDiagnostico, etHallazgos, etfecha;
    private TextView twIdcita, twDiagnostico, twHallazgos, twFecha;
    private Button btnSave;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_reporte);
        innitVars();
    }

    private void innitVars(){
        etIdcita = findViewById(R.id.etIdcita);
        etDiagnostico = findViewById(R.id.etDiagnostico);
        etHallazgos = findViewById(R.id.etHallazgos);
        etfecha = findViewById(R.id.etfecha);
        twIdcita = findViewById(R.id.twIdcita);
        twDiagnostico = findViewById(R.id.twDiagnostico);
        twHallazgos = findViewById(R.id.twFecha);
        btnSave = findViewById(R.id.btnSave);
    }
}
