package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book1 = new Book();
        book1.setTitle("firstBook");
        book1.setPrice(BigDecimal.valueOf(111));

        Book book2 = new Book();
        book2.setTitle("secondBook");
        book2.setPrice(BigDecimal.valueOf(222));

        Book bookToUpdate = new Book();
        bookToUpdate.setId(1L);
        bookToUpdate.setTitle("updateBook");
        bookToUpdate.setPrice(BigDecimal.valueOf(777));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        bookDao.create(book1);
        bookDao.create(book2);

        List<Book> books = bookDao.findAll();
        System.out.println(books);

        bookDao.update(bookToUpdate);
        System.out.println(bookDao.findById(1L).get());

        System.out.println(bookDao.deleteById(2L));
        System.out.println(bookDao.deleteById(5L));
    }
}
