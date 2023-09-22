package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDaoImpl) injector.getInstance(BookDao.class);
        List<Book> books = List.of(
                new Book("Clean Code", BigDecimal.valueOf(250.00)),
                new Book("Effective Java", BigDecimal.valueOf(500.99)),
                new Book("Witcher", BigDecimal.valueOf(300.00)),
                new Book("Witcher 3", BigDecimal.valueOf(1299.99)));

        List<Book> createdBooks = books.stream()
                .map(bookDao::create)
                .toList();

        Book book = bookDao.findById(createdBooks.get(1).getId())
                .orElseThrow(() -> new RuntimeException("Book was not found"));
        System.out.println(book);

        bookDao.findAll().forEach(System.out::println);

        book.setTitle("Harry Potter");
        Book updateBook = bookDao.update(book);
        System.out.println(updateBook);

        bookDao.deleteById(createdBooks.get(0).getId());
    }
}
