package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;

/**
 * У main отримай BookDao через injector і протестуй всі CRUD-методи:
 * create → findById → findAll → update → deleteById.
 */
public class Main {
    public static void main(String[] args) {
        BookDao bookDao;
        Injector injector = Injector.getInstance("mate.academy");

        //bookDao.create(new Book());
    }
}
