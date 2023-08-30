package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final String PACKAGE = "mate.academy";

    public static void main(String[] args) {
        Injector injector = Injector.getInstance(PACKAGE);
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        for (Book book : createBooks()) {
            bookDao.create(book);
        }
        List<Book> all = bookDao.findAll();
        Book bookForUpdating = new Book();
        bookForUpdating.setTitle("The Night in Lisbon");
        bookForUpdating.setPrice(new BigDecimal("120.00"));
        bookForUpdating.setId(all.get(0).getId());
        bookDao.update(bookForUpdating);
        bookDao.deleteById(all.get(0).getId());
        bookDao.findById(all.get(0).getId());
    }

    private static List<Book> createBooks() {
        List<Book> books = new ArrayList<>();
        Book firstBook = new Book();
        firstBook.setTitle("All Quiet on the Western Front");
        firstBook.setPrice(new BigDecimal("100.00"));
        books.add(firstBook);
        Book secondBook = new Book();
        secondBook.setTitle("Arch of Triumph");
        secondBook.setPrice(new BigDecimal("90.00"));
        books.add(secondBook);
        Book thirdBook = new Book();
        thirdBook.setTitle("The Black Obelisk");
        thirdBook.setPrice(new BigDecimal("95.00"));
        books.add(thirdBook);
        return books;
    }
}
