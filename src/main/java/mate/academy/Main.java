package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Book NEW_BOOK = new Book("title", BigDecimal.valueOf(12.99));
    private static final Book ANOTHER_NEW_BOOK = new Book(
            "Second title", BigDecimal.valueOf(15.99));
    private static final Book ANOTHER_NEW_BOOK_UPDATE = new Book(
            2L,"Updated second title", BigDecimal.valueOf(11.00)
    );
    private static final Book THIRD_BOOK = new Book(
            "It's a third book", BigDecimal.valueOf(10.00)
    );
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        bookDao.create(NEW_BOOK);

        bookDao.create(ANOTHER_NEW_BOOK);
        Long idSecondBook = ANOTHER_NEW_BOOK.getId();
        Optional<Book> secondBookById = bookDao.findById(idSecondBook);
        System.out.println(secondBookById.get());
        bookDao.update(ANOTHER_NEW_BOOK_UPDATE);
        Optional<Book> updatedSecondBook = bookDao.findById(idSecondBook);
        System.out.println(updatedSecondBook.get());

        bookDao.create(THIRD_BOOK);
        Long idThirdBook = THIRD_BOOK.getId();
        bookDao.deleteById(idThirdBook);

        List<Book> allBooks = bookDao.findAll();
        System.out.println(allBooks);
    }
}
