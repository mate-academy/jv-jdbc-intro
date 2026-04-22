package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.entities.Book;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Banan");
        book.setPrice(BigDecimal.valueOf(200));
        // initialize field values using setters or constructor
        bookDao.create(book);

        bookDao.deleteById(1L);

        List<Book> bookList;
        bookList = bookDao.findAll();
        for (Book currentBook : bookList) {
            System.out.println(currentBook.getTitle());
        }

        bookDao.update(new Book(2L, "Tracer", BigDecimal.valueOf(100)));

    }
}
