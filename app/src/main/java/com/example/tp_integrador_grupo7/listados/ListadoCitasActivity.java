package com.example.tp_integrador_grupo7.listados;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tp_integrador_grupo7.AdminSQLiteOpenHelper;
import com.example.tp_integrador_grupo7.Inserciones.AltaCitaActivity;
import com.example.tp_integrador_grupo7.Inserciones.AltaMascotaActivity;
import com.example.tp_integrador_grupo7.R;
import com.example.tp_integrador_grupo7.SessionVeterinario;

import java.util.ArrayList;

public class ListadoCitasActivity extends AppCompatActivity {
    private ListView lvCitas;
    private Button btnNuevaCita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado_citas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initVars();
        setLvCitas();
        btnNuevaCita.setOnClickListener(view -> {
            Intent i= new Intent(this, AltaCitaActivity.class);
            startActivity(i);
        });
    }
    public void initVars(){
        //txtVolver=findViewById(R.id.txt_volver_listado_citas);
        btnNuevaCita = findViewById(R.id.btnNuevaCita);
        lvCitas=findViewById(R.id.lvCitas);
        //txtMensaje=findViewById(R.id.txt_mensaje_proximas_citas);

    }
    /*public void volverHome(){
        Intent i= new Intent(this, Home.class);
        startActivity(i);

    }*/

    public void setLvCitas(){
        try{
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"consultorioVeterinario",null,1);
        SessionVeterinario session= (SessionVeterinario) getApplication();
        ArrayList<String> listaCitas=admin.obtenerListaCitasXIdVeterinario(session.getIdSession());
        if(!listaCitas.isEmpty()){
            ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listaCitas);
            lvCitas.setAdapter(arrayAdapter);
        }else{
            Toast.makeText(this,"Aun no tienen citas",Toast.LENGTH_SHORT).show();
        }
        }catch(Exception e){
            Toast.makeText(this, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}