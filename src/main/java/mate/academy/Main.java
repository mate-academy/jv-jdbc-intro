package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book bookOne = new Book("For example: Instructions for HIMARS", BigDecimal.valueOf(0.99));
        bookDao.create(bookOne);

        Book bookTwo = new Book("For example: The space is in you", BigDecimal.valueOf(40));
        bookDao.create(bookTwo);

        Book bookThree = new Book("For example:"
                + " How to live with a neighbour who is a moron", BigDecimal.valueOf(49.99));
        bookDao.create(bookThree);

        System.out.println(bookDao.findById(bookThree.getId()));
        System.out.println(bookDao.findById(11123L));

        bookTwo.setPrice(BigDecimal.valueOf(24.99));
        bookTwo.setTitle("For example: Space has left you");
        bookDao.update(bookTwo);
        System.out.println(bookTwo);

        List<Book> allBooks = bookDao.findAll();
        for (Book books : allBooks) {
            System.out.println(books);
        }

        if (bookDao.deleteById(bookTwo.getId())) {
            System.out.println("Delete book by ID: " + bookTwo.getId());
        }
    }
}
