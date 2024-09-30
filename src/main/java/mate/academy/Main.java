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

        Book book = new Book();
        book.setTitle("Java Programming");
        book.setPrice(new BigDecimal("29.99"));
        bookDao.create(book);

        Optional<Book> retrievedBook = bookDao.findById(book.getId());
        System.out.println(retrievedBook);

        book.setTitle("Advanced Java Programming");
        bookDao.update(book);

        List<Book> allBooks = bookDao.findAll();
        allBooks.forEach(System.out::println);

        bookDao.deleteById(book.getId());
    }
}
