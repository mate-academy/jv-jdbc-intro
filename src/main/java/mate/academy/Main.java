package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("your.package");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Володар перснів");
        book.setPrice(new BigDecimal("29.99"));
        bookDao.create(book);
        System.out.println("Створена книга: " + book);

        Optional<Book> bookFromDb = bookDao.findById(book.getId());
        bookFromDb.ifPresent(System.out::println);

        book.setTitle("Гобіт");
        book.setPrice(new BigDecimal("19.99"));
        bookDao.update(book);
        System.out.println("Оновлена книга: " + book);

        List<Book> allBooks = bookDao.findAll();
        allBooks.forEach(System.out::println);

        boolean deleted = bookDao.deleteById(book.getId());
        System.out.println("Видалено: " + deleted);
    }
}
