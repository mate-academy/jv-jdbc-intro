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
                new Book("Clean Code", BigDecimal.valueOf(200.00)),
                new Book("Effective Java", BigDecimal.valueOf(799.99)),
                new Book("Head First Java", BigDecimal.valueOf(600.50)),
                new Book("Thinking in Java", BigDecimal.valueOf(1299.99))
        );

        // insert books to DB
        List<Book> createdBooks = books.stream()
                .map(bookDao::create)
                .toList();

        // get book by id
        Book book = bookDao.findById(createdBooks.get(1).getId())
                .orElseThrow(() -> new RuntimeException("Book was not found"));
        System.out.println(book);

        // get all books
        bookDao.findAll().forEach(System.out::println);

        // update book
        book.setTitle("Harry Potter");
        Book updateBook = bookDao.update(book);
        System.out.println(updateBook);

        // delete first book from list
        bookDao.deleteById(createdBooks.get(0).getId());
    }
}
