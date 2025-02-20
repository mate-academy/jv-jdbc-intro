package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookStore {

    private static final Logger logger = LoggerFactory.getLogger(BookStore.class);
    private static final Injector injector = Injector.getInstance("mate.academy.dao");
    
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        
        Book book = new Book();
        book.setTitle("Elegant JS");
        book.setPrice(BigDecimal.valueOf(1999.90));
        logger.info("Created book: {}",
                bookDao.create(book));
        
        logger.info("findById() method ----------------------->");
        bookDao.findById(2L)
                .ifPresentOrElse(foundBook -> logger.info("Book found: {}",
                                foundBook),
                        () -> logger.warn("Book with ID 2 not found"));
        
        List<Book> allBooks = bookDao.findAll();
        allBooks.forEach(bookItem -> logger.info("Book: {}",
                bookItem));
        
        logger.info("deleteById() method ------------------------>");
        logger.info("Is book deleted: {}",
                bookDao.deleteById(2L));
        
        logger.info("update() method ------------------------>");
        Book book1 = new Book();
        book1.setId(3L);
        book1.setTitle("Updated title");
        book1.setPrice(BigDecimal.valueOf(1999.90));
        logger.info("Updated book: {}",
                bookDao.update(book1));
    }
}
