package mate.academy;

import mate.academy.models.Book;
import mate.academy.repository.BookRepository;
import mate.academy.repository.JdbcBookRepository;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BookRepository br = new JdbcBookRepository();
        Book gameOfThrones = new Book("Game of Thrones", new BigDecimal("19.99"));
        br.save(gameOfThrones);
        System.out.println(br.findAll());
    }
}
