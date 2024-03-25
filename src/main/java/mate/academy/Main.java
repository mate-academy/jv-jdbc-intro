package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book firstBook = new Book();
        firstBook.setTitle("IT");
        firstBook.setPrice(100.99);

        Book secondBook = new Book();
        secondBook.setTitle("Java");
        secondBook.setPrice(88.90);

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        bookDao.create(firstBook);
        bookDao.create(secondBook);

        System.out.println(bookDao.findAll());

        System.out.println(bookDao.findById(1L).get());

        Book book = bookDao.findById(1L).get();
        book.setTitle("Updated Title");
        bookDao.update(book);
        System.out.println(bookDao.findById(1L).get());

        System.out.println(bookDao.deleteById(2L));
    }
}
