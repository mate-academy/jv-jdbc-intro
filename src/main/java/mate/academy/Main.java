package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final int BOOK_INDEX = 0;
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        BookDao bookDao = (BookDaoImpl) injector.getInstance(BookDao.class);

        List<Book> books = List.of(
                new Book(1L,"The Garden of Eden", BigDecimal.valueOf(450)),
                new Book(2L,"Tropic of Cancer", BigDecimal.valueOf(550)),
                new Book(3L,"19Q4", BigDecimal.valueOf(450))
        );
        List<Book> createdBooks =
                books.stream()
                .map(bookDao::create)
                .toList();
        System.out.println(createdBooks);
        Book book = bookDao.findById(books.get(BOOK_INDEX).getId())
                .orElseThrow(() -> new RuntimeException("Book was not found"));
        System.out.println(book);
        List<Book> allBook = bookDao.findAll();
        System.out.println(allBook);
        Book updatedBook = books.get(BOOK_INDEX);
        updatedBook.setPrice(BigDecimal.valueOf(1000));
        Book updatedBookFromDB = bookDao.update(updatedBook);
        System.out.println(updatedBookFromDB);
        System.out.println(bookDao.deleteById(books.get(BOOK_INDEX).getId()));
    }
}
