package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final BookDao BOOK_DAO = (BookDao) INJECTOR.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book first = new Book("Harry Potter", BigDecimal.valueOf(300));
        Book second = new Book("The Lord of the Rings", BigDecimal.valueOf(400));
        Book third = new Book("The Great Gatsby", BigDecimal.valueOf(500));
        BOOK_DAO.create(first);
        BOOK_DAO.create(second);
        BOOK_DAO.create(third);
        System.out.println(BOOK_DAO.findAll());
        first.setTitle("Harry Potter and Order of The Phoenix");
        BOOK_DAO.update(first);
        System.out.println(BOOK_DAO.findById(third.getId()));
        System.out.println(BOOK_DAO.deleteById(third.getId()));
    }
}
