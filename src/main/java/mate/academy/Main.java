package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        bookDao.create(book);
        bookDao.update(book);
        bookDao.findAll();
        Long id = book.getId();
        bookDao.deleteById(id);
        bookDao.findById(id);

    }
}
