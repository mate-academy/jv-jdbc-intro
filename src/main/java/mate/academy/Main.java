package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Eat that frog");
        book.setPrice(new BigDecimal(340));
        Book eatThatFrog = bookDao.create(book);

        Book book1 = new Book();
        book1.setTitle("One's near river");
        book1.setPrice(new BigDecimal(540));
        Book onesNearRiver = bookDao.create(book1);

        Optional<Book> bookById = bookDao.findById(2L);
        bookById.ifPresent(b -> System.out.println("Book \'" + b.getTitle() + "\' is present"));

        Book abetka = new Book();
        abetka.setId(2L);
        abetka.setTitle("For children");
        abetka.setPrice(new BigDecimal(140));
        Book updatedAbetka = bookDao.update(abetka);
        System.out.println("Updated book: " + updatedAbetka);

        boolean resultAfterDelete = bookDao.deleteById(7L);
        System.out.println("Is delete successful? " + resultAfterDelete);

        List<Book> books = bookDao.findAll();
        for (Book iterateBook : books) {
            System.out.println(iterateBook);
        }
    }
}
