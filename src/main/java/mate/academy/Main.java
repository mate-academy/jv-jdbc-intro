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
        Book firstBook = new Book();
        firstBook.setTitle("The Pragmatic Programmer");
        firstBook.setPrice(new BigDecimal("19.99"));
        Book createdBook = bookDao.create(firstBook);

        Book bookFromDB = bookDao.findById(createdBook.getId()).get();
        System.out.println(createdBook.equals(bookFromDB));

        Book secondBook = new Book();
        secondBook.setTitle("The Pragmatic Programmer");
        secondBook.setPrice(new BigDecimal("19.99"));
        bookDao.create(secondBook);

        List<Book> books = bookDao.findAll();
        System.out.println(books);

        Book updateBook = books.get(0);
        updateBook.setTitle("Effective Java");
        updateBook.setPrice(new BigDecimal("24.99"));
        bookDao.update(updateBook);

        List<Book> updatedBooks = bookDao.findAll();
        System.out.println(updatedBooks);

        boolean delete = bookDao.deleteById(updatedBooks.get(0).getId());
        System.out.println(delete);
        List<Book> finalBooks = bookDao.findAll();
        System.out.println(finalBooks);
    }
}
