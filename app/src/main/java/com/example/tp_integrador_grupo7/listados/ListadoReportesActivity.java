package com.example.tp_integrador_grupo7.listados;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_integrador_grupo7.Home;
import com.example.tp_integrador_grupo7.Inserciones.AltaReporteActivity;
import com.example.tp_integrador_grupo7.R;
import com.example.tp_integrador_grupo7.datos.DataReportes;
import com.example.tp_integrador_grupo7.entidades.Reporte;

import java.util.ArrayList;

public class ListadoReportesActivity extends AppCompatActivity {
    private Button btnNuevoReporte;
    private ListView lvReportes;
    private TextView txtVolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_reportes);

        // Inicializar variables
        initVars();

        // Configurar eventos
        btnNuevoReporte.setOnClickListener(view -> {
            Intent i = new Intent(this, AltaReporteActivity.class);
            startActivity(i);
        });

        txtVolver.setOnClickListener(v -> volverHome());

        // Cargar lista de reportes
        DataReportes data = new DataReportes(this);
        try {
            ArrayList<Reporte> listaReportes = data.mostrarReportes();
            if (listaReportes != null && !listaReportes.isEmpty()) {
                ArrayAdapter<Reporte> arrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, listaReportes);
                lvReportes.setAdapter(arrayAdapter);
            } else {
                Toast.makeText(this, "No hay reportes disponibles", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al cargar los reportes", Toast.LENGTH_SHORT).show();
        }
    }

    private void volverHome() {
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    private void initVars() {
        btnNuevoReporte = findViewById(R.id.btnAggReporte);
        lvReportes = findViewById(R.id.lvReportes);
        txtVolver = findViewById(R.id.txt_volver_reporte);
    }
}
