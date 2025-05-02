package mate.academy;

import java.math.BigDecimal;
import mate.academy.bookdao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Kid's book");
        book.setPrice(BigDecimal.valueOf(12.56));

        bookDao.create(book);
        book.setPrice(BigDecimal.valueOf(20.02));
        bookDao.update(book);
        bookDao.deleteById(11L);

        System.out.println(bookDao.deleteById(17L));
        System.out.println(bookDao.findById(18L));
        System.out.println(bookDao.create(book));
        book.setTitle("Manual");
        System.out.println(bookDao.update(book));
        System.out.println(bookDao.findById(38L).get().getTitle());
        for (long i = 8; i < 40; i++) {
            bookDao.deleteById(i);
        }
        System.out.println(bookDao.findAll());
    }
}
