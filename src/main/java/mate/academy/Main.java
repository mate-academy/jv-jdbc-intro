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
        Book createBook = new Book("Think JAVA", new BigDecimal("23.00"));
        bookDao.create(createBook);

        Optional<Book> findBook = bookDao.findById(3L);
        System.out.println(findBook);

        Book updateBook = bookDao.findById(5L)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        updateBook.setPrice(BigDecimal.valueOf(100));
        bookDao.update(updateBook);

        bookDao.deleteById(6L);

        List<Book> books = bookDao.findAll();
        for (Book b : books) {
            System.out.println(b);
        }
    }
}
