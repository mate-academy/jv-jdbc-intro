package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");
    public static final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book firstBook = new Book();
        firstBook.setTitle("firstBook");
        firstBook.setPrice(50);

        Book secondBook = new Book();
        secondBook.setTitle("secondBook");
        secondBook.setPrice(70);

        Book thirdBook = new Book();
        thirdBook.setTitle("thirdBook");
        thirdBook.setPrice(35);

        Book updateBook = new Book();
        updateBook.setId(1L);
        updateBook.setTitle("updatedBook");
        updateBook.setPrice(100);

        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.create(thirdBook);

        System.out.println(bookDao.findAll());

        bookDao.update(updateBook);

        System.out.println(bookDao.findById(1L));

        bookDao.deleteById(2L);

        System.out.println(bookDao.findAll());
    }
}
