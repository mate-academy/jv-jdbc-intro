package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        System.out.println("### 1. Insert 3 book: ###");
        Book lordOfTheRings = new Book();
        lordOfTheRings.setTitle("Lord Of The Rings");
        lordOfTheRings.setPrice(BigDecimal.valueOf(225));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println(bookDao.create(lordOfTheRings));
        Book theSongOfIceAndFire = new Book();
        theSongOfIceAndFire.setTitle("Song of Ice and Fire");
        theSongOfIceAndFire.setPrice(BigDecimal.valueOf(300));
        System.out.println(bookDao.create(theSongOfIceAndFire));
        Book theFightClub = new Book();
        theFightClub.setTitle("The Fight Club");
        theFightClub.setPrice(BigDecimal.valueOf(160));
        System.out.println(bookDao.create(theFightClub));

        System.out.println("### 2. Get every book by id: ###");
        System.out.println(bookDao.findById(1L).get());
        System.out.println(bookDao.findById(2L).get());
        System.out.println(bookDao.findById(3L).get());

        System.out.println("### 3. Get all books: ###");
        System.out.println(bookDao.findAll());

        System.out.println("### 4. Delete book by id: ###");
        System.out.println("List of books before deleting book by id - 3: ");
        System.out.println(bookDao.findAll());
        System.out.println("Return of method deleteById: " + bookDao.deleteById(3L));
        System.out.println("List of books after deleting book by id - 3: ");
        System.out.println(bookDao.findAll());

        System.out.println("### 5. Update book by id: ###");
        Book bookToUpdate = bookDao.findById(2L).get();
        System.out.println("Book to update by id - 2 from DB: " + bookToUpdate);
        bookToUpdate.setTitle("UpdatedTitle");
        bookToUpdate.setPrice(BigDecimal.valueOf(999));
        System.out.println("Set title to: " + bookToUpdate.getTitle());
        System.out.println("Set price to: " + bookToUpdate.getPrice());
        System.out.println("Return of method update: " + bookDao.update(bookToUpdate));
        Book updatedBook = bookDao.findById(2L).get();
        System.out.println("Get updated book by id - 2 from DB: " + updatedBook);
    }
}
