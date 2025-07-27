package com.daviteca.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.daviteca.app.api.ApiClient;
import com.daviteca.app.api.OpenLibraryService;
import com.daviteca.app.api.models.BookSearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class BuscarLibrosActivity extends AppCompatActivity {
    
    private EditText etBuscar;
    private Button btnBuscar;
    private Button btnVolver;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private BuscarLibrosAdapter adapter;
    private List<BookSearchResponse.BookSearchResult> librosEncontrados;
    private OpenLibraryService apiService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_libros);
        
        // Inicializar componentes
        etBuscar = findViewById(R.id.etBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnVolver = findViewById(R.id.btnVolver);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerViewBuscar);
        
        // Configurar RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        librosEncontrados = new ArrayList<>();
        adapter = new BuscarLibrosAdapter(librosEncontrados, this);
        recyclerView.setAdapter(adapter);
        
        // Inicializar API service
        apiService = ApiClient.getOpenLibraryService();
        
        // Configurar botones
        btnBuscar.setOnClickListener(v -> buscarLibros());
        btnVolver.setOnClickListener(v -> finish());
        
        // Configurar búsqueda al presionar Enter
        etBuscar.setOnEditorActionListener((v, actionId, event) -> {
            buscarLibros();
            return true;
        });
    }
    
    private void buscarLibros() {
        String query = etBuscar.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa un término de búsqueda", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Mostrar progreso
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        
        // Realizar búsqueda
        Call<BookSearchResponse> call = apiService.searchBooks(query);
        call.enqueue(new Callback<BookSearchResponse>() {
            @Override
            public void onResponse(Call<BookSearchResponse> call, Response<BookSearchResponse> response) {
                progressBar.setVisibility(View.GONE);
                
                if (response.isSuccessful() && response.body() != null) {
                    BookSearchResponse searchResponse = response.body();
                    librosEncontrados.clear();
                    
                    if (searchResponse.getDocs() != null) {
                        librosEncontrados.addAll(searchResponse.getDocs());
                    }
                    
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    
                    if (librosEncontrados.isEmpty()) {
                        Toast.makeText(BuscarLibrosActivity.this, 
                            "No se encontraron libros para: " + query, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BuscarLibrosActivity.this, 
                            "Se encontraron " + librosEncontrados.size() + " libros", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BuscarLibrosActivity.this, 
                        "Error al buscar libros", Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
            
            @Override
            public void onFailure(Call<BookSearchResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Toast.makeText(BuscarLibrosActivity.this, 
                    "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
} 