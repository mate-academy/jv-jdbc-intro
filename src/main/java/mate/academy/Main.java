package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {

        BookDao bookDao = new BookDaoImpl();//(BookDao) injector.getInstance(BookDao.class);

        Book kobzar = new Book("T.Shevchenko_Kobzar", BigDecimal.valueOf(50L));
        Book createdBook = bookDao.create(kobzar);
        System.out.println("Book: " + createdBook + " was added to DB.");

        Optional<Book> existingBook = bookDao.findById(createdBook.getId());
        System.out.println("Book by id: " + createdBook.getId() + ". " + existingBook);
        System.out.println("Book by id: 999. " + bookDao.findById(999L));

        kobzar.setPrice(BigDecimal.valueOf(100));
        Book updatingBook = bookDao.update(kobzar);
        System.out.println("Book: " + updatingBook + " was updated.");

        Book thinkingInJava = new Book("B.Eckel_ThinkingInJava", BigDecimal.valueOf(150L));
        System.out.println("Book: " + bookDao.create(thinkingInJava)+ " was added to DB.");

        List<Book> books = bookDao.findAll();
        System.out.println("All book in DB:");
        books.forEach(book -> System.out.println("Book from DB before deleting: " + book));

        System.out.println("Book by id 1 was deleted? - " + bookDao.deleteById(1L));
        System.out.println("Book by id 999 was deleted? - " + bookDao.deleteById(999L));

        System.out.println("Book by id 1: " + bookDao.findById(1L));

        books.forEach(book -> System.out.println("Book from DB after deleting: " + book));
    }
}
