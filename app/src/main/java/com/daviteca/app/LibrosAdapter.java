package com.daviteca.app;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LibrosAdapter extends RecyclerView.Adapter<LibrosAdapter.LibroViewHolder> {
    
    private List<LibrosActivity.Libro> librosList;
    private LibrosActivity activity;
    
    public LibrosAdapter(List<LibrosActivity.Libro> librosList, LibrosActivity activity) {
        this.librosList = librosList;
        this.activity = activity;
    }
    
    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_libro, parent, false);
        return new LibroViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int position) {
        LibrosActivity.Libro libro = librosList.get(position);
        holder.bind(libro);
    }
    
    @Override
    public int getItemCount() {
        return librosList.size();
    }
    
    class LibroViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitulo;
        private TextView tvAutor;
        private TextView tvEditorial;
        private TextView tvDisponible;
        private ImageView ivLibro;
        
        public LibroViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvEditorial = itemView.findViewById(R.id.tvEditorial);
            tvDisponible = itemView.findViewById(R.id.tvDisponible);
            ivLibro = itemView.findViewById(R.id.ivLibro);
            
            // Configurar click en el item
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mostrarDetallesLibro(librosList.get(position));
                }
            });
            
            // Configurar click largo para opciones
            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mostrarOpcionesLibro(librosList.get(position), position);
                }
                return true;
            });
        }
        
        public void bind(LibrosActivity.Libro libro) {
            tvTitulo.setText(libro.getTitulo());
            tvAutor.setText("Por: " + libro.getAutor());
            tvEditorial.setText(libro.getEditorial());
            
            if (libro.isDisponible()) {
                tvDisponible.setText("✅ Disponible");
                tvDisponible.setTextColor(activity.getResources().getColor(android.R.color.holo_green_dark));
            } else {
                tvDisponible.setText("❌ No disponible");
                tvDisponible.setTextColor(activity.getResources().getColor(android.R.color.holo_red_dark));
            }
            
            // Configurar imagen del libro (usando un drawable por defecto)
            // En el futuro se puede agregar campo para URL de portada en la BD
            ivLibro.setImageResource(R.drawable.ic_libro);
        }
        
        private void mostrarDetallesLibro(LibrosActivity.Libro libro) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Detalles del Libro");
            
            String detalles = "Título: " + libro.getTitulo() + "\n\n" +
                            "Autor: " + libro.getAutor() + "\n\n" +
                            "Editorial: " + libro.getEditorial() + "\n\n" +
                            "Estado: " + (libro.isDisponible() ? "Disponible ✅" : "No disponible ❌");
            
            builder.setMessage(detalles);
            builder.setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss());
            builder.show();
        }
        
        private void mostrarOpcionesLibro(LibrosActivity.Libro libro, int position) {
            String[] opciones = {"Cambiar disponibilidad", "Eliminar libro"};
            
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Opciones del libro");
            builder.setItems(opciones, (dialog, which) -> {
                switch (which) {
                    case 0:
                        cambiarDisponibilidad(libro, position);
                        break;
                    case 1:
                        eliminarLibro(libro, position);
                        break;
                }
            });
            builder.show();
        }
        
        private void cambiarDisponibilidad(LibrosActivity.Libro libro, int position) {
            DatabaseHelper dbHelper = activity.getDbHelper();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("disponible", libro.isDisponible() ? 0 : 1);
            
            int updatedRows = db.update("libros", values, "id = ?", new String[]{String.valueOf(libro.getId())});
            db.close();
            
            if (updatedRows > 0) {
                libro.setDisponible(!libro.isDisponible());
                notifyItemChanged(position);
                Toast.makeText(activity, 
                    libro.isDisponible() ? "Libro marcado como disponible" : "Libro marcado como no disponible", 
                    Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Error al cambiar la disponibilidad", Toast.LENGTH_SHORT).show();
            }
        }
        
        private void eliminarLibro(LibrosActivity.Libro libro, int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Confirmar eliminación");
            builder.setMessage("¿Estás seguro de que quieres eliminar '" + libro.getTitulo() + "'?");
            builder.setPositiveButton("Eliminar", (dialog, which) -> {
                // Eliminar de la base de datos
                DatabaseHelper dbHelper = activity.getDbHelper();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                int deletedRows = db.delete("libros", "id = ?", new String[]{String.valueOf(libro.getId())});
                db.close();
                
                if (deletedRows > 0) {
                    librosList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(activity, "Libro eliminado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Error al eliminar el libro", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
            builder.show();
        }
    }
} 