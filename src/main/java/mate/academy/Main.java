package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BookDao BOOK_DAO = (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setPrice(BigDecimal.valueOf(3.21));
        System.out.println("Create method:");
        book = BOOK_DAO.create(book);
        System.out.println(book.toString());
        System.out.println("Find by id method:");
        Optional<Book> book2 = BOOK_DAO.findById(1L);
        book2.ifPresent(System.out::println);
        System.out.println("Update method:");
        book.setTitle("Car Magazine");
        Book update = BOOK_DAO.update(book);
        System.out.println(update.toString());
        System.out.println("Delete by id method:");
        boolean deleted = BOOK_DAO.deleteById(9L);
        System.out.println(deleted);
        System.out.println("Find all method:");
        BOOK_DAO.findAll().forEach(System.out::println);
    }
}
