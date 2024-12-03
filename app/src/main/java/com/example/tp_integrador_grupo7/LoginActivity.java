package com.example.tp_integrador_grupo7;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_integrador_grupo7.Inserciones.AltaVeterinarioActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText et_nombreUsuario;
    private EditText et_contrasenia;
    private TextView txt_mensaje;
    private TextView txt_registrar;
    private Button btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicialización de los elementos de la interfaz
        et_nombreUsuario = findViewById(R.id.et_nombreUsuario);
        et_contrasenia = findViewById(R.id.et_contrasenia);
        txt_mensaje = findViewById(R.id.txt_mensaje);
        txt_registrar = findViewById(R.id.txt_registrar);
        btnIniciarSesion = findViewById(R.id.btn_IniciarSesion);

        // Configurar el botón "Registrar" para abrir AltaVeterinarioActivity
        txt_registrar.setOnClickListener(v -> {
            Intent intent = new Intent(this, AltaVeterinarioActivity.class);
            startActivity(intent);
        });

        // Configurar el botón "Iniciar Sesión" para verificar las credenciales
        btnIniciarSesion.setOnClickListener(v -> {
            verificarCredenciales();
        });
    }

    // Método para verificar las credenciales de inicio de sesión
    private void verificarCredenciales() {
        String nombreUsuario = et_nombreUsuario.getText().toString();
        String contrasenia = et_contrasenia.getText().toString();

        if (!nombreUsuario.isEmpty() && !contrasenia.isEmpty()) {
            // Abrir la base de datos
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
            SQLiteDatabase db = admin.getReadableDatabase();

            // Consulta para verificar las credenciales
            Cursor cursor = db.rawQuery("SELECT * FROM veterinarios WHERE nombre_usuario = ? AND contrasenia = ?",
                    new String[]{nombreUsuario, contrasenia});

            if (cursor.moveToFirst()) {
                // Inicio de sesión exitoso
                txt_mensaje.setTextColor(Color.parseColor("#3beb10"));
                txt_mensaje.setText("Inicio de sesión exitoso");

                // Redirigir a la actividad Home y pasar el nombre de usuario
                Intent intent = new Intent(this, Home.class);
                intent.putExtra("nombre_usuario", nombreUsuario);  // Pasar el nombre de usuario
                startActivity(intent);

                // Cerrar LoginActivity para que el usuario no pueda volver con el botón "Atrás"
                finish();
            } else {
                // Credenciales incorrectas
                txt_mensaje.setTextColor(Color.parseColor("#fa1005"));
                txt_mensaje.setText("Nombre de usuario o contraseña incorrectos");
            }

            cursor.close();
            db.close();
        } else {
            // Mostrar mensaje si hay campos vacíos
            txt_mensaje.setTextColor(Color.parseColor("#fa1005"));
            txt_mensaje.setText("Por favor, complete todos los campos");
        }
    }
}
