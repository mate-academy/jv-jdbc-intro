package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;
import mate.academy.service.BookService;
import mate.academy.service.BookServiceImpl;

public class Main {
    private static final Injector injector = Injector
            .getInstance("mate.academy");
    private static final BigDecimal NEW_PRICE = new BigDecimal("120.50");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        BookService bookService = new BookServiceImpl(bookDao);

        List<Book> savedBooks = new ArrayList<>();
        Map.of("Java", new BigDecimal(300),
                "Python", new BigDecimal(200),
                "C++", new BigDecimal(100))
                .forEach((title, price) -> {
                    Book currentBook = new Book();
                    currentBook.setTitle(title);
                    currentBook.setPrice(price);
                    savedBooks.add(bookService.save(currentBook));
                });
        System.out.println("savedBooks = " + savedBooks);

        Book book = bookService.get(2L);
        System.out.printf("Method get return: " + book);

        book.setPrice(book.getPrice().multiply(NEW_PRICE));
        book = bookService.update(book);
        System.out.printf("Method update return: " + book);

        boolean isDeleteBook = bookService.delete(savedBooks.get(0));
        System.out.printf("Method delete return: " + isDeleteBook);

        List<Book> bookList2 = bookService.getAll();
        System.out.printf("Method getAll return: " + bookList2.toString());
    }
}
