package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        System.out.println("1. Testing CREATE operation: ");
        Book newBook = new Book();
        newBook.setTitle("Clean Code");
        newBook.setPrice(BigDecimal.valueOf(35.50)); // Заменили float на BigDecimal

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book savedbBook = bookDao.create(newBook);
        System.out.println("Saved Book: " + savedbBook);

        System.out.println("\n2. Testing READ (findById): ");
        Optional<Book> foundBook = bookDao.findById(savedbBook.getId());
        foundBook.ifPresent(book -> System.out.println("Found in db: "
                + book));

        System.out.println("\n3. Testing UPDATE operation: ");
        savedbBook.setPrice(BigDecimal.valueOf(45.50));
        savedbBook.setTitle("Clean Code (Updated Edition)");
        bookDao.update(savedbBook);
        System.out.println("Price and name successfully updated! For book: " + savedbBook);

        System.out.println("\n4. Testing READ ALL (findAll): ");
        List<Book> allBooks = bookDao.findAll();
        System.out.println("Total books in database: " + allBooks.size());
        for (Book book : allBooks) {
            System.out.println(" - " + book.getTitle() + " | Price: " + book.getPrice());
            System.out.println("Raw book data: " + book);
        }

        System.out.println("\n5. Testing DELETE Operation: ");
        boolean isDeleted = bookDao.deleteById(savedbBook.getId());
        System.out.println("Book successfully deleted? " + isDeleted);

        System.out.println("\n6. Final check that book really deleted. ");
        Optional<Book> checkDeleted = bookDao.findById(savedbBook.getId());
        System.out.println("Trying to find deleted book: "
                + (checkDeleted.isPresent()
                ? "Unluckily was Found"
                : "Empty as expected(as should be)"));
    }
}
