package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setId(book.getId());
        book.setTitle("tittle");
        book.setPrice(122);

        //CREATE
        Book saved = bookDao.create(book);
        System.out.println("Saved: " + saved);

        //READ
        System.out.println("Find by ID: " + bookDao.findById(saved.getId()));

        //UPDATE
        saved.setTitle("Updated book tittle");
        saved.setPrice(145);
        Book updated = bookDao.update(saved);
        System.out.println("Updated tittle: " + updated);

        //DELETE
        boolean isDeleted = bookDao.deleteId(book.getId());
        System.out.println("Was deleted: " + isDeleted);
    }
}
