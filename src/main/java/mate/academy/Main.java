package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> bookList = List.of(
                new Book("Art of programming", BigDecimal.valueOf(1500.00)),
                new Book("Hunger games. Part-1", BigDecimal.valueOf(865.00)),
                new Book("The Pragmatic Programmer", BigDecimal.valueOf(1200.50))
        );

        List<Book> createdBooks = bookList.stream()
                .map(bookDao::create)
                .toList();

        Book book = bookDao.findById(createdBooks.get(0).getId())
                .orElseThrow(() -> new RuntimeException("Book was not found"));
        System.out.println(book);
        bookDao.findAll().forEach(System.out::println);
        bookDao.deleteById(createdBooks.get(2).getId());
        book.setPrice(BigDecimal.valueOf(2000));
        book.setTitle("Some updated book");
        Book update = bookDao.update(book);
        System.out.println(update);
    }
}
