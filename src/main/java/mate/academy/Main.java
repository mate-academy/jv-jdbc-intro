package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector
            .getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book();
        book1.setTitle("Book1");
        book1.setPrice(BigDecimal.valueOf(149.99));
        Book savedBook1 = bookDao.create(book1);

        Long idToFindBook = savedBook1.getId();
        Optional<Book> foundedBook1 = bookDao.findById(idToFindBook);

        List<Book> books = bookDao.findAll();

        savedBook1.setTitle("book1_1");
        savedBook1.setPrice(BigDecimal.valueOf(249.99));
        Book updatedSavedBook1 = bookDao.update(savedBook1);

        boolean isDeleted = bookDao.deleteById(savedBook1.getId());
    }
}
