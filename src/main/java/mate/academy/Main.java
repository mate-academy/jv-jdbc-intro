package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final BookDao bookDao =
            (BookDao) injector.getInstance(BookDao.class);
    public static void main(String[] args) {
        Book book1 = new Book(1L, "Math", BigDecimal.valueOf(100));
        Book book2 = new Book(2L, "Java", BigDecimal.valueOf(150));
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
//        for (Book book : books) {
//            bookDao.create(book);
//        } // success
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.findById(1L));
        book1.setTitle("English");
        book1.setPrice(BigDecimal.valueOf(70));
        bookDao.update(book1);
        bookDao.deleteById(1L);
        System.out.println(bookDao.findById(1L));
    }
}
