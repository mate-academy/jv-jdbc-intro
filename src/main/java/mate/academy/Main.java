package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao.impl");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book();
        book1.setTitle("Mindset");
        book1.setPrice(BigDecimal.valueOf(190));

        System.out.println(bookDao.findById(2L).get());

        System.out.println(bookDao.create(book1));

        Book book2 = new Book();
        book2.setTitle("Alice in the wonderland");
        book2.setPrice(BigDecimal.valueOf(220));
        book2.setId(2L);

        bookDao.update(book2);

        bookDao.deleteById(3L);
        System.out.println(bookDao.findAll());
    }
}
