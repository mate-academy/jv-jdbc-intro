package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.repository.BookDao;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    public static final List<Book> library;

    static {
        Book book1 = new Book();
        book1.setTitle("\"Flowers for Algernon\" Daniel Keyes");
        book1.setPrice(BigDecimal.valueOf(8));

        Book book2 = new Book();
        book2.setTitle("\"Shining\" Stephen King");
        book2.setPrice(BigDecimal.valueOf(13));

        Book book3 = new Book();
        book3.setTitle("\"Fight Club\" Chuck Palahniuk");
        book3.setPrice(BigDecimal.valueOf(20));

        library = List.of(book1, book2, book3);
    }

    public static void main(String[] args) {
        //Get BookDao instance using Injector
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        //Create new books in the database
        library.forEach(bookDao::create);

        //Read all books from the database and compare it with library list
        List<Book> booksFromDB = bookDao.findAll();
        System.out.println("Books from database:");
        booksFromDB.forEach(System.out::println);

        //Find by id
        Book bookFromLib = library.get(0);
        Book bookFromDb = bookDao.findById(bookFromLib.getId()).orElseThrow();
        System.out.println("Book from db: " + bookFromDb + System.lineSeparator());

        //Update
        bookFromLib.setTitle("Updated title");
        bookDao.update(bookFromLib);
        bookFromDb = bookDao.findById(bookFromLib.getId()).orElseThrow();
        System.out.println("Expect that title is updated: "
                + bookFromDb + System.lineSeparator());

        //Delete by id
        bookDao.deleteById(bookFromLib.getId());

        //Delete all books in the database
        library.forEach(book -> bookDao.deleteById(book.getId()));
    }
}
