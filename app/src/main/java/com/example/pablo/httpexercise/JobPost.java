package com.example.pablo.httpexercise;

public class JobPost {
    private int id;
    private String title;
    private String postDate;
    private String description;

    public JobPost() {
        id = 0;
        title = "";
        postDate = "";
        description = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
