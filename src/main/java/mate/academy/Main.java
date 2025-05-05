package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book("Cars", BigDecimal.valueOf(100));
        Book book1 = new Book("Trains", BigDecimal.valueOf(200));
        Book book2 = new Book("Helicopters", BigDecimal.valueOf(300));
        Book savedBook = bookDao.create(book);
        bookDao.create(book1);
        bookDao.create(book2);

        System.out.println(bookDao.findById(savedBook.getId()));
        bookDao.findAll().forEach(System.out::println);
        Book updatedBook = bookDao.findById(book1.getId()).orElseThrow(() ->
                new RuntimeException("Book was not found"));
        updatedBook.setPrice(BigDecimal.valueOf(150));
        updatedBook.setTitle("Planes");
        bookDao.update(updatedBook);
        bookDao.findAll().forEach(System.out::println);
        bookDao.deleteById(book2.getId());
    }
}
