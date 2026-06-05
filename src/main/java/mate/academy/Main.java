package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.dao.BookDao;
import mate.academy.lib.injector.Injector;
import mate.academy.lib.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");
    
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        // ============== CREATE ==================
        Book book = new Book();
        book.setTitle("Test Book");
        book.setPrice(new BigDecimal("99.99"));
        bookDao.create(book);
        
        Book book2 = new Book();
        book2.setTitle("Another Book");
        book2.setPrice(new BigDecimal("10.99"));
        bookDao.create(book2);

        // ============== READ =================

        bookDao.findAll().forEach(System.out::println);
        bookDao.findById(2L).ifPresent(System.out::println);
        
        // ============== UPDATE ==================
        
        book.setPrice(new BigDecimal("22.99"));
        book.setTitle("Updated Book");
        bookDao.update(book);
        
        // ============== DELETE ===================
        bookDao.deleteById(1L);
    }
}
