package mate.academy;

import java.math.BigDecimal;
import java.util.List;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book();
        book1.setTitle("I am test book");
        book1.setPrice(BigDecimal.valueOf(1500.25));
        Book testBook1 = bookDao.create(book1);
        System.out.println(bookDao.findById(testBook1.getId()).get());
        Book book2 = new Book();
        book2.setTitle("I am test book 2");
        book2.setPrice(BigDecimal.valueOf(869.11));
        Book testBook2 = bookDao.create(book2);
        System.out.println(bookDao.findById(testBook2.getId()).get());
        book2.setTitle("New test 2 book");
        book2.setPrice(BigDecimal.valueOf(900.10));
        bookDao.update(book2);
        System.out.println(bookDao.findById(book2.getId()));
        printDeleteResult(bookDao.deleteById(book1.getId()),book1.getId());
        List<Book> bookList = bookDao.findAll();
        printAllBooks(bookList);
    }

    private static void printAllBooks(List<Book> bookList) {
        for(Book book : bookList) {
            System.out.println(book);
        }
    }

    private static void printDeleteResult(boolean isDeleteSuccessful, Long id) {
        if (isDeleteSuccessful) {
            System.out.println("Book with id: "+ id + " was deleted from DB successfully");
        }
    }
}
