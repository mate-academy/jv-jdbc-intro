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
        // create
        Book book1 = new Book();
        book1.setTitle("Effective Java");
        book1.setPrice(BigDecimal.valueOf(123.45d));
        final Book createdBook_1 = bookDao.create(book1);
        System.out.println("created book: " + createdBook_1);

        Book book2 = new Book();
        book2.setTitle("Java concurrency in practice");
        book2.setPrice(BigDecimal.valueOf(40.75d));
        final Book createdBook_2 = bookDao.create(book2);
        System.out.println("created book: " + createdBook_2);

        // findById
        final Optional<Book> bookWithId_01 = bookDao.findById(1L);
        bookWithId_01.ifPresent(b -> System.out.println("findById(1L): " + b));
        final Optional<Book> bookWithId_02 = bookDao.findById(2L);
        bookWithId_02.ifPresent(b -> System.out.println("findById(2L): " + b));

        // update
        bookWithId_02.ifPresent(b -> b.setTitle(b.getTitle() + " changed title!!! "));
        final Book updatedBook = bookDao.update(bookWithId_02.orElseThrow());
        System.out.println("update() to change title: " + updatedBook);

        // delete
        bookDao.deleteById(2L);
        System.out.println("deleteById(2L)");

        // findFindAll
        final List<Book> allBooks = bookDao.findAll();
        System.out.println("List of all books: " + allBooks);
    }
}
