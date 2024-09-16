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
        List<Book> books = List.of(
                new Book("Can't Hurt Me: Master Your Mind and Defy the Odds", new BigDecimal("8.99")),
                new Book("Atomic Habits", new BigDecimal("25.99")),
                new Book("The Great Gatsby", new BigDecimal("15.00"))
        );

        for (Book book: books) {
            bookDao.create(book);
        }

        List<Book> getBooksFromDB = bookDao.findAll();

        System.out.println(getBooksFromDB);

        Optional<Book> optionalBookFromDb = bookDao.findById(getBooksFromDB.get(2).getId());

        Book bookFromDb = getBooksFromDB.get(2);
        bookFromDb.setPrice(new BigDecimal("40.99"));
        bookFromDb.setTitle("Head First Java: A Brain-Friendly Guide");

        bookDao.update(bookFromDb);

        bookDao.deleteById(books.get(1).getId());
    }
}
