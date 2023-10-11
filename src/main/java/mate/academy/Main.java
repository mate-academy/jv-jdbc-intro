package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book(1L, "Godfather", BigDecimal.valueOf(200));

        bookDao.create(book);

//        List<Book> books = bookDao.findAll();
//        System.out.println(books);
//
//        Optional<Book> searchedBook = bookDao.findById(1L);
//        System.out.println(searchedBook);
//
//        Book updatedBook = new Book(1L, "Narnia Chronicles", BigDecimal.valueOf(400));
//        bookDao.update(updatedBook);
//        System.out.println(bookDao.findById(1L));
//
//        System.out.println(bookDao.deleteById(1L));
//        System.out.println(bookDao.findAll());

        // test other methods from BookDao
    }
}
