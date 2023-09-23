package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.dao.impl.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        List<Book> bookList = new ArrayList<>(List.of(
                new Book(1L,"Story of Roman Empire",BigDecimal.valueOf(300)),
                new Book(2L,"OOP Principes",BigDecimal.valueOf(150)),
                new Book(3L,"Head first Java",BigDecimal.valueOf(1050)),
                new Book(4L,"Clean Code",BigDecimal.valueOf(550))));
        BookDao bookDao = (BookDaoImpl) injector.getInstance(BookDao.class);
        for (Book book: bookList) {
            bookDao.create(book);
        }
        System.out.println(bookDao.findAll());
        Book newBook = new Book(2L,"Top 10 musicians of all time",BigDecimal.valueOf(500));
        bookDao.update(newBook);

        System.out.println(bookDao.findById(3L));
        bookDao.deleteById(3L);
    }
}
