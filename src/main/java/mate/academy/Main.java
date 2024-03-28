package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.models.Book;
import mate.academy.repository.BookRepository;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookRepository br = (BookRepository) injector.getInstance(BookRepository.class);
        System.out.println("Initial data");
        System.out.println(br.findAll());
        Book gameOfThrones = new Book("Game of Thrones", new BigDecimal("19.99"));
        Book saved = br.save(gameOfThrones);
        System.out.println("Data after saving");
        System.out.println(br.findAll());
        saved.setPrice(new BigDecimal("25.99"));
        System.out.println("Data after updating");
        br.updateById(saved);
        System.out.println(br.findAll());
        System.out.println("Data after deleting");
        br.deleteById(saved.getId());
        System.out.println(br.findAll());
    }
}
