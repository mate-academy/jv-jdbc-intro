package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        //CREATE
        System.out.println("CREATE");
        Book book_1 = new Book();
        book_1.setTitle("DnD");
        book_1.setPrice(new BigDecimal(999));
        System.out.println(book_1 = bookDao.create(book_1));

        //CREATE
        System.out.println("CREATE");
        Book book_2 = new Book();
        book_2.setTitle("Necronomicon");
        book_2.setPrice(new BigDecimal(666));
        System.out.println(book_2 = bookDao.create(book_2));

        //UPDATE
        System.out.println("UPDATE");
        book_1.setTitle("DnD_2");
        book_1.setPrice(new BigDecimal(9999));
        System.out.println(bookDao.update(book_1));

        //SELECT by id
        System.out.println("SELECT by id");
        System.out.println(bookDao.findById(1L));

        //SELECT ALL
        System.out.println("SELECT ALL");
        System.out.println(bookDao.findAll());

        //DELETE by id
        System.out.println("DELETE by id");
        System.out.println(bookDao.deleteById(book_2.getId()));

        //SELECT ALL
        System.out.println("SELECT ALL");
        System.out.println(bookDao.findAll());
    }
}
