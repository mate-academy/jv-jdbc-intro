package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        Book book = new Book("GOT", BigDecimal.valueOf(140L));
        Book createdBook = bookDao.create(book);
        bookDao.findAll();
        bookDao.findById(createdBook.getId());
        Book updatedBook = new Book(createdBook.getId(), "Harry potter", BigDecimal.valueOf(300L));
        bookDao.update(updatedBook);
        bookDao.deleteById(createdBook.getId());
    }
}
