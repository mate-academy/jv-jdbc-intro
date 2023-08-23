package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(10));
        book.setTitle("Testing");
        Book savedBook = bookDao.create(book);
        List<Book> all = bookDao.findAll();
        Optional<Book> bookOptional = bookDao.findById(savedBook.getId());
        System.out.println(bookOptional.get().getTitle());
        Book toUpdateBook = new Book();
        toUpdateBook.setPrice(BigDecimal.valueOf(15));
        toUpdateBook.setTitle("Testing2");
        bookDao.update(book);
        bookDao.deleteById(savedBook.getId());
    }
}
