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
        Book book1 = new Book("book1", new BigDecimal("11.11"));
        Book book2 = new Book("book2", new BigDecimal("99.99"));
        bookDao.create(book1);
        bookDao.create(book2);

        Optional<Book> findByIdBook = bookDao.findById(book1.getId());
        findByIdBook.ifPresent(book -> {
            System.out.println("Book is found: " + book);
        });

        book2.setPrice(new BigDecimal("77.77"));
        bookDao.update(book2);
        System.out.println("Updated book: " + book2);

        boolean deletedBook = bookDao.deleteById(2L);
        System.out.println("The book is deleted: " + deletedBook);

        List<Book> bookList = bookDao.findAll();
        System.out.println("The list of books: " + bookList);
    }
}
