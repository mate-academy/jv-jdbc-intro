package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Sherlock Holmes");
        book.setPrice(BigDecimal.valueOf(400));
        Book createdBook = bookDao.create(book);
        Optional<Book> foundBook = bookDao.findById(1L);
        List<Book> allBooks = bookDao.findAll();
        Book updatedBook = new Book();
        updatedBook.setTitle("The Adventures of Sherlock Holmes");
        updatedBook.setPrice(BigDecimal.valueOf(500));
        Book newBook = bookDao.update(updatedBook);
        bookDao.deleteById(1L);
    }
}
