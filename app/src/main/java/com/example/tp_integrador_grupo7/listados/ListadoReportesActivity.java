package com.example.tp_integrador_grupo7.listados;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp_integrador_grupo7.R;

public class ListadoReportesActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado_reportes);
    }
}
