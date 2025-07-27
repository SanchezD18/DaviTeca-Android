package com.daviteca.app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Método para el botón Libros
    public void onLibrosClick(View view) {
        Intent intent = new Intent(this, LibrosActivity.class);
        startActivity(intent);
    }

    // Método para el botón Reseñas
    public void onResenasClick(View view) {
        Toast.makeText(this, "Sección de Reseñas", Toast.LENGTH_SHORT).show();
        // Aquí puedes agregar la navegación a la pantalla de reseñas
    }

    // Método para el botón Leyendo
    public void onLeyendoClick(View view) {
        Toast.makeText(this, "Sección de Leyendo", Toast.LENGTH_SHORT).show();
        // Aquí puedes agregar la navegación a la pantalla de leyendo
    }

    // Método para el botón Salir
    public void onSalirClick(View view) {
        Toast.makeText(this, "¡Gracias por usar DaviTeca!", Toast.LENGTH_SHORT).show();
        finish(); // Cierra la aplicación
    }
} 