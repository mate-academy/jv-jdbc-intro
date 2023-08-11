package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final String FOLDER_PATH = "mate.academy.dao";

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) Injector.getInstance(FOLDER_PATH).getInstance(BookDao.class);
        Book firstBook = new Book();
        firstBook.setTitle("firstBook");
        firstBook.setPrice(BigDecimal.valueOf(20.23));
        firstBook = bookDao.create(firstBook);
        System.out.println("First book added to DB : " + firstBook);
        Book secondBook = new Book();
        secondBook.setTitle("secondBook");
        secondBook.setPrice(BigDecimal.valueOf(220.23));
        secondBook = bookDao.create(secondBook);
        System.out.println("Second book added to DB : " + secondBook);
        Book firstBookById = bookDao.findById(firstBook.getId()).get();
        System.out.println("First book has found : " + firstBookById);
        List<Book> books = bookDao.findAll();
        System.out.println("All books have found : " + books);
        firstBook.setTitle("newTitle");
        Book updated = bookDao.update(firstBook);
        System.out.println("First book updated : " + updated);
        boolean isDeleted = bookDao.deleteById(secondBook.getId());
        System.out.println("Second book deleted : " + isDeleted);
    }
}
