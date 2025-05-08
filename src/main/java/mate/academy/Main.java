package mate.academy;

import dao.BookDao;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Injector;
import model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = List.of(
                new Book("To Kill a Mockingbird", new BigDecimal("11.3")),
                new Book("Pride and Prejudice", new BigDecimal("18.2")),
                new Book("The Great Gatsby", new BigDecimal("20.5")));
        for (Book book : books) {
            bookDao.create(book);
        }

        Optional<Book>
                optionalBookFromDb = bookDao.findById(books.get(2).getId());

        Book bookFromDb = optionalBookFromDb.get();
        bookFromDb.setPrice(new BigDecimal("12222.25"));
        bookFromDb.setTitle("The Shadow's Embrace");

        bookDao.update(bookFromDb);

        bookDao.deleteById(books.get(1).getId());
    }
}
