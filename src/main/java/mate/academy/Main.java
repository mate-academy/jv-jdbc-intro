package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        
        Book book = new Book();
        book.setTitle("Dune");
        book.setPrice(BigDecimal.valueOf(450));

        Book createBook = bookDao.create(book);
        Book foundBook = bookDao.findById(createBook.getId())
                .orElseThrow(() -> new RuntimeException("Can't find book by id"));
        List<Book> all = bookDao.findAll();

        createBook.setPrice(BigDecimal.valueOf(500));
        Book updatedBook = bookDao.update(createBook);
        boolean deleteBook = bookDao.deleteById(createBook.getId());
    }
}
