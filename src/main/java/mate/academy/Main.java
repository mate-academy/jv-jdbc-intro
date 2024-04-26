package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Lord Of The Rings: The Fellowship Of The Ring");
        book.setPrice(BigDecimal.valueOf(500));
        //create
        book = bookDao.save(book);
        System.out.println(book);
        //read
        System.out.println(bookDao.findById(book.getId()));
        System.out.println(bookDao.findAll());
        //update
        book.setTitle("Lord Of The Rings: The Two Towers");
        book.setPrice(BigDecimal.valueOf(450));
        System.out.println(bookDao.update(book));
        //delete
        System.out.println(bookDao.deleteById(book.getId()));
    }
}
