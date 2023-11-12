package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;

import java.math.BigDecimal;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Optional<Book> byId = bookDao.findById(1L);
        Book testBook1 = new Book("Test Book", BigDecimal.valueOf(111));
//        Book testingCreate = bookDao.create(testBook1);
//        System.out.println(testingCreate);
//        bookDao.deleteById(3L);
//        System.out.println(bookDao.update(new Book(2L,"The Shining", BigDecimal.valueOf(1111))));
        System.out.println(bookDao.findAll());
    }
}
