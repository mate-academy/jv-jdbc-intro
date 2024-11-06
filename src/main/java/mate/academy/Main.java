package mate.academy;

import mate.academy.lib.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.lib.model.Book;
import java.math.BigDecimal;
import java.util.Optional;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        Book monteCristoBook = new Book();
        monteCristoBook.setTitle("The Count of Monte Cristo");
        monteCristoBook.setPrice(BigDecimal.valueOf(45.99));

        Book createBook = bookDao.create(monteCristoBook);
        System.out.println("Created book: " + createBook);

        Long bookId = createBook.getId();
        Optional<Book> retrievedBook = bookDao.findById(bookId);
        retrievedBook.ifPresent(System.out::println);

        createBook.setPrice(BigDecimal.valueOf(49.99));
        Book updateBook = bookDao.update(createBook);
        System.out.println("Update book: " + updateBook);

        bookDao.findAll().forEach((book) -> System.out.println("All books: " + book));

        boolean isDeleted = bookDao.deleteById(bookId);
        System.out.println("Book deletion status: " + isDeleted);

    }
}
