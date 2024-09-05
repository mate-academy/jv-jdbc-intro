package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static List<Book> books = List.of(new Book("Core Java Volume I – Fundamentals", BigDecimal.valueOf(29.99)),
            new Book("Effective Java", BigDecimal.valueOf(19.99)),
            new Book("Java - The Complete Reference", BigDecimal.valueOf(35.45)));

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
//create
        for (Book book : books) {
            System.out.println("Created record in DB: "
                    + bookDao.create(book));
        }

//read
        books = bookDao.findAll();
        System.out.println(System.lineSeparator() + "Records in DB:");
        books.forEach(System.out::println);

//update
        Book updatedBook = bookDao.findAll().stream()
                .filter(b -> Objects.equals(b.getTitle(), "Effective Java"))
                .findFirst()
                .get();
        updatedBook.setTitle("SQL in 10 Minutes");
        updatedBook.setPrice(BigDecimal.valueOf(24.99));

        System.out.println(System.lineSeparator() + "Updated in db: "
                + bookDao.update(updatedBook) + System.lineSeparator());

//delete
        books = bookDao.findAll();
        for (Book book : books) {
            System.out.println("Deleted record from DB: " + bookDao.deleteById(book.getId()));
        }
    }
}
