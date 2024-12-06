package com.example.tp_integrador_grupo7;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tp_integrador_grupo7.entidades.Propietarios;
import com.example.tp_integrador_grupo7.listados.ListadoPropietariosActivity;

public class DetallePropietarioActivity extends AppCompatActivity {
    private TextView txtVolver, txtDetalle;
    private Button btnBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_propietario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBorrar= findViewById(R.id.btn_borrar_detalle_propietario);

        txtDetalle= findViewById(R.id.txt_detalle_propietario);
        txtVolver=findViewById(R.id.txt_volver_detalle_propietario);
        Propietarios prop=(Propietarios)getIntent().getSerializableExtra("propietario");
        txtVolver.setOnClickListener(v->{
            Intent i=new Intent(this, ListadoPropietariosActivity.class);
            startActivity(i);
        });
        txtDetalle.setText(prop.toString());
        btnBorrar.setOnClickListener(v->borrar(prop.getId()));
    }
    public void borrar(int id){
        if(Exist(id)){
            AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this, "consultorioVeterinario",null,1);

            SQLiteDatabase baseDeDatos=admin.getWritableDatabase();
            long eliminado=baseDeDatos.delete("propietarios","id=?",new String[]{String.valueOf(id)});
            if(eliminado==1){
                Intent i=new Intent(this, ListadoPropietariosActivity.class);
                startActivity(i);
            }else {
                Toast.makeText(this, "no se eliminó correctamente", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public boolean Exist(int id){
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"consultorioVeterinario",null,1);
        boolean exist=false;
        SQLiteDatabase baseDeDatos= admin.getReadableDatabase();
        Cursor fila= baseDeDatos.rawQuery("SELECT id FROM propietarios where id=?", new String[]{String.valueOf(id)});
        if(fila.moveToFirst()){
            exist=true;
        }
        fila.close();
        baseDeDatos.close();
        return exist;
    }
}