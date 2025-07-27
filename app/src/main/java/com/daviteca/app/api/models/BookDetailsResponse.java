package com.daviteca.app.api.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BookDetailsResponse {
    @SerializedName("key")
    private String key;
    
    @SerializedName("title")
    private String title;
    
    @SerializedName("authors")
    private List<Author> authors;
    
    @SerializedName("description")
    private Description description;
    
    @SerializedName("first_publish_date")
    private String firstPublishDate;
    
    @SerializedName("covers")
    private List<Integer> covers;
    
    @SerializedName("subjects")
    private List<String> subjects;
    
    @SerializedName("excerpts")
    private List<Excerpt> excerpts;
    
    public String getKey() {
        return key;
    }
    
    public String getTitle() {
        return title;
    }
    
    public List<Author> getAuthors() {
        return authors;
    }
    
    public Description getDescription() {
        return description;
    }
    
    public String getFirstPublishDate() {
        return firstPublishDate;
    }
    
    public List<Integer> getCovers() {
        return covers;
    }
    
    public List<String> getSubjects() {
        return subjects;
    }
    
    public List<Excerpt> getExcerpts() {
        return excerpts;
    }
    
    public String getAuthorDisplay() {
        if (authors != null && !authors.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < authors.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(authors.get(i).getName());
            }
            return sb.toString();
        }
        return "Autor desconocido";
    }
    
    public String getDescriptionText() {
        if (description != null && description.getValue() != null) {
            return description.getValue();
        }
        return "Sin descripción disponible";
    }
    
    public String getCoverUrl() {
        if (covers != null && !covers.isEmpty()) {
            return "https://covers.openlibrary.org/b/id/" + covers.get(0) + "-L.jpg";
        }
        return null;
    }
    
    public static class Author {
        @SerializedName("key")
        private String key;
        
        @SerializedName("name")
        private String name;
        
        public String getKey() {
            return key;
        }
        
        public String getName() {
            return name;
        }
    }
    
    public static class Description {
        @SerializedName("type")
        private String type;
        
        @SerializedName("value")
        private String value;
        
        public String getType() {
            return type;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    public static class Excerpt {
        @SerializedName("type")
        private String type;
        
        @SerializedName("value")
        private String value;
        
        public String getType() {
            return type;
        }
        
        public String getValue() {
            return value;
        }
    }
} 