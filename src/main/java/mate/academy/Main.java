import dao.BookDao;
import mate.academy.lib.Injector;
import model.Book;

import java.math.BigDecimal;

public static void main(String[] args) {
    Injector injector = Injector.getInstance("mate.academy");
    BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

    // 1. CREATE
    Book book = new Book();
    book.setTitle("Wyprawa w Tatry");
    book.setPrice(BigDecimal.valueOf(49.99));
    bookDao.create(book);
    System.out.println("Utworzono: " + book);

    // 2. FIND BY ID
    Book foundBook = bookDao.findById(book.getId()).orElseThrow(
            () -> new RuntimeException("Nie znaleziono książki o ID: " + book.getId())
    );
    System.out.println("Znaleziono: " + foundBook);

    // 3. UPDATE
    foundBook.setTitle("Wyprawa w Tatry - Wydanie II");
    bookDao.update(foundBook);
    System.out.println("Zaktualizowano: " + bookDao.findById(book.getId()).get());

    // 4. FIND ALL
    System.out.println("Liczba wszystkich książek: " + bookDao.findAll().size());

    // 5. DELETE
    boolean isDeleted = bookDao.deleteById(book.getId());
    System.out.println("Czy usunięto? " + isDeleted);
    System.out.println("Czy nadal istnieje? " + bookDao.findById(book.getId()).isPresent());
}