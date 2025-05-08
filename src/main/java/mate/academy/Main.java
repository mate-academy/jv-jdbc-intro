package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao instance = (BookDao) injector.getInstance(BookDao.class);
        instance.create(new Book(1L,"ZVEROBOY",new BigDecimal("999.99")));
        instance.create(new Book(2L,"SLEDOPIT",new BigDecimal("45.99")));
        Book book = new Book(3L, "INDEEC", new BigDecimal("9.39"));
        instance.create(book);
        book.setTitle("DON KIHOT");
        book.setPrice(new BigDecimal("100.10"));
        instance.update(book);
        System.out.println(instance.findAll());
        System.out.println(instance.findById(2L));
        instance.deleteById(2L);
        System.out.println(instance.findAll());
    }
}
