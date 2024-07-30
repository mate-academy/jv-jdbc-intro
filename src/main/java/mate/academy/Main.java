package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = new ArrayList<>();
        books.add(new Book("Harry Potter", new BigDecimal(1200)));
        books.add(new Book("Star Wars", new BigDecimal(1100)));
        books.add(new Book("Dune", new BigDecimal(1700)));

        books.forEach(bookDao::create);
        Book book = bookDao.findById(1L).orElseThrow(() ->
                new RuntimeException("Couldn't find the book")
        );
        List<Book> bookList = bookDao.findAll();
        System.out.println(bookList);
        Book updatedBook = bookDao.update(new Book("Harry Potter", new BigDecimal(2500)));

        System.out.println(bookDao.deleteById(1L));
    }
}
