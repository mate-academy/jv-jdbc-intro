package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final Long ID_FOR_BOOK_1 = 1L;
    private static final Long ID_FOR_BOOK_2 = 2L;
    private static final String FIRST_TITLE = "First title";
    private static final String SECOND_TITLE = "Second title";
    private static final String THIRD_TITLE = "Third title";
    private static final BigDecimal FIRST_PRICE = BigDecimal.valueOf(100.00);
    private static final BigDecimal SECOND_PRICE = BigDecimal.valueOf(150.00);
    private static final BigDecimal THIRD_PRICE = BigDecimal.valueOf(200.00);
    private static final String BOOK_UNSPECIFIED_MESSAGE = "The book is not specified";
    private static final String DELETED_BOOK_MESSAGE
            = "The book by id " + ID_FOR_BOOK_1 + " was deleted";

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book(FIRST_TITLE, FIRST_PRICE);
        Book book2 = new Book(SECOND_TITLE, SECOND_PRICE);
        Book book3 = new Book(THIRD_TITLE, THIRD_PRICE);
        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);
        Optional<Book> bookById = bookDao.findById(ID_FOR_BOOK_2);
        List<Book> bookList = bookDao.findAll();
        Book updatedBook = bookDao.update(book3);
        final boolean isDeleted = bookDao.deleteById(ID_FOR_BOOK_1);

        if (bookById.isPresent()) {
            System.out.println(bookById.get());
        } else {
            System.out.println(BOOK_UNSPECIFIED_MESSAGE);
        }

        for (Book book : bookList) {
            System.out.println(book);
        }

        System.out.println(updatedBook);

        if (isDeleted) {
            System.out.println(DELETED_BOOK_MESSAGE);
        }
    }
}
