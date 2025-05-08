package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Kobzar");
        book.setPrice(BigDecimal.valueOf(15));
        bookDao.create(book);
        List<Book> books = bookDao.findAll();
        System.out.println(books);
        Book book1 = bookDao.findById(2L)
                .orElseThrow(() -> new RuntimeException("Can`t find book with such id"));
        System.out.println(book1);
        book1.setPrice(BigDecimal.valueOf(17));
        bookDao.update(book1);
        bookDao.deleteById(1L);
    }
}
