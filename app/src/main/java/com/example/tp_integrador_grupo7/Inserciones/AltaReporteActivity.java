package com.example.tp_integrador_grupo7.Inserciones;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_integrador_grupo7.AdminSQLiteOpenHelper;
import com.example.tp_integrador_grupo7.R;
import com.example.tp_integrador_grupo7.datos.DataReportes;
import com.example.tp_integrador_grupo7.datos.DataTratamientos;
import com.example.tp_integrador_grupo7.entidades.Citas;
import com.example.tp_integrador_grupo7.entidades.Propietarios;
import com.example.tp_integrador_grupo7.entidades.Reporte;

import java.sql.Date;
import java.util.ArrayList;

public class AltaReporteActivity extends AppCompatActivity {
    private EditText etDiagnostico, etHallazgos, etfecha;
    private TextView twIdcita, twDiagnostico, twHallazgos, twFecha;
    private Button btnSave;
    private Spinner spinnerIdCita;
    private DataReportes data = new DataReportes(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_reporte);
        innitVars();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {guardarReportes();}
        });
        ArrayList<Citas> listaCitas = mostrarCitas();

        if(listaCitas!=null) {
            ArrayAdapter<Citas> arrayAdapter = new ArrayAdapter<Citas>(getApplicationContext(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaCitas);
            spinnerIdCita.setAdapter(arrayAdapter);
        }
    }

    private void guardarReportes(){
    Reporte rep = new Reporte();
    rep.setDiagnostico(etDiagnostico.getText().toString());
    rep.setFecha(Date.valueOf(etfecha.getText().toString()));
    rep.setHallazgos(etHallazgos.getText().toString());
    rep.setIdCita(Integer.parseInt(spinnerIdCita.getSelectedItem().toString()));

    if (rep.getDiagnostico().isEmpty() || rep.getFecha().toString().isEmpty() || rep.getHallazgos().isEmpty()){
        Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        return;
        }
     data.insertarReporte(rep);
    }

    private void innitVars(){
        spinnerIdCita = findViewById(R.id.spinnerIdCita);
        etDiagnostico = findViewById(R.id.etDiagnostico);
        etHallazgos = findViewById(R.id.etHallazgos);
        etfecha = findViewById(R.id.etfecha);
        twIdcita = findViewById(R.id.twIdcita);
        twDiagnostico = findViewById(R.id.twDiagnostico);
        twHallazgos = findViewById(R.id.twFecha);
        btnSave = findViewById(R.id.btnSave);
    }

    public ArrayList<Citas> mostrarCitas(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "consultorioVeterinario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ArrayList<Citas> listaCitas = new ArrayList<Citas>();

        try {
            Cursor filas = bd.rawQuery("SELECT id, motivo FROM citas", null);
            if(filas.moveToFirst()){
                do{
                    Citas citas = new Citas();
                    citas.setId(filas.getInt(0));
                    citas.setMotivo(filas.getString(1));
                    listaCitas.add(citas);
                } while (filas.moveToNext());
            } else {
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return listaCitas;
    }

}
