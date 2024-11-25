package com.example.tp_integrador_grupo7;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private TextView textViewUsuario, txtCerrarSesion,txtIdUsuario;
    private Button btnCitas, btnMascotas, btnTratamientos, btnReportesMedicos, btnVeterinarios, btnConfiguracion, btnPropietarios,btnProximasCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Inicialización de los elementos de la interfaz
        txtIdUsuario=findViewById(R.id.txt_IdUsuario);
        txtCerrarSesion=findViewById(R.id.txt_cerrarSesion);
        textViewUsuario = findViewById(R.id.textView9);
        btnCitas = findViewById(R.id.btnCitas);
        btnMascotas = findViewById(R.id.button5);
        btnTratamientos = findViewById(R.id.button7);
        btnReportesMedicos = findViewById(R.id.button8);
        btnVeterinarios = findViewById(R.id.button9);
        btnConfiguracion = findViewById(R.id.button10);
        btnPropietarios=findViewById(R.id.btn_propietarios);
        btnProximasCitas=findViewById(R.id.btn_proximas_citas);

        // Configurar el nombre de usuario
        SessionVeterinario session= (SessionVeterinario) getApplicationContext();
        String nombreUsuarioSession =session.getNombreUsuarioSession();
        Integer idSession=session.getIdSession();
        if(nombreUsuarioSession==null || idSession==null) {
            String nombreUsuario = getIntent().getStringExtra("nombre_usuario");

            session.setNombreUsuarioSession(nombreUsuario);
            nombreUsuarioSession=session.getNombreUsuarioSession();
            Integer id=obtenerIdXNombreUsuario(nombreUsuarioSession);
            session.setIdSession(id);
            idSession=session.getIdSession();

        }
        if (nombreUsuarioSession != null) {
            textViewUsuario.setText("Usuario: " + nombreUsuarioSession);
        } else {
            textViewUsuario.setText("Usuario: desconocido");
        }
        if(idSession!=-1){
            txtIdUsuario.setText("ID: "+ idSession);
        }else{
            txtIdUsuario.setText("ID no encontrado");
        }
        btnPropietarios.setOnClickListener(v->{
            Intent i= new Intent(this,AltaPropietariosActivity.class);
            startActivity(i);
        });

         //Configurar las acciones de los botones
        btnCitas.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, AltaCitaActivity.class);
            startActivity(intent);
        });

        btnMascotas.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, AltaMascotaActivity.class);
            startActivity(intent);
        });

        btnTratamientos.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, AltaTratamientoActivity.class);
            startActivity(intent);
        });
        btnProximasCitas.setOnClickListener(v->{
            Intent i=new Intent(this,ListadoCitasActivity.class);
            startActivity(i);
        });
        /*btnReportesMedicos.setOnClickListener(v -> {
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
        txtCerrarSesion.setOnClickListener(v->{
            session.setIdSession(null);
            session.setNombreUsuarioSession(null);
            Intent i= new Intent(this,LoginActivity.class);
            startActivity(i);
        });
    }
    public Integer obtenerIdXNombreUsuario(String nombreUsuario){
        Integer idObtenido=-1;
        if(nombreUsuario!=null){
            AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(this,"consultorioVeterinario",null,1);
            SQLiteDatabase BaseDeDatos= admin.getReadableDatabase();
            Cursor cursor= BaseDeDatos.rawQuery("SELECT id FROM veterinarios where nombre_usuario=?", new String[]{nombreUsuario});
            if(cursor.moveToFirst()){
                idObtenido=cursor.getInt(0);
            }
        }
        return idObtenido;
    }
}
