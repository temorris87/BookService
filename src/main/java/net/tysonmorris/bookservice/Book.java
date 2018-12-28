package net.tysonmorris.bookservice;

import org.springframework.data.annotation.Id;

public class Book {

    @Id
    private String id;

    private String isbn;
    private String title;
    private Person author;
    private String publisher;

    public Book(String isbn, String title, Person author, String publisher) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    public String getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Person getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return String.format(
                "{ \"id\" : \"%s\", \"title\" : \"%s\", \"author\" : \"%s\", \"publisher\" : \"%s\" }",
                this.id,
                this.title,
                this.author,
                this.publisher
        );
    }

}
