package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy.dao.impl");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Kameniari by Ivan Franko");
        book.setPrice(BigDecimal.valueOf(200));
        Book createdBook = bookDao.create(book);
        System.out.println(createdBook);

        Optional<Book> extractedBook = bookDao.findById(2L);
        System.out.println("The book by id: "
                + extractedBook.get().getId()
                + " is "
                + "named: " + extractedBook);

        List<Book> allBooks = bookDao.findAll();
        for (Book bookInfo : allBooks) {
            System.out.println(bookInfo);
        }

        Book updatedBook = new Book();
        updatedBook.setId(2L);
        updatedBook.setTitle("On the Road by M.Kotsiubynsky");
        updatedBook.setPrice(BigDecimal.valueOf(300));
        bookDao.update(updatedBook);
        System.out.println("Updated book with id: "
                + "'"
                + updatedBook.getId()
                + "' "
                + "was updated to:" + bookDao.findById(2L));

        Book deletedBook = new Book();
        deletedBook.setId(24L);
        boolean byId = bookDao.deleteById(24L);
        System.out.println("Book with id: "
                + "'"
                + deletedBook.getId()
                + "' "
                + "was deleted: "
                + byId);
    }
}
