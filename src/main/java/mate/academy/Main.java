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

        // Create object of Harry Potter Book
        Book hpBook = new Book();
        hpBook.setTitle("Harry Potter");
        hpBook.setPrice(new BigDecimal(500));

        // Create object of The Lord of The Rings Book
        Book loTrBook = new Book();
        loTrBook.setTitle("The Lord of The Rings Book");
        loTrBook.setPrice(new BigDecimal(1000));

        // Create (add to DB) new book
        Book newHpBook = bookDao.create(hpBook);
        System.out.println(newHpBook);
        Book newLoTrBook = bookDao.create(hpBook);
        System.out.println(newLoTrBook);

        // Find book by id
        Optional<Book> bookById = bookDao.findById(newLoTrBook.getId());
        if (bookById.isPresent()) {
            Book book1 = bookById.get();
            System.out.println(book1);
        }

        // Find all books
        List<Book> findAllBooks = bookDao.findAll();
        System.out.println(findAllBooks);

        // Update book
        loTrBook.setId(hpBook.getId());
        Book updatedBook = bookDao.update(newLoTrBook);
        System.out.println(updatedBook);

        // Delete book by id
        boolean isBookDeleted = bookDao.deleteById(loTrBook.getId());
        System.out.println(isBookDeleted);
    }
}
