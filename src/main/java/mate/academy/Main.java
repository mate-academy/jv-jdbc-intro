package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book(
                "Effective Java", new BigDecimal("45.90")
        );
        Book createBook = bookDao.create(book);
        System.out.println("Created book: " + createBook);
        System.out.println(
                "Found by id: " + bookDao.findById(createBook.getId())
        );
        System.out.println("All books: " + bookDao.findAll());

        createBook.setTitle("Effective Java Third Edition");
        createBook.setPrice(new BigDecimal("49.90"));

        Book updateBook = bookDao.update(createBook);
        System.out.println("Updated book: " + updateBook);

        boolean delete = bookDao.deleteById(createBook.getId());
        System.out.println(
                "Book after deletion: " + bookDao.findById(createBook.getId())
        );
    }
}
