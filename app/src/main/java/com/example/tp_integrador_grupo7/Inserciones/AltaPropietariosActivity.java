package com.example.tp_integrador_grupo7.Inserciones;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_integrador_grupo7.AdminSQLiteOpenHelper;
import com.example.tp_integrador_grupo7.Home;
import com.example.tp_integrador_grupo7.R;

public class AltaPropietariosActivity extends AppCompatActivity {
    //private Button btnGuardar;
    private EditText etNombre,etEmail,etTelefono,etDni;
    private TextView txtMensaje;//, txtVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.registro_propietario);
            Button btnGuardar = findViewById(R.id.btn_guardarPropietario);
            etNombre = findViewById(R.id.et_nombrePropietario);
            etEmail = findViewById(R.id.et_correo);
            etTelefono = findViewById(R.id.et_telefono);
            etDni = findViewById(R.id.et_dni);
            txtMensaje = findViewById(R.id.txt_mensaje);
            TextView txtVolver = findViewById(R.id.txt_volver);

            txtVolver.setOnClickListener(v -> {
                Intent i = new Intent(this, Home.class);
                startActivity(i);

            });
            btnGuardar.setOnClickListener(v -> {

                GuardarPropietario();

            });
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void GuardarPropietario(){
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"consultorioVeterinario",null,1);
        SQLiteDatabase BaseDeDatos=admin.getWritableDatabase();

        String nombrePropietario=etNombre.getText().toString();
        String mail= etEmail.getText().toString();
        String telefono= etTelefono.getText().toString();
        String dni= etDni.getText().toString();
        if(!Exist(dni)) {
            if (!nombrePropietario.isEmpty() && !mail.isEmpty() && !dni.isEmpty() && !telefono.isEmpty()) {
                ContentValues registro = new ContentValues();
                registro.put("nombre", nombrePropietario);
                registro.put("dni", dni);
                registro.put("telefono", telefono);
                registro.put("mail", mail);

                long idRegistro = BaseDeDatos.insert("propietarios", null, registro);

                BaseDeDatos.close();
                etNombre.setText("");
                etTelefono.setText("");
                etEmail.setText("");
                etDni.setText("");

                if (idRegistro != -1) {
                    txtMensaje.setTextColor(Color.parseColor("#3beb10"));
                    txtMensaje.setText("Propietario agregado con exito");
                } else {
                    txtMensaje.setTextColor(Color.parseColor("#fa1005"));
                    txtMensaje.setText("no se pudo agregar");
                }
            } else {
                txtMensaje.setTextColor(Color.parseColor("#fa1005"));
                txtMensaje.setText("complete todos los campos");
            }
        }else{
            txtMensaje.setTextColor(Color.parseColor("#fa1005"));
            txtMensaje.setText("el DNI ya está registrado");
        }

    }
    public boolean Exist(String dni){
        boolean exist=false;
        try{
            AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"consultorioVeterinario",null,1);
            SQLiteDatabase baseDeDatos=admin.getWritableDatabase();
            Cursor cursor=baseDeDatos.rawQuery("SELECT * FROM propietarios where dni=?", new String[]{dni});
            if(cursor.moveToFirst())
                exist=true;

        }
        catch(Exception e){
            e.printStackTrace();

        }
        return exist;
    }
}
