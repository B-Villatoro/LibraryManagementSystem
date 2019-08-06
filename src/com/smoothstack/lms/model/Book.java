package com.smoothstack.lms.model;

public class Book {

    private String title;
    private String isbn;
    private String publisherId;
    private String authorId;

    public Book(String title, String isbn,String authorId,String publisherId) {
        this.title = title;
        this.isbn = isbn;
        this.authorId = authorId;
        this.publisherId = publisherId;

    }
    public Book(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }


}
