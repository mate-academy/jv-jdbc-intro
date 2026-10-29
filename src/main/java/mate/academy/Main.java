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

        Book prince = new Book();
        prince.setTitle("Little Prince");
        prince.setPrice(new BigDecimal("19.99"));
        Book princeCreated = bookDao.create(prince);
        Long createdId = princeCreated.getId();
        Optional<Book> bookFindById = bookDao.findById(createdId);
        List<Book> findAll = bookDao.findAll();

        Book bookNew = new Book();
        bookNew.setId(createdId);
        bookNew.setTitle("Alchemist");
        bookNew.setPrice(new BigDecimal("17.80"));
        Book bookUpdate = bookDao.update(bookNew);
        boolean deleteById = bookDao.deleteById(createdId);
    }
}
