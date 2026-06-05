package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = bookDao.findById(1L).get();
        newBook.setPrice(new BigDecimal("49.99"));

        Book savedBook = bookDao.create(newBook);
        System.out.println("Successfully created book: " + savedBook);
        System.out.println();

        Long targetId = savedBook.getId();
        Optional<Book> bookOptional = bookDao.findById(targetId);
        if (bookOptional.isPresent()) {
            System.out.println("Found book: " + bookOptional.get());
        } else {
            System.out.println("Book not found!");
        }

        Book bookToUpdate = bookOptional.get();
        bookToUpdate.setPrice(new BigDecimal("55.00"));

        Book updatedBook = bookDao.update(bookToUpdate);
        System.out.println("Successfully updated book: " + updatedBook);

        List<Book> allBooks = bookDao.findAll();
        System.out.println("Total books currently in database: " + allBooks.size());

        boolean isDeleted = bookDao.deleteById(targetId);
        System.out.println("Was book deleted successfully? " + isDeleted);

        Optional<Book> deletedBookCheck = bookDao.findById(targetId);
        System.out.println("Does the deleted book still exist? " + deletedBookCheck.isPresent());
    }
}
