package mate.academy;

import java.math.BigDecimal;
import mate.academy.book.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector
            .getInstance("mate.academy.lib");
    private static final BookDao dao = (BookDao) injector.getInstance(BookDao.class);
    private static final BigDecimal BIG_DECIMAL = new BigDecimal(8855);
    private static final Book BOOK_TO_CREATE = new Book("title", BIG_DECIMAL);
    private static final Book BOOK_TO_UPDATE = new Book(2L,"name", BIG_DECIMAL);

    public static void main(String[] args) {
        dao.create(BOOK_TO_CREATE);
        dao.deleteById(3L);
        dao.update(BOOK_TO_UPDATE);
        dao.findById(1L);
        dao.findAll();
    }
}
