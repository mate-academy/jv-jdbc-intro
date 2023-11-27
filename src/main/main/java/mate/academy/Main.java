package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
    }
}
