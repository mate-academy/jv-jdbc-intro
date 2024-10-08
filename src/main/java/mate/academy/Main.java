package mate.academy;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;
import mate.academy.util.InitDatabase;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        try {
            InitDatabase.executeInitScript(ConnectionUtil.getConnection());
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot initialize DataBase", e);
        }
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book testBook1 = bookDao.create(new Book("Harry Potter and the Philosophers Stone",
                BigDecimal.valueOf(9.99)));

        //findById method test
        Optional<Book> testFindBook = bookDao.findById(testBook1.getId());
        if (testFindBook.isPresent()) {
            Book test1 = testFindBook.get();
            System.out.println("Find by ID1 book: " + System.lineSeparator() + test1);
        }

        //Update method test
        testBook1.setPrice(BigDecimal.valueOf(5.99));
        bookDao.update(testBook1);
        System.out.println(System.lineSeparator()
                + "Suggested price changed from 9.99 to 5.99 on book with ID 1");
        bookDao.findAll().forEach(System.out::println);

        Book testBook2 = bookDao.create(new Book("Harry Potter and the Chamber of Secrets",
                BigDecimal.valueOf(10.99)));
        Book testBook3 = bookDao.create(new Book("Harry Potter and the Prisoner of Azkaban",
                BigDecimal.valueOf(11.99)));
        Book testBook4 = bookDao.create(new Book("Harry Potter and the Goblet of Fire",
                BigDecimal.valueOf(12.99)));
        Book testBook5 = bookDao.create(new Book("Harry Potter and the Order of the Phoenix",
                BigDecimal.valueOf(13.99)));
        Book testBook6 = bookDao.create(new Book("Harry Potter and the Half-Blood Prince",
                BigDecimal.valueOf(14.99)));
        Book testBook7 = bookDao.create(new Book("Harry Potter and the Deathly Hallows",
                BigDecimal.valueOf(15.99)));

        //FindAll method test
        System.out.println(System.lineSeparator() + "List of ALL books:");
        bookDao.findAll().forEach(System.out::println);

        Book[] testBooks = new Book[]{testBook1, testBook2, testBook3,
                testBook4, testBook5, testBook6, testBook7};

        //deleteById method test
        System.out.println(System.lineSeparator() + "Delete test (must be an empty list):");
        for (Book testBook : testBooks) {
            bookDao.deleteById(testBook.getId());
        }
        bookDao.findAll().forEach(System.out::println);
        bookDao.clearTable();
    }
}
