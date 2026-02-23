package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        //create
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Zbrodnia i Kara");
        book.setPrice(new BigDecimal("99.99"));
        bookDao.create(book);

        //read
        Book book2 = new Book();
        bookDao.findById(book2.getId());

        //update
        book.setTitle("Zbrodnia i Kara II");
        bookDao.update(book);

        //delete
        Book book4 = new Book();
        Long idRead = book4.getId();
        bookDao.deleteById(idRead);
    }

}
