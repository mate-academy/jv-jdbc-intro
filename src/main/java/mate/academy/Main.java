package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        // Create
        Book book2 = new Book();
        book2.setTitle("breakfast for champions");
        book2.setPrice(BigDecimal.valueOf(28));
        System.out.println(bookDao.create(book2));
        // Read
        System.out.println(bookDao.findAll());
        long id = 1L;
        Optional<Book> bookOptional = bookDao.findById(id);
        bookOptional.ifPresent(System.out::println);
        if (bookOptional.isEmpty()) {
            System.out.println(("Book with id = " + id + " no exist."));
        }
        // Update
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Catch 22");
        book.setPrice(BigDecimal.valueOf(22));
        System.out.println(bookDao.update(book));
        // Delete
        book2.setId(5L);
        if (! bookDao.delete(book2)) {
            System.out.println(book2 + " not found!");
        }
    }
}
