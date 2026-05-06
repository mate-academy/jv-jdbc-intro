package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.dao.BookDaoImpl;
import mate.academy.lib.model.Book;

public class Main {
    public static void main(String[] args) {
        final var bookDao = new BookDaoImpl();
        final var book1 = new Book();
        book1.setTitle("Book 4");
        book1.setPrice(BigDecimal.valueOf(1000));
        final var book2 = new Book();
        book2.setTitle("Book 1");
        book2.setPrice(BigDecimal.valueOf(50));
        final var book3 = new Book();
        book3.setTitle("Book 2");
        book3.setPrice(BigDecimal.valueOf(100));
        final var book4 = new Book();
        book4.setTitle("Book 3");
        book4.setPrice(BigDecimal.valueOf(52));
        book4.setId(3L);
        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);
        System.out.println(bookDao.delete(2L));
        System.out.println(bookDao.update(book4));
        System.out.println(bookDao.findById(3L));
        System.out.println(bookDao.update(book4));
        System.out.println(bookDao.findAll());
    }
}
