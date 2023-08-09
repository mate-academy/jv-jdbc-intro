package mate.academy;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao =
                (BookDaoImpl) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("1984");
        book.setPrice(BigDecimal.valueOf(100));

        Book createdBook = bookDao.create(book);
        System.out.println("Created Book title: " + createdBook.getTitle()
                + " ,price: " + createdBook.getPrice());

        List<Book> allBooks = bookDao.getAll();
        System.out.println("All books:");
        for (Book manufacturer1 : allBooks) {
            System.out.print("Book: [" + "title: "
                    + manufacturer1.getTitle()
                    + " ,price: " + manufacturer1.getPrice() + "]" + "\n");
        }

        book.setTitle("Kobzar");
        book.setPrice(BigDecimal.valueOf(1000));
        Book updatedBook = bookDao.update(book);
        System.out.println("Updated Book: "
                + "[title: " + updatedBook.getTitle()
                + " price: " + book.getPrice() + "]");

        boolean isDeleted = bookDao.delete(book.getId());
        System.out.println("Book deleted: " + isDeleted);

        Optional<Book> manufacturerOptional =
                bookDao.get(createdBook.getId());
        manufacturerOptional.ifPresent(System.out::println);
    }
}
