package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("NotingToSay", BigDecimal.valueOf(12.0));
        Book book3 = new Book("TorontoTokyo", BigDecimal.valueOf(11.0));
        Book book4 = new Book("Marakesh", BigDecimal.valueOf(23.0));
        bookDao.create(book);
        bookDao.create(book3);
        bookDao.create(book4);

        Book book2 = bookDao.findById(1L).get();
        book2.setTitle("Harry Potter");
        bookDao.update(book2);
        bookDao.deleteById(2L);
        for (Book b : bookDao.findAll()) {
            System.out.println(b);
        }
    }
}
