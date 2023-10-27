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
        book.setTitle("The Great Gatsby");
        book.setPrice(new BigDecimal(20));
        Book newBook = bookDao.create(book);
        Optional<Book> byId = bookDao.findById(1L);
        List<Book> bookDaoAll = bookDao.findAll();
        Book book1 = new Book();
        book1.setTitle("1984");
        book1.setPrice(new BigDecimal(24));
        book1.setId(1L);
        Book updateBook = bookDao.update(book1);
        boolean b = bookDao.deleteById(1L);
        System.out.println(newBook.getId());
    }
}
