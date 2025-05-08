package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final Book BOOK_1_LOWER_PRICE
            = new Book("The Little Prince", new BigDecimal(149));
    private static final Book BOOK_1_HIGHER_PRICE
            = new Book(1L,"The Little Prince", new BigDecimal(249));
    private static final Book BOOK_2 = new Book("Pride and Prejudice", new BigDecimal(198));
    private static final Book BOOK_3 = new Book("Atomic habits", new BigDecimal(274));

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        bookDao.create(BOOK_1_LOWER_PRICE);
        bookDao.create(BOOK_2);
        bookDao.create(BOOK_3);

        bookDao.update(BOOK_1_HIGHER_PRICE);

        Optional<Book> findById = bookDao.findById(2L);
        System.out.println(findById);
        bookDao.deleteById(3L);

        List<Book> allBooks = bookDao.findAll();
        System.out.println(allBooks);
    }
}
