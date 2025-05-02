package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book firstBook = new Book();
        Book secondBook = new Book();
        firstBook.setTitle("Kolobok");
        firstBook.setPrice(new BigDecimal(100));
        secondBook.setTitle("Lord of the rings");
        secondBook.setPrice(new BigDecimal(120));
        Book thirdBook = new Book();
        thirdBook.setTitle("Alice in Wonderland");
        thirdBook.setPrice(new BigDecimal(200));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        System.out.println("Create operation");
        System.out.println(bookDao.create(firstBook));
        System.out.println(bookDao.create(secondBook));
        System.out.println(bookDao.create(thirdBook));

        System.out.println("Find by Id = 2, operation");
        System.out.println(bookDao.findById(2L));

        System.out.println("Update operation");
        System.out.println(bookDao.update(new Book(1L, "1984", new BigDecimal(80))));

        System.out.println("Find all operation");
        for (Book book : bookDao.findAll()) {
            System.out.println(book);
        }

        System.out.println("Delete id = 2, operation");
        System.out.println(bookDao.deleteById(2L));

        System.out.println("Find all operation");
        for (Book book : bookDao.findAll()) {
            System.out.println(book);
        }
    }
}
