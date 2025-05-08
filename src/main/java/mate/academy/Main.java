package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();
        System.out.println(bookDao.get(3L));

        Book kobzar = new Book();
        kobzar.setTitle("Kobzar");
        kobzar.setPrice(30);

        Book kobzarUpdated = bookDao.save(kobzar);
        System.out.println(kobzarUpdated);

    }
}

