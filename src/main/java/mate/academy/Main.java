package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) Injector
                .getInstance("mate.academy.dao")
                .getInstance(BookDao.class);
        Book firstBook = new Book("Lord of rings", new BigDecimal(1500));
        Book secondBook = new Book("Martin Eden", new BigDecimal(200));
        Book thirdBook = new Book("Thinking in Java", new BigDecimal(600));
        Book fourthBook = new Book("The Godfather", new BigDecimal(500));
        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.create(thirdBook);
        bookDao.create(fourthBook);
        bookDao.update(new Book(secondBook.getId(),
                "John BarleyCorn", new BigDecimal(250)));
        bookDao.deleteById(fourthBook.getId());
        System.out.println(bookDao.findById(firstBook.getId()));
        System.out.println(bookDao.findAll());
    }
}
