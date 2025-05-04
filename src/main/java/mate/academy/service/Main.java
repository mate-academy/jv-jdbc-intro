package mate.academy.service;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector
            = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Java Programming");
        book.setPrice(BigDecimal.valueOf(29.99));

        bookDao.create(book);
        System.out.println("The book added: " + book);

        Book foundBook = bookDao.findById(book.getId()).orElse(null);
        if (foundBook == null) {
            System.out.println("The book with ID " + book.getId() + " was not found.");
        } else {
            System.out.println("Found book: " + foundBook);
        }

        book.setTitle("Advanced Java Programming");
        book.setPrice(BigDecimal.valueOf(34.99));
        bookDao.update(book);
        System.out.println("The book was updated: " + bookDao.findById(book.getId()).orElse(null));

        System.out.println("List all books:");
        for (Book books : bookDao.findAll()) {
            System.out.println(books);
        }

        boolean isDeleted = bookDao.deleteById(book.getId());
        System.out.println("The book was deleted: " + isDeleted);
    }
}
