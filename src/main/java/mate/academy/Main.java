package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate");
    private static final BookDao BOOK_DAO = (BookDao) INJECTOR.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book firsBook = new Book("firstBook", BigDecimal.valueOf(123));
        Book secondBook = new Book("secondBook", BigDecimal.valueOf(321));

        BOOK_DAO.create(firsBook);
        BOOK_DAO.create(secondBook);

        System.out.println(BOOK_DAO.findAll());

        secondBook.setTitle("thirdBook");
        BOOK_DAO.update(secondBook);
        System.out.println(BOOK_DAO.findById(secondBook.getId()));

        System.out.println(BOOK_DAO.deleteById(secondBook.getId()));
    }
}
