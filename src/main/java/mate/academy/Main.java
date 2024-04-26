package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        final Book book1 = new Book();
        final Book book2 = new Book();
        final Book book3 = new Book();
        final Book book4 = new Book();
        // initialize field values using setters or constructor
        book1.setTitle("QWERTY");
        book1.setPrice(BigDecimal.valueOf(123));
        bookDao.create(book1);
        book2.setTitle("title");
        book2.setPrice(BigDecimal.valueOf(456));
        bookDao.create(book2);
        book3.setTitle("World");
        book3.setPrice(BigDecimal.valueOf(789));
        bookDao.create(book3);
        book4.setTitle("Cat");
        book4.setPrice(BigDecimal.valueOf(543));
        bookDao.create(book4);
        // test other methods from BookDao
        List<Book> bookList = bookDao.findAll();
        bookList.forEach(System.out::println);

        System.out.println();

        Book updatedBook = new Book();
        updatedBook.setId(bookList.get(0).getId());
        updatedBook.setTitle("Updated");
        updatedBook.setPrice(BigDecimal.valueOf(999));
        bookDao.update(updatedBook);
        bookList.forEach(System.out::println);

        bookList.forEach(book -> bookDao.deleteById(book.getId()));
    }
}
