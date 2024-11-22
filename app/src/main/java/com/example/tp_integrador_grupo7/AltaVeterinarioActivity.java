package com.example.tp_integrador_grupo7;

import android.content.ContentValues;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
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
    private Button btnRegistrar;
    private TextView txtVolver;
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
        txtVolver=findViewById(R.id.txt_volver);
        btnRegistrar=findViewById(R.id.btn_registrar);
        etNombre=findViewById(R.id.et_nombre);
        etContrasenia=findViewById(R.id.et_contrasenia);
        etEmail=findViewById(R.id.et_email);
        etNombreUsuario=findViewById(R.id.et_nombreUsuario);
        etTelefono=findViewById(R.id.et_telefono);
        txtMensaje=findViewById(R.id.txt_mensaje);
        txtVolver.setOnClickListener(v->{
            Intent i= new Intent(this, LoginActivity.class);
            startActivity(i);
        });
        btnRegistrar.setOnClickListener(v->{
            insertarVeterinario();
        });
    }
    public void insertarVeterinario(){
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"consultorioVeterinario",null,1);
        SQLiteDatabase BaseDeDatos=admin.getWritableDatabase();

        String nombre=etNombre.getText().toString();
        String mail=etEmail.getText().toString();
        String nombreUsuario= etNombreUsuario.getText().toString();
        String contrasenia= etContrasenia.getText().toString();
        String telefono= etTelefono.getText().toString();

        if(!Exist(nombreUsuario,mail)) {
            if (!nombre.isEmpty() || !mail.isEmpty() || !nombreUsuario.isEmpty() || !contrasenia.isEmpty()) {
                ContentValues registro = new ContentValues();
                registro.put("nombre", nombre);
                registro.put("contrasenia", contrasenia);
                registro.put("telefono", telefono);
                registro.put("mail", mail);
                registro.put("nombre_usuario", nombreUsuario);
                long idRegistro = BaseDeDatos.insert("veterinarios", null, registro);

                BaseDeDatos.close();
                etNombre.setText("");
                etTelefono.setText("");
                etEmail.setText("");
                etContrasenia.setText("");
                etNombreUsuario.setText("");
                if (idRegistro != -1) {
                    txtMensaje.setTextColor(Color.parseColor("#3beb10"));
                    txtMensaje.setText("Veterinario agregado con exito");
                } else {
                    txtMensaje.setTextColor(Color.parseColor("#fa1005"));
                    txtMensaje.setText("no se pudo agregar");
                }
            } else {
                txtMensaje.setTextColor(Color.parseColor("#fa1005"));
                txtMensaje.setText("complete los campos");
            }
        }else{
            txtMensaje.setTextColor(Color.parseColor("#fa1005"));
            txtMensaje.setText("nombre de usuario o mail ya existentes");
        }
    }
    public boolean Exist(String nombreUsuario, String email){
        boolean exist=false;
        try{
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"consultorioVeterinario",null,1);
        SQLiteDatabase baseDeDatos=admin.getWritableDatabase();
        Cursor cursor=baseDeDatos.rawQuery("SELECT * FROM veterinarios where nombre_usuario=? or mail=?", new String[]{nombreUsuario,email});
        if(cursor.moveToFirst())
            exist=true;

        }
        catch(Exception e){
            e.printStackTrace();

        }
        return exist;
    }
}