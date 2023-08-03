package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = bookDao.findById(2L).get();
        book.setTitle("pun");
        System.out.println(bookDao.update(book));

        bookDao.deleteById(3L);

        System.out.println(bookDao.findAll());
    }
}
