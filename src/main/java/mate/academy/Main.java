package mate.academy;

import mate.academy.dao.BookDao;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Injector;
import java.math.BigDecimal;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = new Book(null, "Clean Code", new BigDecimal("19.99"));
        bookDao.create(newBook);

        Optional<Book> foundBook = bookDao.findById(newBook.getId());
        foundBook.ifPresent(System.out::println);

        List<Book> books = bookDao.findAll();
        books.forEach(System.out::println);

        newBook.setPrice(new BigDecimal("17.99"));
        bookDao.update(newBook);
        System.out.println(newBook);

        boolean isDeleted = bookDao.deleteById(newBook.getId());
        System.out.println("Deleted: " + isDeleted);
    }
}
