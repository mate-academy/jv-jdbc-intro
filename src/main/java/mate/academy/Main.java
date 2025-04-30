package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("createBook");
        book.setPrice(new BigDecimal(45));
        bookDao.create(book);

        bookDao.findById(book.getId());

        List<Book> bookDaoAll = bookDao.findAll();

        Book bookUpd = bookDaoAll.get(0);
        bookUpd.setTitle("updateBookDao");
        bookUpd.setPrice(new BigDecimal(300));
        bookDao.update(bookUpd);

        bookDao.deleteById(1L);
    }
}
