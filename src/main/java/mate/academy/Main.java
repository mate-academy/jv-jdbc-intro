package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector
            = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        // test method create()
        Book book01 = new Book();
        book01.setTitle("Effective Java");
        book01.setPrice(BigDecimal.valueOf(123.45d));
        final Book createdBook_1 = bookDao.create(book01);
        System.out.println("created book: " + createdBook_1);

        Book book02 = new Book();
        book02.setTitle("Java concurrency in practice");
        book02.setPrice(BigDecimal.valueOf(40.75d));
        final Book createdBook_2 = bookDao.create(book02);
        System.out.println("created book: " + createdBook_2);

        // test method findById()
        final Optional<Book> bookWithId_1 = bookDao.findById(1L);
        bookWithId_1.ifPresent(b -> System.out.println("findById(1L): " + b));
        final Optional<Book> bookWithId_2 = bookDao.findById(2L);
        bookWithId_2.ifPresent(b -> System.out.println("findById(2L): " + b));

        // test method update()
        bookWithId_2.ifPresent(b -> b.setTitle(b.getTitle() + " changed title!!! "));
        final Book updatedBook = bookDao.update(bookWithId_2.orElseThrow());
        System.out.println("update() to change title: " + updatedBook);

        // test method delete()
        bookDao.deleteById(2L);
        System.out.println("deleteById(2L)");

        // test method findFindAll()
        final List<Book> allBooks = bookDao.findAll();
        System.out.println("List of all books: " + allBooks);
    }
}
