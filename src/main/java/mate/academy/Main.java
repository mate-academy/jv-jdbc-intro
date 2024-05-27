package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final Book BOOK_1 = new Book("OnePiece", new BigDecimal(195));
    private static final Book BOOK_1_UPDATE = new Book(1L, "OnePice", new BigDecimal(250));
    private static final Book BOOK_2 = new Book("Naruto", new BigDecimal(235));
    private static final Book BOOK_3 = new Book("Jujutsu Kaisen", new BigDecimal(243));

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        bookDao.create(BOOK_1);
        bookDao.create(BOOK_2);
        bookDao.create(BOOK_3);

        bookDao.update(BOOK_1_UPDATE);

        List<Book> books = bookDao.findAll();
        System.out.println("All books:");
        for (Book book : books) {
            System.out.println(book);
        }

        Optional<Book> findById = bookDao.findById(1L);
        System.out.println("Book found by id: " + findById);

        bookDao.deleteById(3L);

        List<Book> listAfterDelete = bookDao.findAll();
        System.out.println("List after delete by id:");
        for (Book book : listAfterDelete) {
            System.out.println(book);
        }

    }
}
