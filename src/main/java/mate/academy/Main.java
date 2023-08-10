package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final BookDao bookDao = (BookDao) injector
                .getInstance(BookDao.class);
        List<Book> books = new ArrayList<>();
        Book kobzar = new Book();
        kobzar.setTitle("Kobzar");
        kobzar.setPrice(BigDecimal.valueOf(150));
        books.add(kobzar);
        Book algebra = new Book();
        algebra.setTitle("Algebra");
        algebra.setPrice(BigDecimal.valueOf(200));
        books.add(algebra);
        Book biology = new Book();
        biology.setTitle("Biology");
        biology.setPrice(BigDecimal.valueOf(250));
        books.add(biology);
        books.forEach(System.out::println);
        books.forEach(bookDao::create);
        bookDao.findAll().forEach(System.out::println);
        System.out.println(bookDao.findById(2L));
        bookDao.deleteById(1L);
        biology.setTitle("Zoology");
        biology.setPrice(BigDecimal.valueOf(300));
        bookDao.update(biology);
        bookDao.findAll().forEach(System.out::println);
    }
}
