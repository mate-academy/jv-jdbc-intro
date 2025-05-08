package mate.academy;

import java.math.BigDecimal;
import java.util.function.IntUnaryOperator;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Lord Of The Ring", BigDecimal.valueOf(100));
        Book book2 = new Book("Bible", BigDecimal.valueOf(20));

        System.out.println("We created those books:");
        Book lordOfTheRingWithId = bookDao.create(book);
        System.out.println(lordOfTheRingWithId);
        Book bibleWithId = bookDao.create(book2);
        System.out.println(bibleWithId);

        System.out.println("Find method tests");
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findById(2L));
        System.out.println(bookDao.findById(5L));

        IntUnaryOperator operator = n -> n * n;
        int result = operator.applyAsInt(10);

        System.out.println("Update books:");
        lordOfTheRingWithId.setTitle("Hobbit");
        lordOfTheRingWithId.setPrice(BigDecimal.valueOf(50));
        System.out.println("Updated book: " + bookDao.update(lordOfTheRingWithId));

        System.out.println("Delete book");
        System.out.println("Delete Lord Of The Ring - " + bookDao.deleteById(1L));
        System.out.println("Delete Bible - " + bookDao.deleteById(2L));
    }
}
