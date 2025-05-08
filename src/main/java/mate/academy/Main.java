package mate.academy;

import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDaoImpl bookDao = new BookDaoImpl();
        bookDao.deleteById(16L);
        System.out.println(bookDao.findById(17L));
        System.out.println(bookDao.findAll().toString());
    }
}
