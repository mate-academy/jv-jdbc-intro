package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao dao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Hobbit");
        book.setPrice(new BigDecimal("630.35"));
        dao.create(book);
        Optional<Book> bookGet = dao.findById(1L);
        List<Book> books = dao.findAll();
        book.setPrice(BigDecimal.valueOf(5000.50));
        dao.update(book);
        System.out.println(book);
        dao.deleteById(1L);
    }
}
