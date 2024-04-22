package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book();
        firstBook.setTitle("Mindset");
        firstBook.setPrice(BigDecimal.valueOf(190));

        System.out.println(bookDao.findById(2L).get());

        System.out.println(bookDao.create(firstBook));

        Book secondBook = new Book();
        secondBook.setTitle("Alice in the wonderland");
        secondBook.setPrice(BigDecimal.valueOf(220));
        secondBook.setId(2L);

        bookDao.update(secondBook);

        bookDao.deleteById(3L);
        System.out.println(bookDao.findAll());
    }
}
