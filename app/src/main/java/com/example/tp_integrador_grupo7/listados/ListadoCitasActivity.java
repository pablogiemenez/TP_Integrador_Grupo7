package com.example.tp_integrador_grupo7.listados;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
import com.example.tp_integrador_grupo7.R;
import com.example.tp_integrador_grupo7.SessionVeterinario;

import java.util.ArrayList;

public class ListadoCitasActivity extends AppCompatActivity {
    private TextView txtVolver,txtMensaje;
    private ListView lvCitas;
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
        txtVolver.setOnClickListener(v->volverHome());
        setLvCitas();
    }
    public void initVars(){
        txtVolver=findViewById(R.id.txt_volver_listado_citas);
        lvCitas=findViewById(R.id.lv_citas);
        txtMensaje=findViewById(R.id.txt_mensaje_proximas_citas);

    }
    public void volverHome(){
        Intent i= new Intent(this, Home.class);
        startActivity(i);

    }
    public void setLvCitas(){
        try{
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"consultorioVeterinario",null,1);
        SessionVeterinario session= (SessionVeterinario) getApplication();
        ArrayList<String> listaCitas=admin.obtenerListaCitasXIdVeterinario(session.getIdSession());
        if(listaCitas.isEmpty()){
            ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listaCitas);
            lvCitas.setAdapter(arrayAdapter);
        }else{
            txtMensaje.setTextSize(20);
            txtMensaje.setText("Aún no tienes citas");
        }
        }catch(Exception e){
            Toast.makeText(this, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}