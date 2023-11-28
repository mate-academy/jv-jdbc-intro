package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    private static final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        //CREATE
        System.out.println("CREATE");
        Book book1 = new Book();
        book1.setTitle("DnD");
        book1.setPrice(new BigDecimal(999));
        System.out.println(book1 = bookDao.create(book1));

        //CREATE
        System.out.println("CREATE");
        Book book2 = new Book();
        book2.setTitle("Necronomicon");
        book2.setPrice(new BigDecimal(666));
        System.out.println(book2 = bookDao.create(book2));

        //UPDATE
        System.out.println("UPDATE");
        book1.setTitle("DnD_2");
        book1.setPrice(new BigDecimal(9999));
        System.out.println(bookDao.update(book1));

        //SELECT by id
        System.out.println("SELECT by id");
        System.out.println(bookDao.findById(1L));

        //SELECT ALL
        System.out.println("SELECT ALL");
        System.out.println(bookDao.findAll());

        //DELETE by id
        System.out.println("DELETE by id");
        System.out.println(bookDao.deleteById(book2.getId()));

        //SELECT ALL
        System.out.println("SELECT ALL");
        System.out.println(bookDao.findAll());
    }
}
