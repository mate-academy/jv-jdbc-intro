package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {

    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("test book");
        book.setPrice(BigDecimal.valueOf(99.99));
        try {
            Book createdBook = bookDao.create(book);
            if (createdBook.getId() != null) {
                Long bookId = createdBook.getId();

                Optional<Book> bookToUpdate = bookDao.findById(bookId);
                bookToUpdate.ifPresent(updateBook -> {
                    updateBook.setTitle("new title");
                    updateBook.setPrice(BigDecimal.valueOf(12));
                    bookDao.update(updateBook);

                    bookDao.deleteById(updateBook.getId());
                });
            } else {
                System.out.println("Failed to retrieve the ID of the created book.");
            }

        } catch (DataProcessingException e) {
            throw new DataProcessingException("Data processing error occurred", e);
        }
    }
}
