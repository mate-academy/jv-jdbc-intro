package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        //create operation
        Book book = new Book();
        book.setTitle("A Dog Called Money");
        book.setPrice(BigDecimal.valueOf(1100));
        Book bookFromDb = bookDao.create(book);

        //findById
        Book byId = bookDao.findById(bookFromDb.getId())
                .orElseThrow(() ->
                        new NoSuchElementException("No book with id: " + bookFromDb.getId()));

        //findAll
        List<Book> booksFromDB = bookDao.findAll();
        assert (!booksFromDB.isEmpty());

        //update operation
        bookFromDb.setPrice(BigDecimal.valueOf(999));
        Book updatedPrice = bookDao.update(bookFromDb);

        //deleteById
        boolean isDeleted = bookDao.deleteById(bookFromDb.getId());
        assert (isDeleted);

    }
}
