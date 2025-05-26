package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.bookdao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final String DAO_PATH = "mate.academy";

    public static void main(String[] args) {
        Injector injector = Injector.getInstance(DAO_PATH);
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book();
        book1.setTitle("First");
        book1.setPrice(new BigDecimal("100.00"));
        Book savedBook = bookDao.create(book1);
        System.out.println("Book was saved with id: " + savedBook.getId());

        Optional<Book> optionalBook = bookDao.findById(1L);
        optionalBook.ifPresentOrElse(
                book -> System.out.println("Found book: " + book),
                () -> System.out.println("Book not found")
        );

        Book book2 = new Book();
        book2.setId(book1.getId());
        book2.setTitle("Update title");
        book2.setPrice(new BigDecimal("123.00"));
        Book updateBook = bookDao.update(book2);
        System.out.println("Book was update: " + book1 + " -> " + book2);

        boolean isDeleted = bookDao.deleteById(book2.getId());
        System.out.println("Book was delete");

        System.out.println(bookDao.findAll());
    }
}
