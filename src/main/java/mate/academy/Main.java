package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = List.of(
                new Book("FirstBook", BigDecimal.valueOf(100)),
                new Book("SecondBook", BigDecimal.valueOf(200)),
                new Book("ThirdBook", BigDecimal.valueOf(300))
        );

        for (Book book: books) {
            bookDao.create(book);
        }

        System.out.println(bookDao.findById(3L));
        System.out.println(bookDao.findById(4L));

        List<Book> bookList = bookDao.findAll();
        bookList.forEach(System.out::println);

        Book updatedBook = bookDao.update(new Book(3L, "StillThirdBook",
                BigDecimal.valueOf(400L)));
        System.out.println(updatedBook);

        boolean deletedBook = bookDao.deleteById(2L);
        System.out.println(deletedBook);
    }
}
