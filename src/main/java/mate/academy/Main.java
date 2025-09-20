package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.lidimpl.Injector;
import mate.academy.models.Book;

public class Main {
    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("Dream");
        book.setPrice(BigDecimal.valueOf(10.99));
        Book book2 = new Book();
        book2.setTitle("Night");
        book2.setPrice(BigDecimal.valueOf(15.99));
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book savedBook1 = bookDao.create(book);
        Book savedBook2 = bookDao.create(book2);
        System.out.println("All books: " + bookDao.findAll());
        System.out.println("Find by id: " + bookDao.findById(savedBook2.getId()).get());
        savedBook1.setPrice(BigDecimal.valueOf(30));
        bookDao.update(savedBook1);
        bookDao.deleteById(savedBook2.getId());
        System.out.println("All books after delete: " + bookDao.findAll());
    }
}


