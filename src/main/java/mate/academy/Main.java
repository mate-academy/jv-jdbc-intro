package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final double FIRST_PRICE = 2023.00;
    private static final double SECOND_PRICE = 777.00;
    private static final String FIRST_BOOK_NAME = "Jdbc intro";
    private static final String SECOND_BOOK_NAME = "Jdbc for dummies";
    private static final Long BOOK_ID = 1L;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book myFirstBook = new Book();
        myFirstBook.setTitle(FIRST_BOOK_NAME);
        myFirstBook.setPrice(BigDecimal.valueOf(FIRST_PRICE));

        System.out.println("Create new book: " + bookDao.create(myFirstBook));
        System.out.println("Find all books: " + bookDao.findAll());

        Book bookById = bookDao.findById(BOOK_ID).get();
        System.out.println("Find book by id: " + bookById);

        bookById.setTitle(SECOND_BOOK_NAME);
        bookById.setPrice(BigDecimal.valueOf(SECOND_PRICE));
        System.out.println("Updated book: " + bookDao.update(bookById));

        System.out.println("Delete book with id: " + bookById.getId()
                + " result: " + bookDao.deleteById(bookById.getId()));
        System.out.println("Check DB after delete: " + bookDao.findAll());
    }
}
