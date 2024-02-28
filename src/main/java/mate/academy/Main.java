package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BooksDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final String PACKAGE = "mate.academy";
    private static final Injector injector = Injector.getInstance(PACKAGE);

    public static void main(String[] args) {
        BooksDao booksDao = (BooksDao) injector.getInstance(BooksDao.class);

        List<Book> allBooks = booksDao.findAll();
        System.out.println("All books: " + allBooks);

        Long bookToDeleteId = 2L;
        boolean isDeleted = booksDao.deleteById(bookToDeleteId);
        if (isDeleted) {
            System.out.println("Book deleted successfully with ID: " + bookToDeleteId);
        } else {
            System.out.println("Book not found or deletion failed with ID: " + bookToDeleteId);
        }

        Long bookId = 4L;
        Optional<Book> bookOptional = booksDao.findById(bookId);
        if (bookOptional.isPresent()) {
            Book foundBook = bookOptional.get();
            System.out.println("Found book: " + foundBook);
        } else {
            System.out.println("Book not found with ID: " + bookId);
        }

        Long bookToUpdateId = 2L;
        Optional<Book> bookToUpdateOptional = booksDao.findById(bookToUpdateId);
        if (bookToUpdateOptional.isPresent()) {
            Book bookToUpdate = bookToUpdateOptional.get();
            bookToUpdate.setTitle("test11");
            bookToUpdate.setPrice(BigDecimal.valueOf(1111));
            booksDao.update(bookToUpdate);
            System.out.println("Book updated: " + bookToUpdate);
        } else {
            System.out.println("Book not found with ID: " + bookToUpdateId);
        }

        Book book = new Book();
        book.setId(6L);
        book.setTitle("1122");
        book.setPrice(BigDecimal.valueOf(124));
        booksDao.create(book);
    }
}
