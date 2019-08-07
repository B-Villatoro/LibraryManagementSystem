package com.smoothstack.lms.model;

import java.util.List;

public class Author {

    private String name;
    private List<Book> books;
    private String id;

    public Author(){}

    public Author(String name, List<Book> books, String id) {
        this.name = name;
        this.books = books;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}