package com.daviteca.app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class LibrosActivity extends AppCompatActivity {
    
    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private LibrosAdapter adapter;
    private List<Libro> librosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros);
        
        // Inicializar la base de datos
        dbHelper = new DatabaseHelper(this);
        
        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewLibros);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columnas para simular estanterías
        
        // Cargar libros
        cargarLibros();
        
        // Configurar botón de volver
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        // Configurar botón de búsqueda en línea
        Button btnBuscarOnline = findViewById(R.id.btnBuscarOnline);
        btnBuscarOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibrosActivity.this, BuscarLibrosActivity.class);
                startActivity(intent);
            }
        });
        
        // Configurar botón de agregar libro
        Button btnAgregarLibro = findViewById(R.id.btnAgregarLibro);
        btnAgregarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoAgregarLibro();
            }
        });
    }
    
    private void cargarLibros() {
        librosList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        
        Cursor cursor = db.query(
            "libros",
            null,
            null,
            null,
            null,
            null,
            "titulo ASC"
        );
        
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("id");
            int tituloIndex = cursor.getColumnIndex("titulo");
            int autorIndex = cursor.getColumnIndex("autor");
            int editorialIndex = cursor.getColumnIndex("editorial");
            int disponibleIndex = cursor.getColumnIndex("disponible");
            
            if (idIndex != -1 && tituloIndex != -1 && autorIndex != -1 && 
                editorialIndex != -1 && disponibleIndex != -1) {
                
                Libro libro = new Libro(
                    cursor.getInt(idIndex),
                    cursor.getString(tituloIndex),
                    cursor.getString(autorIndex),
                    cursor.getString(editorialIndex),
                    cursor.getInt(disponibleIndex) == 1
                );
                librosList.add(libro);
            }
        }
        
        cursor.close();
        db.close();
        
        // Configurar adapter
        adapter = new LibrosAdapter(librosList, this);
        recyclerView.setAdapter(adapter);
    }
    
    private void mostrarDialogoAgregarLibro() {
        // Crear un diálogo simple para agregar libro
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Agregar Nuevo Libro");
        
        // Crear layout para el diálogo
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);
        
        final android.widget.EditText etTitulo = new android.widget.EditText(this);
        etTitulo.setHint("Título del libro");
        layout.addView(etTitulo);
        
        final android.widget.EditText etAutor = new android.widget.EditText(this);
        etAutor.setHint("Autor");
        layout.addView(etAutor);
        
        final android.widget.EditText etEditorial = new android.widget.EditText(this);
        etEditorial.setHint("Editorial");
        layout.addView(etEditorial);
        
        builder.setView(layout);
        
        builder.setPositiveButton("Agregar", (dialog, which) -> {
            String titulo = etTitulo.getText().toString().trim();
            String autor = etAutor.getText().toString().trim();
            String editorial = etEditorial.getText().toString().trim();
            
            if (!titulo.isEmpty() && !autor.isEmpty() && !editorial.isEmpty()) {
                agregarLibro(titulo, autor, editorial);
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
        
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
        
        builder.show();
    }
    
    private void agregarLibro(String titulo, String autor, String editorial) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put("titulo", titulo);
        values.put("autor", autor);
        values.put("editorial", editorial);
        values.put("disponible", 1);
        
        long newRowId = db.insert("libros", null, values);
        db.close();
        
        if (newRowId != -1) {
            Toast.makeText(this, "Libro agregado exitosamente", Toast.LENGTH_SHORT).show();
            // Recargar la lista
            cargarLibros();
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Error al agregar el libro", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
    
    // Getter para el DatabaseHelper
    public DatabaseHelper getDbHelper() {
        return dbHelper;
    }
    
    // Clase para representar un libro
    public static class Libro {
        private int id;
        private String titulo;
        private String autor;
        private String editorial;
        private boolean disponible;
        
        public Libro(int id, String titulo, String autor, String editorial, boolean disponible) {
            this.id = id;
            this.titulo = titulo;
            this.autor = autor;
            this.editorial = editorial;
            this.disponible = disponible;
        }
        
        // Getters
        public int getId() { return id; }
        public String getTitulo() { return titulo; }
        public String getAutor() { return autor; }
        public String getEditorial() { return editorial; }
        public boolean isDisponible() { return disponible; }
        
        // Setters
        public void setDisponible(boolean disponible) { this.disponible = disponible; }
    }
    

} 