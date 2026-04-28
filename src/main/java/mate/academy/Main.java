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
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println("---1. Тест Create---");
        Book book = new Book();
        book.setTitle("Spawn");
        book.setPrice(BigDecimal.valueOf(500.50));
        Book savedBook = bookDao.create(book);
        System.out.println("Create book " + savedBook);
        System.out.println("\n--- 2. Тест FINDBYID ---");
        Optional<Book> foundBook = bookDao.findById(savedBook.getId());
        System.out.println("Знайдено книгу за ID: "
                + (foundBook.isPresent() ? foundBook.get() : "Не знайдено"));
        System.out.println("\n--- 3. Тест FINDALL ---");
        List<Book> allBooks = bookDao.findAll();
        System.out.println("Всі книги в базі:");
        for (Book b : allBooks) {
            System.out.println(b);
        }
        System.out.println("\n--- 4. Тест UPDATE ---");
        savedBook.setTitle("The Lord of the Rings");
        savedBook.setPrice(BigDecimal.valueOf(999.99));
        Book updatedBook = bookDao.update(savedBook);
        System.out.println("Оновлено книгу: " + updatedBook);
        System.out.println("\n--- 5. Тест DELETE ---");
        boolean isDeleted = bookDao.deleteById(savedBook.getId());
        System.out.println("Книгу успішно видалено? " + isDeleted);
        System.out.println("\n--- Перевірка після видалення ---");
        System.out.println("Список книг після видалення: " + bookDao.findAll());
    }
}
