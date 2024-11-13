package mate.academy;

import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.services.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Red Book");
        book.setPrice(50);

        bookDao.create(book);
        bookDao.findById(1L);

        Book updateBook = new Book();
        updateBook.setTitle("Ice");

        bookDao.update(updateBook);

        List<Book> allBooks = bookDao.findAll();

        bookDao.deleteById(1L);

    }
}
