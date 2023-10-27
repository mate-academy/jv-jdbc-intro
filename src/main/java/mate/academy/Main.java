package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();
        Book book = new Book();
        book.setTitle("1984");
        book.setPrice(new BigDecimal(100));
        bookDao.create(book);
        Optional<Book> optionalBook = bookDao.findById(1L);
        System.out.println(optionalBook.get());
        List<Book> bookList = bookDao.findAll();
        System.out.println(bookList);
        Book newBook = new Book();
        newBook.setTitle("365 days");
        newBook.setPrice(new BigDecimal(50));
        newBook.setId(1L);
        Book updatedBook = bookDao.update(newBook);
        List<Book> updatedBookList = bookDao.findAll();
        boolean isDelete = bookDao.deleteById(1L);
        System.out.println(updatedBookList);
        System.out.println(isDelete);
    }
}
