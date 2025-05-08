package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("First-book");
        book.setPrice(BigDecimal.valueOf(225));

        Book updatedBook = new Book();
        updatedBook.setId(2L);
        updatedBook.setTitle("Updated-book");
        updatedBook.setPrice(BigDecimal.valueOf(225));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println(bookDao.create(book) + " book created");
        System.out.println(bookDao.findById(2L).get() + " found book by id" );
        System.out.println(bookDao.findAll() + " found all books");
        System.out.println(bookDao.update(updatedBook) + " updated book by id");
        System.out.println(bookDao.create(book) + " book created");
        System.out.println(bookDao.deleteById(2L) + " deleted book by id");
    }
}
