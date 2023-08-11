package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bookOne = new Book(0L, "Hyperion", BigDecimal.valueOf(500));
        Book bookTwo = new Book(0L, "The Fall of Hyperion", BigDecimal.valueOf(1200));
        Book bookThree = new Book(0L, "Endymion", BigDecimal.valueOf(300));
        Book bookFour = new Book(0L, "the rise of endymion,", BigDecimal.valueOf(2500));

        bookDao.create(bookOne);
        bookDao.create(bookTwo);
        bookDao.create(bookThree);
        bookDao.create(bookFour);

        Optional<Book> bookById = bookDao.findById(1L);
        System.out.println("Book by id 1 = " + bookById);

        List<Book> allBook = bookDao.findAll();
        System.out.println(allBook);

        Book newBook = new Book(4L, "The Rise of Endymion", BigDecimal.valueOf(10000));
        Book bookUpdate = bookDao.update(newBook);
        System.out.println("Book update by id = 4 " + bookUpdate);

        boolean bookDelete = bookDao.deleteById(1L);
        System.out.println("Delete successful - " + bookDelete);
    }
}
