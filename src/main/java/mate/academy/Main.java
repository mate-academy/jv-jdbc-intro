package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book("The Silent Patient", new BigDecimal("18.50"));
        Book book2 = new Book("Where the Crawdads Sing", new BigDecimal("14.99"));

        Book createdBook1 = bookDao.create(book1);
        Book createdBook2 = bookDao.create(book2);

        List<Book> allBooks = bookDao.findAll();

        Long idToFind = createdBook1.getId();
        Optional<Book> foundBook = bookDao.findById(idToFind);

        // 4. UPDATE
        if (foundBook.isPresent()) {
            Book bookToUpdate = foundBook.get();
            bookToUpdate.setPrice(new BigDecimal("22.00"));
            bookToUpdate.setTitle("The Silent Patient (Updated Edition)");
            bookDao.update(bookToUpdate);
        }

        Long idToDelete = createdBook2.getId();
        bookDao.deleteById(idToDelete);

        List<Book> finalBooks = bookDao.findAll();
    }
}

