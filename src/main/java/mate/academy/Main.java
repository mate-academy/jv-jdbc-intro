package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        for (int i = 0; i < 10; i++) {
            Book createBook = new Book();
            createBook.setTitle("Book " + (i + 1));
            createBook.setPrice(BigDecimal.valueOf(RandomGenerator.getDefault().nextDouble(100.0)));
            bookDao.create(createBook);
        }
        // findById
        Optional<Book> findBook = bookDao.findById(10L);
        findBook.ifPresent(System.out::println);
        // findAll
        List<Book> all = bookDao.findAll();
        for (Book books : all) {
            System.out.println(books);
        }
        // update book
        Optional<Book> updatedBook = bookDao.findById(1L);
        if (updatedBook.isPresent()) {
            Book book = updatedBook.get();
            book.setTitle("New title");
            book.setPrice(BigDecimal.valueOf(50.0));
            Book updated = bookDao.update(book);
            System.out.println(updated);
        }
        // delete book
        Optional<Book> deleteBook = bookDao.findById(1L);
        if (deleteBook.isPresent()) {
            Book book = deleteBook.get();
            boolean isDeleted = bookDao.deleteById(book.getId());
            System.out.println(isDeleted);
        }
    }
}
