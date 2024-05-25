package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import mate.academy.service.BookServiceImpl;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static BookService service;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        service = new BookServiceImpl(bookDao);

        List books = List.of(
                new Book("Harry", new BigDecimal(1222)),
                new Book("Potter", new BigDecimal(999)),
                new Book("Lol", new BigDecimal(69)),
                new Book("Kek", new BigDecimal(1488)));

        books.stream().map(book -> service.createBook((Book) book)).collect(Collectors.toList());

        service.findAllBooks().forEach(System.out::println);

        System.out.println(service.findBookById(3L));

        Book bookForUpdate = new Book(3L, "Update", new BigDecimal(12345));
        service.updateBook(bookForUpdate);

        service.deleteBookById(4L);

        service.deleteAll();
    }
}
