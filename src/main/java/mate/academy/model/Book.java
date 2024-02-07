package mate.academy.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Book {
    private Long idBook;
    private String titleBook;
    private BigDecimal priceBook;

    public Book() {
    }

    public Book(Long idBook, String titleBook, BigDecimal priceBook) {
        this.idBook = idBook;
        this.titleBook = titleBook;
        this.priceBook = priceBook;
    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public String getTitleBook() {
        return titleBook;
    }

    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
    }

    public BigDecimal getPriceBook() {
        return priceBook;
    }

    public void setPriceBook(BigDecimal priceBook) {
        this.priceBook = priceBook;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Book book = (Book) object;
        return Objects.equals(idBook, book.idBook)
                && Objects.equals(titleBook, book.titleBook)
                && Objects.equals(priceBook, book.priceBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBook, titleBook, priceBook);
    }

    @Override
    public String toString() {
        return "Book{"
                + "idBook=" + idBook
                + ", titleBook='" + titleBook
                + '\''
                + ", priceBook=" + priceBook
                + '}';
    }
}
