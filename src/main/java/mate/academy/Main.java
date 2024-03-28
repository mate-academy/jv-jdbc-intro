package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book firstBook = new Book("The Chronical Of Amber ", new BigDecimal("10.99"));
        bookDao.create(firstBook);

        Book secondBook = new Book("Shogun ",new BigDecimal("9.02"));
        bookDao.create(secondBook);

        System.out.println("Find all books " + bookDao.findAll());
        System.out.println("Find by id " + bookDao.findById(firstBook.getId()).get());

        Book book = bookDao.findById(firstBook.getId()).get();
        book.setTitle("Taypen");
        bookDao.update(book);
        System.out.println("New Title " + bookDao.findById(firstBook.getId()).get());

        System.out.println("Delete by id " + bookDao.deleteById(secondBook.getId()));
        System.out.println("Remaining books " + bookDao.findAll());
    }
}
