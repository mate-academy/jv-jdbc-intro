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

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book(1L, "Java Programming", new BigDecimal("29.99"));
        Book book1 = new Book(2L, "Java", new BigDecimal("30.25"));
        bookDao.create(book);
        bookDao.create(book1);

        Optional<Book> retrievedBook = bookDao.findById(book.getId());
        System.out.println(retrievedBook);

        book.setTitle("Advanced Java Programming");
        bookDao.update(book);

        List<Book> allBooks = bookDao.findAll();
        allBooks.forEach(System.out::println);

        bookDao.deleteById(book.getId());
    }
}
