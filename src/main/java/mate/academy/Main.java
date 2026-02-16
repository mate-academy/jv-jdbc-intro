package mate.academy;

import dao.BookDao;
import java.math.BigDecimal;
import mate.academy.lib.Injector;
import model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // 1. CREATE
        Book myBook = new Book();
        myBook.setTitle("Wyprawa w Tatry");
        myBook.setPrice(BigDecimal.valueOf(49.99));
        bookDao.create(myBook);
        System.out.println("Utworzono: " + myBook);

        // 2. FIND BY ID
        Book foundBook = bookDao.findById(myBook.getId()).orElseThrow(
                () -> new RuntimeException("Nie znaleziono książki o ID: " + myBook.getId())
        );
        System.out.println("Znaleziono: " + foundBook);

        // 3. UPDATE
        foundBook.setTitle("Wyprawa w Tatry - Wydanie II");
        bookDao.update(foundBook);
        Book updatedBook = bookDao.findById(myBook.getId()).get();
        System.out.println("Zaktualizowano: " + updatedBook);

        // 4. FIND ALL
        System.out.println("Wszystkie książki: " + bookDao.findAll());

        // 5. DELETE
        boolean isDeleted = bookDao.deleteById(myBook.getId());
        System.out.println("Czy usunięto? " + isDeleted);
        System.out.println("Czy nadal istnieje? " + bookDao.findById(myBook.getId()).isPresent());
    }
}
