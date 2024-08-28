package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        Book book1 = new Book();
        book1.setTitle("book1");
        book1.setPrice(BigDecimal.valueOf(200));
        Book book2 = new Book();
        book2.setTitle("book2");
        book2.setPrice(BigDecimal.valueOf(300));;
        Book updateBook = new Book();
        updateBook.setId(1L);
        updateBook.setTitle("updatedBook");
        updateBook.setPrice(BigDecimal.valueOf(999));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(book1);
        bookDao.create(book2);
        System.out.println(bookDao.findById(2L));
        System.out.println(bookDao.findAll().toString());
        System.out.println(bookDao.deleteById(2L));
        bookDao.update(updateBook);
    }
}
