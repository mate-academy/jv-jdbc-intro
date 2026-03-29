package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        System.out.println("Created HeadFirstDesignPatterns book");
        System.out.println(bookDao.create(
                new Book(null, "HeadFirstDesignPatterns", new BigDecimal("64.99"))));

        System.out.println("Created GoF book");
        Book gofBook = bookDao.create(new Book(null, "GoF", new BigDecimal("39.99")));
        System.out.println(gofBook);

        System.out.println("Found GoF book by id");
        System.out.println(bookDao.findById(gofBook.id()));

        System.out.println("All books now: ");
        bookDao.findAll()
                .forEach(System.out::println);

        System.out.println("Updated GoF book price from $39.99 to $24.99");
        Book cheaperGoFBook = bookDao
                .update(new Book(gofBook.id(), gofBook.title(), new BigDecimal("24.99")));

        System.out.println("Comparing variables with different versions of GoF book");
        System.out.println(gofBook);
        System.out.println(cheaperGoFBook);

        System.out.println("All books now: ");
        bookDao.findAll()
                .forEach(System.out::println);

        System.out.println("Result of deleting GoF book from DB by id");
        System.out.println(bookDao.deleteById(gofBook.id()));

        System.out.println("All books now: ");
        bookDao.findAll()
                .forEach(System.out::println);

    }
}
