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

        Book book = new Book();
        book.setTitle("South Sea Tales");
        book.setPrice(new BigDecimal("23.5"));
        Book savedBook = bookDao.create(book);
        System.out.println("Created book: " + savedBook.getId() + " " + savedBook.getTitle());

        Optional<Book> foundBook = bookDao.findById(savedBook.getId());
        foundBook.ifPresent(b -> System.out.println("Found book: " + book.getId() + " "
                + book.getTitle()));

        System.out.println("All books in DB:");
        bookDao.findAll().forEach(System.out::println);

        savedBook.setPrice(new BigDecimal("30"));
        bookDao.update(savedBook);
        System.out.println("Updated book price: " + savedBook.getPrice());

        boolean deleted = bookDao.deleteById(savedBook.getId());
        System.out.println("Deleted book: " + deleted);

    }
}
