package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Injector;
import mate.academy.lib.dao.Dao;
import mate.academy.lib.dao.impl.BookDao;
import mate.academy.lib.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(Dao.class);

        Book bookOne = new Book("Book One", BigDecimal.valueOf(10.01));
        Book bookTwo = new Book("Book Two", BigDecimal.valueOf(20.02));
        Book bookThree = new Book("Book Three", BigDecimal.valueOf(30.03));

        bookDao.create(bookOne);
        bookDao.create(bookTwo);
        bookDao.create(bookThree);

        Optional<Book> bookOneFoundById = bookDao.findById(bookOne.getId());
        System.out.println(bookOneFoundById);

        bookTwo.setPrice(bookTwo.getPrice().add(BigDecimal.valueOf(200.2)));
        bookDao.update(bookTwo);

        bookThree.setPrice(bookThree.getPrice().add(BigDecimal.valueOf(300.3)));
        bookThree.setTitle("Book 3");
        bookDao.update(bookThree);

        List<Book> books = bookDao.findAll();
        System.out.println(books);

        bookDao.deleteById(bookOne.getId());

        List<Book> updatedBooks = bookDao.findAll();
        System.out.println(updatedBooks);
    }
}
