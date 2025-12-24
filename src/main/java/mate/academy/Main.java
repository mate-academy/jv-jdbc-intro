package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Progrmowanie w Javie");
        book.setPrice(new BigDecimal("24.78"));

        Book book1 = bookDao.create(book);
        System.out.println(book1);

        Optional<Book> foundBook = bookDao.findById(book1.getId());
        System.out.println("FoundBook: " + foundBook);

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books: " + allBooks);

        book1.setTitle("Programowanie w Javie i Pythonie");
        book1.setPrice(new BigDecimal("55.65"));

        Book updatedBook = bookDao.update(book1);
        System.out.println("Updated book: " + updatedBook);

        boolean ifDeleted = bookDao.deleteById(book1.getId());
        System.out.println("Was deleted? " + ifDeleted);

    }
}
