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
        Book redAndBlack = new Book();
        redAndBlack.setTitle("Red and Black");
        redAndBlack.setPrice(BigDecimal.valueOf(100));
        bookDao.create(redAndBlack);
        Book odissey = new Book();
        odissey.setTitle("Odyssey");
        odissey.setPrice(BigDecimal.valueOf(200));
        bookDao.create(odissey);
        List<Book> books = bookDao.findAll();
        System.out.println(books);
        Long idFindBook = 1L;
        Optional<Book> bookById = bookDao.findById(idFindBook);
        System.out.println(bookById);
        odissey.setPrice(BigDecimal.valueOf(300));
        bookDao.update(odissey);
        bookDao.findAll().forEach(System.out:: println);
        bookDao.deleteById(redAndBlack.getId());
        bookDao.findAll().forEach(System.out:: println);
    }
}
