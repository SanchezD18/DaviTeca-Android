package com.daviteca.app.api;

import com.daviteca.app.api.models.BookSearchResponse;
import com.daviteca.app.api.models.BookDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenLibraryService {
    
    // Buscar libros por título, autor, etc.
    @GET("search.json")
    Call<BookSearchResponse> searchBooks(@Query("q") String query);
    
    // Obtener detalles de un libro específico por ID
    @GET("works/{workId}.json")
    Call<BookDetailsResponse> getBookDetails(@Path("workId") String workId);
    
    // Buscar libros por autor
    @GET("search.json")
    Call<BookSearchResponse> searchBooksByAuthor(@Query("author") String author);
    
    // Buscar libros por título
    @GET("search.json")
    Call<BookSearchResponse> searchBooksByTitle(@Query("title") String title);
} 