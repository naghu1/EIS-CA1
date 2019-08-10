package com.example.eisebook;

public class Book {

    public String title, author, isbn, edition, publisher, date, url;
    public String details;


    public Book(String title, String author, String isbn, String edition, String publisher, String date, String url) {

        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.edition = edition;
        this.publisher = publisher;
        this.date = date;
        this.url = url;

        details = "Author : "+ author+"\n" +
                "ISBN : "+ isbn+"\n" +
                "Edition : "+ edition+"\n" +
                "Publisher : "+ publisher+"\n" +
                "Date : "+ date;
    }
}
