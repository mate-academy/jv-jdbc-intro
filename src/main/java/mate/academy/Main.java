package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final List<Book> books = new ArrayList<>();
    private static final Integer BOOK_AMOUNT = 10;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        for (int i = 1; i < BOOK_AMOUNT + 1; i++) {
            books.add(new Book((long) i, "book" + i, BigDecimal.valueOf(i)));
        }
        for (Book book : books) {
            bookDao.create(book);
        }
        Book bookToUpdate = books.get(3);
        bookToUpdate.setPrice(BigDecimal.valueOf(666));
        bookToUpdate.setTitle("Clean Code");
        bookDao.update(bookToUpdate);
        Optional<Book> byId = bookDao.findById(4L);
        List<Book> allBooks = bookDao.findAll();
        boolean deleteById = bookDao.deleteById(2L);
    }
}
