package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book designPatterns = new Book();
        designPatterns.setTitle("Design Patterns");
        designPatterns.setPrice(BigDecimal.valueOf(1000));
        bookDao.create(designPatterns);
        Book javaBook = new Book();
        javaBook.setTitle("Java textbook");
        javaBook.setPrice(BigDecimal.valueOf(500));
        bookDao.create(javaBook);
        Book englishBook = new Book();
        englishBook.setTitle("English textbook");
        englishBook.setPrice(BigDecimal.valueOf(150));
        bookDao.create(englishBook);
        designPatterns.setPrice(BigDecimal.valueOf(900));
        bookDao.update(designPatterns);
        System.out.println(bookDao.findById(englishBook.getId()));
        for (Book book : bookDao.findAll()) {
            System.out.println(book);
        }
        System.out.println(bookDao.deleteById(javaBook.getId()));
    }
}
