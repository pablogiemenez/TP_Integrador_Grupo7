package com.example.tp_integrador_grupo7.listados;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tp_integrador_grupo7.AdminSQLiteOpenHelper;
import com.example.tp_integrador_grupo7.Home;
import com.example.tp_integrador_grupo7.Inserciones.AltaMascotaActivity;
import com.example.tp_integrador_grupo7.R;

import java.util.ArrayList;

public class ListadoMascotasActivity extends AppCompatActivity {
    private ListView lvMascotas;
    private Button btnNuevoRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado_mascotas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initVars();
        btnNuevoRegistro.setOnClickListener(v->irAltaMascota());
        setLvMascotas();
    }

    public void initVars() {
        lvMascotas = findViewById(R.id.lv_mascotas);
        btnNuevoRegistro=findViewById(R.id.btn_nuevo_registro_mascota);
    }

    public void irAltaMascota(){
        Intent i= new Intent(this, AltaMascotaActivity.class);
        startActivity(i);
    }

    public void setLvMascotas() {
        try {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
            ArrayList<String> listaMascotas = admin.obtenerListaMascotas();
            if (!listaMascotas.isEmpty()) {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaMascotas);
                lvMascotas.setAdapter(arrayAdapter);
            } else {
                Toast.makeText(this,"Aun no hay mascotas", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

