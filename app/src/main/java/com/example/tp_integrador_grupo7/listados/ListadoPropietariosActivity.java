package com.example.tp_integrador_grupo7.listados;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_integrador_grupo7.AdminSQLiteOpenHelper;
import com.example.tp_integrador_grupo7.DetallePropietarioActivity;
import com.example.tp_integrador_grupo7.Home;
import com.example.tp_integrador_grupo7.Inserciones.AltaPropietariosActivity;
import com.example.tp_integrador_grupo7.R;
import com.example.tp_integrador_grupo7.entidades.Propietarios;

import java.util.ArrayList;

public class ListadoPropietariosActivity extends AppCompatActivity {
    private ListView lvPropietarios;
    private TextView txtVolver;
    private Button btnNuevoRegistro;
    ArrayList<Propietarios> listaPropietarios;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado_propietarios);
        innitVars();


        btnNuevoRegistro.setOnClickListener(v->{
            Intent i =new Intent(this, AltaPropietariosActivity.class);
            startActivity(i);
        });

        txtVolver.setOnClickListener(v->{
            Intent i= new Intent(this, Home.class);
            startActivity(i);
        });

        setLvPropietarios();
        lvPropietarios.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id)->{
            Intent i = new Intent(this, DetallePropietarioActivity.class);
            Propietarios prop= listaPropietarios.get(position);
            i.putExtra("propietario",prop);
            startActivity(i);

        });
    }

    private void innitVars(){
        btnNuevoRegistro= findViewById(R.id.btn_alta_propietario);
        lvPropietarios=findViewById(R.id.lv_propietarios);
        txtVolver=findViewById(R.id.txt_volver_listado_propietarios);
    }

    public void setLvPropietarios(){
        try {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
            listaPropietarios = admin.obtenerListaPropietarios();
            if (!listaPropietarios.isEmpty()) {
                ArrayAdapter<Propietarios> adap = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaPropietarios);
                lvPropietarios.setAdapter(adap);
            }
        }catch (Exception e){
            Toast.makeText(this, "error al cargar los registros", Toast.LENGTH_SHORT).show();
        }
    }
}
