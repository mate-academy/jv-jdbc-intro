package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao;
        bookDao = (BookDao) injector.getInstance(BookDao.class);

        // 1. Создаем книгу
        Book book = new Book();
        book.setTitle("Java Theory");
        book.setPrice(new BigDecimal("500.00"));
        bookDao.create(book);
        System.out.println("Created: " + book);

        // 2. Обновляем (меняем цену)
        book.setPrice(new BigDecimal("10.00"));
        bookDao.update(book);
        System.out.println("Updated (price 10.00): " + bookDao.findById(book.getId()));

        // 3. Смотрим весь список
        System.out.println("All books before delete: " + bookDao.findAll());

        // 4. Удаляем
        bookDao.deleteById(book.getId());
        System.out.println("All books after delete: " + bookDao.findAll());
    }
}
