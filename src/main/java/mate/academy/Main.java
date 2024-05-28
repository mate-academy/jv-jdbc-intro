package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        Book book1 = new Book("Book of Jungles", new BigDecimal(100));
        bookDao.create(book1);

        Optional<Book> bookById = bookDao.findById(1L);
        System.out.println(bookById);

        List<Book> books = bookDao.findAll();
        System.out.println(books);

        Book bookToUpdate = new Book(2L, "Book of Jungles 2", BigDecimal.valueOf(120));
        System.out.println(bookDao.update(bookToUpdate));

        boolean isDeleted = bookDao.deleteById(5L);
        System.out.println(isDeleted);
    }
}
