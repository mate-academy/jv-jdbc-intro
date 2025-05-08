package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book(3L,"History of Ukraine", Integer.valueOf(120));
        System.out.println(bookDao.save(book));
        System.out.println(bookDao.findById(1L));
        Book bookToUpdate = new Book(3L,"Modern history of Ukraine", Integer.valueOf(150));
        bookToUpdate.setId(3L);
        System.out.println(bookDao.update(bookToUpdate));
        System.out.println(bookDao.delete(3L));

    }
}
