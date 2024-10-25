package com.example.tp_integrador_grupo7;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AltaVeterinarioActivity extends AppCompatActivity {
    private EditText etNombre;
    private EditText etEmail;
    private EditText etTelefono;
    private EditText etContrasenia;
    private EditText etNombreUsuario;
    private TextView txtMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alta_veterinario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etNombre=findViewById(R.id.et_nombre);
        etContrasenia=findViewById(R.id.et_contrasenia);
        etEmail=findViewById(R.id.et_email);
        etNombreUsuario=findViewById(R.id.et_nombreUsuario);
        etTelefono=findViewById(R.id.et_telefono);
        txtMensaje=findViewById(R.id.txt_mensaje);
    }
}