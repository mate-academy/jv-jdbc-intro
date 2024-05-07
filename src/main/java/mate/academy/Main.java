package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector
            .getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("New Book");
        book.setPrice(new BigDecimal(61));
        bookDao.create(book);

        bookDao.findById(book.getId());

        List<Book> findAll = bookDao.findAll();

        Book updateBook = findAll.get(0);
        updateBook.setTitle("Book part 2");
        updateBook.setPrice(new BigDecimal(35));
        bookDao.update(updateBook);

        bookDao.deleteById(updateBook.getId());
    }
}
