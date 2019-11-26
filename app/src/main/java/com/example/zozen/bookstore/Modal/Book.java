package com.example.zozen.bookstore.Modal;

public class Book {
    private String picUrlBook;
    private String nameBook;
    private Double priceBook;
    private String detailBook;
    private String authorBook;
    private String idBook;

    public Book(String picUrlBook, String nameBook, Double priceBook, String detailBook, String authorBook, String idBook) {
        this.picUrlBook = picUrlBook;
        this.nameBook = nameBook;
        this.priceBook = priceBook;
        this.detailBook = detailBook;
        this.authorBook = authorBook;
        this.idBook = idBook;
    }

    public Book(String nameBook, Double priceBook, String detailBook) {
        this.picUrlBook = "";
        this.nameBook = nameBook;
        this.priceBook = priceBook;
        this.detailBook = detailBook;
    }

    public String getPicUrlBook() {
        return picUrlBook;
    }

    public String getNameBook() {
        return nameBook;
    }

    public Double getPriceBook() {
        return priceBook;
    }

    public String getDetailBook() {
        return detailBook;
    }

    public String getAuthorBook() { return  authorBook; }

    public String getIdBook() { return  idBook; }

    @Override
    public String toString() {
        return getNameBook() + " " + getPriceBook();
    }
}

