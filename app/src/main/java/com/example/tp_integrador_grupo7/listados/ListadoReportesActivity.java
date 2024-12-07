package com.example.tp_integrador_grupo7.listados;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_integrador_grupo7.AdminSQLiteOpenHelper;
import com.example.tp_integrador_grupo7.Inserciones.AltaReporteActivity;
import com.example.tp_integrador_grupo7.R;

import java.util.ArrayList;

public class ListadoReportesActivity extends AppCompatActivity {
    private Button btnNuevoReporte;
    private ListView lvReportes;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado_reportes);
        innitVars();
        setLvMascotas();
        btnNuevoReporte.setOnClickListener(view -> {
            Intent i= new Intent(this, AltaReporteActivity.class);
            startActivity(i);
        });
    }

    private void innitVars(){
        btnNuevoReporte = findViewById(R.id.btnAggReporte);
        lvReportes = findViewById(R.id.lvReportes);
    }



    private void setLvMascotas() {
        try {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
            ArrayList<String> listaReportes = admin.obtenerListaMascotas();
            if (!listaReportes.isEmpty()) {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaReportes);
                lvReportes.setAdapter(arrayAdapter);
            } else {
                Toast.makeText(this,"Aun no hay reportes", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
