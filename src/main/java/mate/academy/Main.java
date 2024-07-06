package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("second book");
        book.setPrice(200);
        Book bookFromDB = bookDao.create(book);
        System.out.println(bookFromDB);
        // test other methods from BookDao
    }
}
