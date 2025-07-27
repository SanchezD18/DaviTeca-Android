package com.daviteca.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "daviteca.db";
    private static final int DATABASE_VERSION = 1;
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LIBROS_TABLE = "CREATE TABLE libros (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo TEXT NOT NULL," +
                "autor TEXT NOT NULL," +
                "editorial TEXT NOT NULL," +
                "disponible INTEGER DEFAULT 1)";
        
        db.execSQL(CREATE_LIBROS_TABLE);
        
        // Insertar algunos libros de ejemplo
        insertarLibrosEjemplo(db);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS libros");
        onCreate(db);
    }
    
    private void insertarLibrosEjemplo(SQLiteDatabase db) {
        String[][] librosEjemplo = {
            {"Don Quijote", "Miguel de Cervantes", "Editorial Real"},
            {"Cien años de soledad", "Gabriel García Márquez", "Editorial Sudamericana"},
            {"El señor de los anillos", "J.R.R. Tolkien", "Allen & Unwin"},
            {"1984", "George Orwell", "Secker & Warburg"},
            {"El principito", "Antoine de Saint-Exupéry", "Reynal & Hitchcock"},
            {"Harry Potter y la piedra filosofal", "J.K. Rowling", "Bloomsbury"},
            {"Los miserables", "Victor Hugo", "A. Lacroix"},
            {"Orgullo y prejuicio", "Jane Austen", "T. Egerton"}
        };
        
        for (String[] libro : librosEjemplo) {
            ContentValues values = new ContentValues();
            values.put("titulo", libro[0]);
            values.put("autor", libro[1]);
            values.put("editorial", libro[2]);
            values.put("disponible", 1);
            db.insert("libros", null, values);
        }
    }
} 