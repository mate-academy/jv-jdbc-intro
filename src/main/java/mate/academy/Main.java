package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.lib.BookDao;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();

        Long bookIdToUpdate = 1L;

        Optional<Book> foundBook = bookDao.findById(bookIdToUpdate);

        if (foundBook.isPresent()) {
            Book bookToUpdate = foundBook.get();
            bookToUpdate.setPrice(new BigDecimal(150));

            Book updatedBook = bookDao.update(bookToUpdate);

            System.out.println("Updated Book: " + updatedBook);
        } else {
            System.out.println("No book found with ID: " + bookIdToUpdate);
        }
    }
}
