package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import mate.academy.service.BookServiceImpl;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static BookService service;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        service = new BookServiceImpl(bookDao);

        testCreate();
        testFindAll();
        testFindById();
        testUpdate();
        testDelete();
        testFindAll();

        service.deleteAll();
    }

    private static void testCreate() {
        System.out.println("\nCREATE");
        Book bookHarry = new Book("Harry", new BigDecimal(1222));
        Book bookPotter = new Book("Potter", new BigDecimal(999));
        Book bookLol = new Book("Lol", new BigDecimal(69));
        Book bookKek = new Book("Kek", new BigDecimal(1488));
        service.createBook(bookHarry);
        service.createBook(bookPotter);
        service.createBook(bookLol);
        service.createBook(bookKek);
    }

    private static void testFindAll() {
        System.out.println("\nFIND ALL");
        for (var book : service.findAllBooks()) {
            System.out.println(book);
        }
    }

    private static void testFindById() {
        System.out.println("\nFIND BY ID");
        System.out.println(service.findBookById(3L));
    }

    private static void testDelete() {
        System.out.println("\nDELETE");
        service.deleteBookById(4L);
    }

    private static void testUpdate() {
        System.out.println("\nUPDATE");
        Book bookForUpdate = new Book(3L, "Update", new BigDecimal(12345));
        service.updateBook(bookForUpdate);
    }
}
