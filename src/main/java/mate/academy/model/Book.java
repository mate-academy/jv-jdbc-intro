package mate.academy.model;

import java.util.Date;
import java.util.Objects;

public class Book {
    private long book_id;
    private String title;
    private String author;
    private int publication_year;
    private String genre;
    private double price;

    public Book() {
    }

    public Book(long book_id, String title, String author, int publication_year, String genre, double price) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.publication_year = publication_year;
        this.genre = genre;
        this.price = price;
    }

    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return book_id == book.book_id && Double.compare(book.price, price) == 0
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author)
                && Objects.equals(publication_year, book.publication_year)
                && Objects.equals(genre, book.genre);
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publication_year=" + publication_year +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                '}';
    }
}
