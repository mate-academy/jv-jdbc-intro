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

        Book newBook = new Book();
        newBook.setTitle(" The Divine Comedy");
        newBook.setPrice(new BigDecimal("329.99"));
        Book bookCreated = bookDao.create(newBook);
        System.out.println(bookCreated);

        Long bookIdToFind = 1L;
        Optional<Book> foundBook = bookDao.findById(bookIdToFind);
        foundBook.ifPresent(book1 -> {
            System.out.println("Found Book: " + book1.getTitle() + ", Price: " + book1.getPrice());
        });

        if (foundBook.isPresent()) {
            Book bookToUpdate = foundBook.get();
            bookToUpdate.setTitle("Title");
            bookToUpdate.setPrice(new BigDecimal("734.99"));
            bookDao.update(bookToUpdate);
            System.out.println("Book updated successfully.");
        }

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All Books:");
        for (Book book2 : allBooks) {
            System.out.println("Book Title: " + book2.getTitle() + ", Price: " + book2.getPrice());
        }

        Long bookIdToDelete = 2L;
        boolean isDeleted = bookDao.deleteById(bookIdToDelete);
        if (isDeleted) {
            System.out.println("Book with ID " + bookIdToDelete + " has been deleted.");
        } else {
            System.out.println("Book with ID "
                    + bookIdToDelete + " not found or couldn't be deleted.");
        }
    }
}
