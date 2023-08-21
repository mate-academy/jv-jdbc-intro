package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Book book = new Book("book", BigDecimal.valueOf(100));
    private static final Book book1 = new Book("book1", BigDecimal.valueOf(1000));
    private static final Book book2 = new Book("book2", BigDecimal.valueOf(2000));

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book createdBook = bookDao.create(book);
        System.out.println("Created book: " + createdBook);

        Book findedBook = bookDao.findById(createdBook.getId()).orElse(null);
        System.out.println("Founded book: " + findedBook);

        createdBook.setPrice(BigDecimal.valueOf(2000));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("updated book: " + updatedBook);

        System.out.println("add " + book1);
        bookDao.create(book1);
        System.out.println("add " + book2);
        bookDao.create(book2);
        List<Book> booksInTable = bookDao.findAll();
        System.out.println("List of books in table = " + booksInTable);

        boolean book1IsDeleted = bookDao.deleteById(book.getId());
        System.out.println("Book is deleted = " + book1IsDeleted);
        System.out.println("List of books in table = " + bookDao.findAll());
    }
}
