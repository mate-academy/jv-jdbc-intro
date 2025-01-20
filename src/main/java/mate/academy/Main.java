package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();

        // create()
        Book book1 = new Book("book1", new BigDecimal("120.0"));
        book1 = bookDao.create(book1);
        Book book2 = new Book("book2", new BigDecimal("250.0"));
        book2 = bookDao.create(book2);
        Book book3 = new Book("book3", new BigDecimal("370.0"));
        book3 = bookDao.create(book3);

        // findAll()
        List<Book> bookList = bookDao.findAll();
        System.out.println(bookList);

        // deleteById()
        bookDao.deleteById(book3.getId());

        // findById()
        Long idToFind = book3.getId();
        Optional<Book> bookById = bookDao.findById(idToFind);

        if (bookById.isPresent()) {
            System.out.println("We found book with id = "
                    + idToFind + ": ");
            System.out.println(bookById.get().getTitle());
        } else {
            System.out.println("There is no book with id = " + idToFind);
        }

        // update()
        Book bookToUpdate = book1;
        bookToUpdate.setPrice(new BigDecimal("150.0"));
        bookDao.update(bookToUpdate);

    }
}
