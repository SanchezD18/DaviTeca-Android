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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.daviteca.app.api.models.BookSearchResponse;
import java.util.List;

public class BuscarLibrosAdapter extends RecyclerView.Adapter<BuscarLibrosAdapter.BookViewHolder> {
    
    private List<BookSearchResponse.BookSearchResult> librosList;
    private BuscarLibrosActivity activity;
    
    public BuscarLibrosAdapter(List<BookSearchResponse.BookSearchResult> librosList, BuscarLibrosActivity activity) {
        this.librosList = librosList;
        this.activity = activity;
    }
    
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_libro_busqueda, parent, false);
        return new BookViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookSearchResponse.BookSearchResult libro = librosList.get(position);
        holder.bind(libro);
    }
    
    @Override
    public int getItemCount() {
        return librosList.size();
    }
    
    class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitulo;
        private TextView tvAutor;
        private TextView tvEditorial;
        private TextView tvAnio;
        private ImageView ivPortada;
        
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvEditorial = itemView.findViewById(R.id.tvEditorial);
            tvAnio = itemView.findViewById(R.id.tvAnio);
            ivPortada = itemView.findViewById(R.id.ivPortada);
            
            // Configurar click en el item
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mostrarDetallesLibro(librosList.get(position));
                }
            });
            
            // Configurar click largo para agregar a biblioteca
            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mostrarOpcionesLibro(librosList.get(position));
                }
                return true;
            });
        }
        
        public void bind(BookSearchResponse.BookSearchResult libro) {
            tvTitulo.setText(libro.getTitle());
            tvAutor.setText("Por: " + libro.getAuthorDisplay());
            tvEditorial.setText(libro.getPublisherDisplay());
            
            if (libro.getFirstPublishYear() != null) {
                tvAnio.setText("Año: " + libro.getFirstPublishYear());
            } else {
                tvAnio.setText("Año: Desconocido");
            }
            
            // Cargar imagen de portada con Glide
            String coverUrl = libro.getCoverUrl();
            if (coverUrl != null) {
                Glide.with(activity)
                        .load(coverUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(R.drawable.ic_libro)
                        .error(R.drawable.ic_libro)
                        .into(ivPortada);
            } else {
                ivPortada.setImageResource(R.drawable.ic_libro);
            }
        }
        
        private void mostrarDetallesLibro(BookSearchResponse.BookSearchResult libro) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Detalles del Libro");
            
            StringBuilder detalles = new StringBuilder();
            detalles.append("Título: ").append(libro.getTitle()).append("\n\n");
            detalles.append("Autor: ").append(libro.getAuthorDisplay()).append("\n\n");
            detalles.append("Editorial: ").append(libro.getPublisherDisplay()).append("\n\n");
            
            if (libro.getFirstPublishYear() != null) {
                detalles.append("Año de publicación: ").append(libro.getFirstPublishYear()).append("\n\n");
            }
            
            if (libro.getEditionCount() != null) {
                detalles.append("Número de ediciones: ").append(libro.getEditionCount()).append("\n\n");
            }
            
            if (libro.getLanguage() != null && !libro.getLanguage().isEmpty()) {
                detalles.append("Idioma: ").append(String.join(", ", libro.getLanguage())).append("\n\n");
            }
            
            if (libro.getIsbn() != null && !libro.getIsbn().isEmpty()) {
                detalles.append("ISBN: ").append(String.join(", ", libro.getIsbn()));
            }
            
            builder.setMessage(detalles.toString());
            builder.setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss());
            builder.show();
        }
        
        private void mostrarOpcionesLibro(BookSearchResponse.BookSearchResult libro) {
            String[] opciones = {"Agregar a mi biblioteca"};
            
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Opciones del libro");
            builder.setItems(opciones, (dialog, which) -> {
                switch (which) {
                    case 0:
                        agregarABiblioteca(libro);
                        break;
                }
            });
            builder.show();
        }
        
        private void agregarABiblioteca(BookSearchResponse.BookSearchResult libro) {
            DatabaseHelper dbHelper = new DatabaseHelper(activity);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            
            ContentValues values = new ContentValues();
            values.put("titulo", libro.getTitle());
            values.put("autor", libro.getAuthorDisplay());
            values.put("editorial", libro.getPublisherDisplay());
            values.put("disponible", 1);
            
            long newRowId = db.insert("libros", null, values);
            db.close();
            
            if (newRowId != -1) {
                Toast.makeText(activity, "Libro agregado a tu biblioteca", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Error al agregar el libro", Toast.LENGTH_SHORT).show();
            }
        }
    }
} 