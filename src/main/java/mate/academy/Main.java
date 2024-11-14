package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Book;
import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.lib");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // Створення нової книги
        Book book = new Book();
        book.setTitle("New Book");
        book.setPrice(new BigDecimal("19.99"));

        // Додавання книги в базу
        Book createdBook = bookDao.create(book);
        System.out.println("Created Book: " + createdBook);

        // Пошук книги за ID
        Optional<Book> foundBook = bookDao.findById(createdBook.getId());
        foundBook.ifPresent(b -> System.out.println("Found Book: " + b));

        // Оновлення книги
        createdBook.setTitle("Updated Title");
        bookDao.update(createdBook);
        System.out.println("Updated Book: " + createdBook);

        // Отримання всіх книг
        List<Book> books = bookDao.findAll();
        books.forEach(b -> System.out.println("Book: " + b));

        // Видалення книги за ID
        boolean isDeleted = bookDao.deleteById(createdBook.getId());
        System.out.println("Deleted: " + isDeleted);
    }
}
