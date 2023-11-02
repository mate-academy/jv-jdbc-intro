package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book cleanCode = new Book("Clean code", BigDecimal.valueOf(35.99));
        Book headFirstJava = new Book("Head First Java", BigDecimal.valueOf(25.50));
        Book optimizingJava = new Book("Optimizing Java", BigDecimal.valueOf(98.50));

        System.out.println("Create operation:");
        Book cleanCodeDB = bookDao.create(cleanCode);
        Book headFirstJavaDB = bookDao.create(headFirstJava);
        Book optimizingJavaDB = bookDao.create(optimizingJava);
        System.out.println(cleanCodeDB + System.lineSeparator()
                + headFirstJavaDB + System.lineSeparator()
                + optimizingJavaDB + System.lineSeparator());

        System.out.println("Find by id operation:");
        long cleanCodeId = cleanCodeDB.getId();
        Book cleanCodeFound = bookDao.findById(cleanCodeId).orElseThrow();
        long headFirstJavaId = headFirstJavaDB.getId();
        Book headFirstJavaFound = bookDao.findById(headFirstJavaId).orElseThrow();
        long optimizingJavaId = optimizingJavaDB.getId();
        Book optimizingJavaFound = bookDao.findById(optimizingJavaId).orElseThrow();
        System.out.println(cleanCodeFound + System.lineSeparator()
                + headFirstJavaFound + System.lineSeparator()
                + optimizingJavaFound + System.lineSeparator());

        System.out.println("Find all operation:");
        List<Book> allBooks = bookDao.findAll();
        for (Book book : allBooks) {
            System.out.println(book);
        }

        System.out.println(System.lineSeparator() + "Update operation:" + System.lineSeparator()
                + "Old book:");
        Book newBook = new Book(headFirstJavaId, "UPDATED", BigDecimal.valueOf(125));
        Book oldBook = bookDao.update(newBook);
        System.out.println(oldBook);

        System.out.println("New book:");
        newBook = bookDao.findById(headFirstJavaId).orElseThrow();
        System.out.println(newBook + System.lineSeparator());

        System.out.println("Delete by id operation:");
        System.out.println(bookDao.deleteById(headFirstJavaId));
    }
}
