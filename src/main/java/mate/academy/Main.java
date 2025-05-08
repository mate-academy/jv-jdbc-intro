package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> bookList = List.of(
                new Book("Java", BigDecimal.valueOf(100)),
                new Book("Python", BigDecimal.valueOf(200)),
                new Book("C++", BigDecimal.valueOf(300)),
                new Book("Javascript", BigDecimal.valueOf(400)));
        bookDao.create(bookList.get(1));
        Book python = bookDao.findById(9L).get();
        System.out.println(python);
        python.setTitle("Updated python");
        python.setPrice(BigDecimal.valueOf(500));
        bookDao.update(python);
        Book updatedPython = bookDao.findById(9L).get();
        System.out.println(updatedPython);
        List<Book> beforDeleteLast = bookDao.findAll();
        System.out.println(beforDeleteLast);
        bookDao.deleteById(11L);
        List<Book> afterDeleteLast = bookDao.findAll();
        System.out.println(afterDeleteLast);
    }
}

