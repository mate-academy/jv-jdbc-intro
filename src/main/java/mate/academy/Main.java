package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(548));
        book.setTitle("The Witcher. 1. The Last Wish");
        bookDao.create(book);
        System.out.println(bookDao.findById(6L).orElseThrow(()
                -> new RuntimeException("Can`t find book with id " + 1L)));
        List<Book> books = bookDao.findAll();
        books.forEach(System.out::println);
        Optional<Book> newBook = bookDao.findById(6L);
        Book newBookForChange = newBook.get();
        newBookForChange.setPrice(BigDecimal.valueOf(250));
        System.out.println(bookDao.update(newBook.get()));
        System.out.println(bookDao.deleteById(7L));
    }
}
