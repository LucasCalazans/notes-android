package br.com.lucascalazans.notes;

import java.io.Serializable;

public class Note implements Serializable{

    private String key;
    private String title;
    private String description;
    private String date;

    public Note() {
        this.title = "";
        this.description = "";
        this.date = android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date()).toString();
    }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        this.date = android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date()).toString();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
