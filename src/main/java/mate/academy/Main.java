package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String BOOK_TITLE_EXAMPLE = "Lords of the Rings";
    private static final String BOOK_TITLE_UPDATE = "Harry Potter";
    private static final BigDecimal BOOK_PRICE_EXAMPLE = new BigDecimal("99.99");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle(BOOK_TITLE_EXAMPLE);
        book.setPrice(BOOK_PRICE_EXAMPLE);

        Book bookCreate = bookDao.create(book);
        System.out.println(bookCreate);

        Optional<Book> bookFindById = bookDao.findById(bookCreate.getId());
        System.out.println(bookFindById);

        List<Book> booksFindAll = bookDao.findAll();
        System.out.println(booksFindAll);

        book.setTitle(BOOK_TITLE_UPDATE);
        Book bookUpdate = bookDao.update(book);
        System.out.println(bookUpdate);

        boolean bookDelete = bookDao.deleteById(bookCreate.getId());
        System.out.println(bookDelete);
    }
}
