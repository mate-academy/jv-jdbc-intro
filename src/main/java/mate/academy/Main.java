package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        System.out.println("Find by id 1 " + bookDao.findById(1L));

        Book newBook = new Book("Цветы для Элджернона", BigDecimal.valueOf(350));
        Book createdBook = bookDao.create(newBook);
        System.out.println("Created book " + createdBook);

        Book bookToUpdate = new Book(3L, "Тёмные начала", BigDecimal.valueOf(500));
        Book updated = bookDao.update(bookToUpdate);
        System.out.println("Updated book " + updated);

        System.out.println("Is deleted book by id 2 " + bookDao.deleteById(2L));

        for (Book book : bookDao.findAll()) {
            System.out.println(book);
        }
    }
}
