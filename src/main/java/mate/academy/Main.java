package mate.academy;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book firstBook = new Book();
        firstBook.setTitle("Bible");
        firstBook.setPrice(BigDecimal.valueOf(300.00));
        System.out.println("Before adding " + firstBook);
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        firstBook = bookDao.create(firstBook);
        System.out.println("After adding " + firstBook);

        firstBook = bookDao.findById(firstBook.getId()).orElse(null);
        System.out.println("After finding " + firstBook);

        Book secondBook = new Book();
        secondBook.setTitle("Quran");
        secondBook.setPrice(BigDecimal.valueOf(301.00));
        bookDao.create(secondBook);
        List<Book> books = bookDao.findAll();
        System.out.println("Find all " + Arrays.toString(books.toArray()));

        System.out.println("Before updating " + secondBook);
        secondBook.setTitle("Quran2");
        secondBook.setPrice(BigDecimal.valueOf(303.00));
        secondBook = bookDao.update(secondBook);
        System.out.println("After updating " + secondBook);

        System.out.println(bookDao.deleteById(secondBook.getId()));
        books = bookDao.findAll();
        System.out.println("Find all after deleting" + Arrays.toString(books.toArray()));
    }
}
