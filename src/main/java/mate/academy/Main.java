package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.Dao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        Book firstBook = new Book();
        firstBook.setTitle("Sherlok");
        firstBook.setPrice(BigDecimal.valueOf(2031));
        Book secondBook = new Book();
        secondBook.setTitle("Harry Potter");
        secondBook.setPrice(BigDecimal.valueOf(3000));

        BookDao bookDao = (BookDao) injector.getInstance(Dao.class);
        System.out.println("Insert Books");
        bookDao.create(firstBook);
        bookDao.create(secondBook);

        System.out.println("\nAll founded books");
        List<Book> books = bookDao.findAll();
        books.forEach(System.out::println);

        System.out.println("\nFind book by id = 1");
        Optional<Book> bookById = bookDao.findById(1L);
        System.out.println(bookById);

        System.out.println("\nUpdate Sherlok Book");
        bookById.get().setTitle("Sherlok 2");
        bookById.get().setPrice(BigDecimal.valueOf(3500));
        bookById = Optional.of(bookDao.update(bookById.get()));
        System.out.println(bookById);

        System.out.println("\nDelete Second Book");
        System.out.println(bookDao.deleteById(2L));
    }
}
