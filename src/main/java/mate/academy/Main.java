package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {

    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Old book");
        book.setPrice(BigDecimal.valueOf(15L));
        Book createdBookInDB = bookDao.create(book);
        Long createdBookId = createdBookInDB.getId();
        Optional<Book> foundedBook = bookDao.findById(createdBookId);

        if (foundedBook.isPresent()) {
            Book bookFromOptional = foundedBook.get();
            bookFromOptional.setPrice(BigDecimal.valueOf(12L));
            bookFromOptional.setTitle("New book");
            bookDao.update(bookFromOptional);
        }

        List<Book> books = bookDao.findAll();
        for (Book book1 : books) {
            System.out.println(book1);
        }
    }
}
