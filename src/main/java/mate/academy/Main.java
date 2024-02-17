package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setPrice(BigDecimal.valueOf(15.6));
        Book createdBook = bookDao.create(book);
        Long createdBookId = createdBook.getId();
        Optional<Book> foundBookOptional = bookDao.findById(createdBookId);

        if (foundBookOptional.isPresent()) {
            Book foundBook = foundBookOptional.get();
            foundBook.setTitle("Updated Title");
            foundBook.setPrice(BigDecimal.valueOf(19.99));
            bookDao.update(foundBook);
            bookDao.deleteById(createdBookId);
        } else {
            System.out.println("Book not found");
        }
    }
}
