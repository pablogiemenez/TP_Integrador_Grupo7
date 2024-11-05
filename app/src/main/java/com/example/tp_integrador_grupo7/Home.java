package com.example.tp_integrador_grupo7;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private TextView textViewUsuario;
    private Button btnCitas, btnMascotas, btnTratamientos, btnReportesMedicos, btnVeterinarios, btnConfiguracion, btnPropietarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Inicialización de los elementos de la interfaz
        textViewUsuario = findViewById(R.id.textView9);
        btnCitas = findViewById(R.id.btnCitas);
        btnMascotas = findViewById(R.id.button5);
        btnTratamientos = findViewById(R.id.button7);
        btnReportesMedicos = findViewById(R.id.button8);
        btnVeterinarios = findViewById(R.id.button9);
        btnConfiguracion = findViewById(R.id.button10);
        btnPropietarios=findViewById(R.id.btn_propietarios);

        // Configurar el nombre de usuario
        String nombreUsuario = getIntent().getStringExtra("nombre_usuario");
        if (nombreUsuario != null) {
            textViewUsuario.setText("Usuario: " + nombreUsuario);
        } else {
            textViewUsuario.setText("Usuario: desconocido");
        }
        btnPropietarios.setOnClickListener(v->{
            Intent i= new Intent(this,AltaPropietariosActivity.class);
            startActivity(i);
        });

        /* Configurar las acciones de los botones
        btnCitas.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, CitasActivity.class);
            startActivity(intent);
        });

        btnMascotas.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, MascotasActivity.class);
            startActivity(intent);
        });

        btnTratamientos.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, TratamientosActivity.class);
            startActivity(intent);
        });

        btnReportesMedicos.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, ReportesMedicosActivity.class);
            startActivity(intent);
        });

        btnVeterinarios.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, VeterinariosActivity.class);
            startActivity(intent);
        });

        btnConfiguracion.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, ConfiguracionActivity.class);
            startActivity(intent);
        });*/
    }
}
