package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book newBook = new Book(1L,
                "Harry Potter and the Prisoner of Javazkaban",
                new BigDecimal("19.99"));
        bookDao.create(newBook);

        Optional<Book> bookFound = bookDao.findById(newBook.getId());
        bookFound.ifPresent(book -> System.out.println("Book found: " + book.getTitle()));

        newBook.setPrice(new BigDecimal("20.99"));
        bookDao.update(newBook);

        bookDao.findAll().forEach(book -> System.out.println("Books found: " + book.getTitle()));

        bookDao.removeById(newBook.getId());
    }
}
