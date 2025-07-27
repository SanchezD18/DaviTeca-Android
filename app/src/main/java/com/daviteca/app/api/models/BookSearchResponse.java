package com.daviteca.app.api.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BookSearchResponse {
    @SerializedName("numFound")
    private int numFound;
    
    @SerializedName("start")
    private int start;
    
    @SerializedName("docs")
    private List<BookSearchResult> docs;
    
    public int getNumFound() {
        return numFound;
    }
    
    public int getStart() {
        return start;
    }
    
    public List<BookSearchResult> getDocs() {
        return docs;
    }
    
    public static class BookSearchResult {
        @SerializedName("key")
        private String key;
        
        @SerializedName("title")
        private String title;
        
        @SerializedName("author_name")
        private List<String> authorName;
        
        @SerializedName("first_publish_year")
        private Integer firstPublishYear;
        
        @SerializedName("cover_i")
        private Integer coverId;
        
        @SerializedName("edition_count")
        private Integer editionCount;
        
        @SerializedName("publisher")
        private List<String> publisher;
        
        @SerializedName("language")
        private List<String> language;
        
        @SerializedName("isbn")
        private List<String> isbn;
        
        public String getKey() {
            return key;
        }
        
        public String getTitle() {
            return title;
        }
        
        public List<String> getAuthorName() {
            return authorName;
        }
        
        public Integer getFirstPublishYear() {
            return firstPublishYear;
        }
        
        public Integer getCoverId() {
            return coverId;
        }
        
        public Integer getEditionCount() {
            return editionCount;
        }
        
        public List<String> getPublisher() {
            return publisher;
        }
        
        public List<String> getLanguage() {
            return language;
        }
        
        public List<String> getIsbn() {
            return isbn;
        }
        
        public String getAuthorDisplay() {
            if (authorName != null && !authorName.isEmpty()) {
                return String.join(", ", authorName);
            }
            return "Autor desconocido";
        }
        
        public String getPublisherDisplay() {
            if (publisher != null && !publisher.isEmpty()) {
                return String.join(", ", publisher);
            }
            return "Editorial desconocida";
        }
        
        public String getCoverUrl() {
            if (coverId != null) {
                return "https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";
            }
            return null;
        }
    }
} 