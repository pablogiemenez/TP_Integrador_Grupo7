package com.example.tp_integrador_grupo7.listados;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_integrador_grupo7.Inserciones.AltaReporteActivity;
import com.example.tp_integrador_grupo7.R;
import com.example.tp_integrador_grupo7.datos.DataReportes;
import com.example.tp_integrador_grupo7.entidades.Reporte;

import java.util.ArrayList;

public class ListadoReportesActivity extends AppCompatActivity {
    private Button btnNuevoReporte;
    private ListView lvReportes;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado_reportes);
        innitVars();
        btnNuevoReporte.setOnClickListener(view -> {
            Intent i= new Intent(this, AltaReporteActivity.class);
            startActivity(i);
        });

        DataReportes data = new DataReportes(this);
        try {
            ArrayAdapter<Reporte> arrayAdapter = new ArrayAdapter<Reporte>(this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, data.mostrarReportes());
            lvReportes.setAdapter(arrayAdapter);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void innitVars(){
        btnNuevoReporte = findViewById(R.id.btnAggReporte);
        lvReportes = findViewById(R.id.lvReportes);

    }
}
