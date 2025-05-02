package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        Book book1 = new Book("Java 8", new BigDecimal("125.99"));
        Book book2 = new Book("Go for noobs", new BigDecimal("55.00"));
        Book book3 = new Book("Js in Action", new BigDecimal("99.99"));
        Book book4 = new Book("Sprig for pros", new BigDecimal("200.00"));

//        Book savedBook1 = bookDao.create(book1);
//        Book savedBook2 = bookDao.create(book2);
//        Book savedBook3 = bookDao.create(book3);
//        Book savedBook4 = bookDao.create(book4);

        Optional<Book> byId = bookDao.findById(2L);
        System.out.println(byId.orElseGet(Book::new));

        book4.setPrice(new BigDecimal("200.00"));
        book4.setId(4L);
        Book updatedBook = bookDao.update(book4);
        System.out.println(updatedBook);

        List<Book> all = bookDao.findAll();

        all.forEach(System.out::println);
        System.out.println(bookDao.deleteById(1L));
    }
}
