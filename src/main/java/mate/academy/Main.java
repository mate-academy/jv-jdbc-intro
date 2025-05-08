package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("My book", BigDecimal.valueOf(10.50));
        Book myBook = bookDao.create(book);
        Optional<Book> byId = bookDao.findById(myBook.getId());
        Book updatedBook = byId.get();
        updatedBook.setTitle("My updated book");
        bookDao.update(updatedBook);
        System.out.println(bookDao.findAll().toString());
        System.out.println(bookDao.deleteById(updatedBook.getId()));
    }
}
