package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book newBook = new Book("Казнить нельзя, помиловать", BigDecimal.valueOf(100.00));
        System.out.println(bookDao.create(newBook));
        Optional<Book> findBook = bookDao.findById(2L);
        findBook.ifPresent(System.out::println);
        System.out.println(bookDao.findAll());
        Book updatedBook = bookDao.update(new Book(1L, BigDecimal.valueOf(70.25)));
        System.out.println(bookDao.create(newBook));
        if (bookDao.deleteById(3L)) {
            System.out.println("book with id = " + 2 + " was deleted");
        }

    }
}
