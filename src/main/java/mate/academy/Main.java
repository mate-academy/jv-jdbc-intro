package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println("ВСІ КНИГИ В БАЗІ ДАНИХ");
        bookDao.findAll().forEach(System.out::println);
        System.out.println("КНИГА З ID 1:");
        bookDao.findById(1L).ifPresent(System.out::println);
        System.out.println("ДОДАВАННЯ НОВОЇ КНИГИ В БАЗУ");
        try {
            bookDao.create(new Book(null, "REDHOOD",new BigDecimal("48.90")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("ВИДАЛЕННЯ З ID 2:");
        bookDao.deleteById(2L);
        System.out.println("ФІНАЛЬНИЙ РЕЗУЛЬТАТ");
        bookDao.findAll().forEach(System.out::println);
    }
}