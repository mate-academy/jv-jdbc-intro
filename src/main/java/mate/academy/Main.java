package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book fieldsOfBlood = new Book("Fields of Blood", BigDecimal.valueOf(450.00));
        bookDao.create(fieldsOfBlood);
        System.out.println("Created: " + fieldsOfBlood);

        Book kobzar = new Book("Kobzar", BigDecimal.valueOf(350.00));
        bookDao.create(kobzar);

        Optional<Book> foundBook = bookDao.findById(fieldsOfBlood.getId());
        System.out.println("Found by ID: " + foundBook.orElseThrow());

        fieldsOfBlood.setPrice(BigDecimal.valueOf(500.50));
        bookDao.update(fieldsOfBlood);
        System.out.println("Updated: " + bookDao.findById(fieldsOfBlood.getId()).orElse(null));

        System.out.println("All books before deletion: " + bookDao.findAll());

        boolean isDeleted = bookDao.deleteById(kobzar.getId());
        System.out.println("Is 'Kobzar' deleted?: " + isDeleted);
        System.out.println("All books after deletion: " + bookDao.findAll());

    }
}



