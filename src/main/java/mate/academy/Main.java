package mate.academy;

import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book1 = new Book("XXX", "xxxxxxxxxx");
        Book book2 = new Book("AAA", "aaaaaaaaa");

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        bookDao.create(book2);
        bookDao.create(book1);

        List<Book> allBooks = bookDao.getAll();
        for (Book book : allBooks) {
            System.out.println(book);
        }

        System.out.println(bookDao.get(1L));

        Book book3 = bookDao.get(2L).get();
        book3.setName("YYY");
        book3.setTitle("yyyyyyyy");
        bookDao.update(book3);

        System.out.println(bookDao.get(2L));

        bookDao.delete(1L);
    }
}
