package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book firstBook = new Book();
        Book secondBook = new Book();
        firstBook.setTitle("The Adventures of Tom");
        firstBook.setPrice(BigDecimal.valueOf(300));
        secondBook.setTitle("Kobzar");
        secondBook.setPrice(BigDecimal.valueOf(400));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(firstBook);
        bookDao.create(secondBook);

        Optional<Book> bookById = bookDao.findById(2L);
        System.out.println(bookById + "\n");
        List<Book> allBooks = bookDao.findAll();
        allBooks.forEach(System.out::println);
        System.out.println();

        firstBook.setPrice(BigDecimal.valueOf(350));
        bookDao.update(firstBook);
        bookDao.deleteById(2L);
        allBooks = bookDao.findAll();
        allBooks.forEach(System.out::println);
    }
}
