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

        Book book1 = new Book();
        book1.setTitle("Flash");
        book1.setPrice(BigDecimal.valueOf(35.99));

        bookDao.create(book1);

        Book book2 = new Book();
        book2.setTitle("Per un pugno di dollari");
        book2.setPrice(BigDecimal.valueOf(42.99));

        bookDao.create(book2);

        Book book3 = new Book();
        book3.setTitle("Akira");
        book3.setPrice(BigDecimal.valueOf(109.99));

        bookDao.create(book3);

        bookDao.deleteById(1L);

        List<Book> listOfBooks = bookDao.findAll();

        for (Book book : listOfBooks) {
            System.out.println(book);
        }

        System.out.println("=======================");

        Long bookId = 2L;
        Book bookToUpdate = bookDao.findById(bookId).orElseThrow(()
                -> new IllegalStateException("Book with id " + bookId + " not found"));

        System.out.println("Before");
        System.out.println(bookToUpdate);

        bookToUpdate.setTitle("Rambo");
        bookToUpdate.setPrice(BigDecimal.valueOf(109.99));

        Book updatedBook = bookDao.update(bookToUpdate);

        System.out.println("After");
        System.out.println(updatedBook);
    }
}
