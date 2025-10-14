package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setPrice(BigDecimal.valueOf(499.99));

        Book createdBook = bookDao.create(book);
        System.out.println("Book created: " + createdBook);

        Optional<Book> foundBook = bookDao.findById(createdBook.getId());
        foundBook.ifPresent(b -> System.out.println("Found book: " + b));

        createdBook.setPrice(BigDecimal.valueOf(549.99));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        bookDao.findAll().forEach(System.out::println);

        boolean deleted = bookDao.deleteById(createdBook.getId());
        System.out.println("Book deleted: " + deleted);
    }
}