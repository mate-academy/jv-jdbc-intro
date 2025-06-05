package mate.academy;

import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy.dao");

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = new Book();
        newBook.setTitle("Clean Code");
        newBook.setPrice(450);
        Book savedBook = bookDao.save(newBook);
        System.out.println("Saved book: " + savedBook);

        Optional<Book> bookOptional = bookDao.findById(savedBook.getId());
        if (bookOptional.isPresent()) {
            System.out.println("Book found by findById: " + bookOptional.get());
        } else {
            System.out.println("Book not found by findById.");
        }

        savedBook.setTitle("Clean Code (Updated)");
        savedBook.setPrice(500);
        Book updatedBook = bookDao.update(savedBook);
        System.out.println("Updated book: " + updatedBook);

        boolean isDeleted = bookDao.delete(updatedBook);
        System.out.println("Deleted book: " + isDeleted);

        Optional<Book> afterDeleteOptional = bookDao.findById(updatedBook.getId());
        System.out.println("findById after delete (should be empty): " + afterDeleteOptional);
    }
}
