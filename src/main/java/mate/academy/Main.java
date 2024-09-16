package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book zapovit = new Book("zapovit", BigDecimal.valueOf(100));
        Book newZapiovit = bookDao.create(zapovit);

        Optional<Book> bookById2 = bookDao.findById(2L);

        List<Book> allBooks = bookDao.findAll();

        Book updateZapovit = bookDao.update(zapovit);

        boolean isDeleted = bookDao.deleteById(3L);
    }
}
