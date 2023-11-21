package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setId(1L);
        book.setTitle("FIRST_UPDATED");
        book.setPrice(350);
        bookDao.update(book);
        Book book2 = new Book();
        bookDao.deleteById(2L);
    }
}
