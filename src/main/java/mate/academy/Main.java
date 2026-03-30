package mate.academy;

import java.math.BigDecimal;
import java.sql.SQLException;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println("ВСІ КНИГИ В БАЗІ ДАНИХ");
        bookDao.findAll().forEach(System.out::println);
        System.out.println("КНИГА З ID 1:");
        bookDao.findById(1L).ifPresent(System.out::println);
        System.out.println("ДОДАВАННЯ НОВОЇ КНИГИ В БАЗУ");
        bookDao.create(new Book(null, "REDHOOD",new BigDecimal("48.90")));
        System.out.println("ВИДАЛЕННЯ З ID 2:");
        bookDao.deleteById(2L);
        System.out.println("ОНОВЛЕННЯ ДАНИХ");
        bookDao.update(new Book(1L, "UPDATEDBOOK",new BigDecimal("108.90")));
        System.out.println("ФІНАЛЬНИЙ РЕЗУЛЬТАТ");
        bookDao.findAll().forEach(System.out::println);
    }
}
