package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.ConnectionUtilImpl;
import mate.academy.lib.Injector;
import mate.academy.models.Book;
import mate.academy.service.BookService;
import mate.academy.service.BookServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Injector injector = Injector
            .getInstance("C:\\Users\\illio\\IdeaProjects\\jv-jdbc-intro");
    private static final String PASSWORD = "illya";
    private static final String DB_URL = "jdbs.mysql://localhost:3306/my_db";
    private static final BigDecimal NEW_PRICE = new BigDecimal("120.50");

    public static void main(String[] args) {
//        ConnectionUtilImpl.setPassword(PASSWORD);
//        ConnectionUtilImpl.setDbUrl(DB_URL);
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        BookService bookService = new BookServiceImpl(bookDao);

        Map<String, BigDecimal> dataForBooks = Map.of("Java", new BigDecimal(300),
                "Python", new BigDecimal(200), "C++", new BigDecimal(100));
        dataForBooks.forEach((key, value) -> {
            Book currentBook = new Book();
            currentBook.setTitle(key);
            currentBook.setPrice(value);
            bookService.save(currentBook);
        });

        Book book = bookService.get(2L);
        System.out.printf("Method get return: " + book);
        book.setPrice(book.getPrice().multiply(NEW_PRICE));
        book = bookService.update(book);
        System.out.printf("Method update return: " + book);
        boolean isDeleteBook = bookService.delete(book);
        System.out.printf("Method delete return: " + isDeleteBook);
        List<Book> bookList2 = bookService.getAll();
        System.out.printf("Method getAll return: " + bookList2.toString());
    }
}
