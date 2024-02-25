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

        Book book = new Book(null, "New book", BigDecimal.valueOf(500));
        book = bookDao.create(book);
        System.out.println(book);

        Optional<Book> bookOptional = bookDao.findById(2L);
        System.out.println(bookOptional);

        List<Book> books = bookDao.findAll();
        System.out.println(books.toString());

        Book book2 = new Book(2L, "New second book", BigDecimal.valueOf(500));
        book = bookDao.update(book2);
        System.out.println(book);

        books = bookDao.findAll();
        System.out.println(books.toString());

        Long maxId = books
                .stream()
                .map(Book::getId)
                .max(Long::compare)
                .orElse(0L);

        boolean isRowDeleted = bookDao.deleteById(maxId);
        System.out.println(maxId + " " + isRowDeleted);
        books = bookDao.findAll();
        System.out.println(books.toString());
    }
}
