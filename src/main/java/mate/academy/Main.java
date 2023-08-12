package mate.academy;

import mate.academy.Dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book harryPotterBook = new Book();
        harryPotterBook.setTitle("Harry Potter");
        harryPotterBook.setPrice(BigDecimal.valueOf(120));
        Book theHobbitBook = new Book();
        theHobbitBook.setTitle("The Hobbit");
        theHobbitBook.setPrice(BigDecimal.valueOf(200));

        bookDao.create(harryPotterBook);
        bookDao.create(theHobbitBook);
        theHobbitBook.setPrice(BigDecimal.valueOf(150));
        bookDao.update(theHobbitBook);

        System.out.println(bookDao.findById(theHobbitBook.getId()));
        bookDao.findAll().forEach(System.out::println);
        System.out.println(bookDao.deleteById(theHobbitBook.getId()));
    }
}
