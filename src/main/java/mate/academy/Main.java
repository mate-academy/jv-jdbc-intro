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
                new Book("the witcher Last wish", new BigDecimal("210.3")),
                new Book("Lord of the rings", new BigDecimal("180.2")),
                new Book("Hobbit", new BigDecimal("200.5")));
        for (Book book : books) {
            bookDao.create(book);
        }

        Optional<Book>
                optionalBookFromDb = bookDao.findById(books.get(2).getId());

        Book bookFromDb = optionalBookFromDb.get();
        bookFromDb.setPrice(new BigDecimal("122.5"));
        bookFromDb.setTitle("Harry Potter");

        bookDao.update(bookFromDb);

        bookDao.deleteById(books.get(1).getId());
    }
}
