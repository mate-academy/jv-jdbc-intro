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
                new Book("The Garden of Eden", BigDecimal.valueOf(450)),
                new Book("Tropic of Cancer", BigDecimal.valueOf(550)),
                new Book("19Q4", BigDecimal.valueOf(450))
        );
        // add books to DB
        List<Book> createdBooks =
                books.stream()
                .map(bookDao::create)
                .toList();
        System.out.println(createdBooks);
        // find book by ID
        Book book = bookDao.findById(books.get(0).getId())
                .orElseThrow(() -> new RuntimeException("Book was not found"));
        System.out.println(book);
        // find all books
        List<Book> allBook = bookDao.findAll();
        System.out.println(allBook);
        // update book
        Book updatedBook = books.get(0);
        updatedBook.setPrice(BigDecimal.valueOf(1000));
        Book updatedBookFromDB = bookDao.update(updatedBook);
        System.out.println(updatedBook);
        // delete book
        System.out.println(bookDao.deleteById(books.get(0).getId()));
    }
}
