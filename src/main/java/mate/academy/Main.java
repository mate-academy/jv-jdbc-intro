package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.Optional;

public class Main {
    private static BookDao bookDao;

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        bookDao = (BookDao) injector.getInstance(BookDao.class);

        testCreate();
        testUpdate();
        testDelete();
    }

    private static void testCreate() {
        System.out.printf("%nTesting book creation...%n");

        Book bookOne = new Book("bookOne", BigDecimal.valueOf(100));
        Book bookTwo = new Book("bookTwo", BigDecimal.valueOf(150));
        Book bookThree = new Book("bookThree", BigDecimal.valueOf(200));
        bookDao.create(bookOne);
        bookDao.create(bookTwo);
        bookDao.create(bookThree);

        bookDao.findAll().forEach(System.out::println);
    }

    private static void testUpdate() {
        System.out.printf("%nTesting book updating...%n");

        long id = 2L;
        Optional<Book> bookToUpdate = bookDao.findById(id);
        if (bookToUpdate.isEmpty()) {
            System.out.printf("Book with id %d not found%n", id);
            return;
        }
        Book toUpdate = bookToUpdate.get();
        toUpdate.setTitle("updatedBook");
        bookDao.update(toUpdate);

        bookDao.findById(id).ifPresent(System.out::println);
    }

    private static void testDelete() {
        System.out.printf("%nTesting book deletion...%n");

        long id = 2L;
        boolean isDeleted = bookDao.deleteById(id);
        if (isDeleted) {
            System.out.printf("Book with id %d deleted successfully%n", id);
        } else {
            System.out.println("Failed to delete book with id " + id);
        }

        bookDao.findAll().forEach(System.out::println);
    }
}
