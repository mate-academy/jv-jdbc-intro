package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.findById(1L));

        Book book = new Book();
        book.setTitle("Test book");
        book.setAuthor("Test author");
        book.setGenre("Test genre");
        Book createdBook = bookDao.create(book);
        System.out.println("Created Book ID: " + createdBook.getId());

        boolean isDeleted = bookDao.deleteById(createdBook.getId());
        System.out.println("Book deleted: " + isDeleted);

        Book bookUpdated = new Book();
        bookUpdated.setTitle("Test bookUpdated");
        bookUpdated.setAuthor("Test authorUpdated");
        bookUpdated.setGenre("Test genreUpdated");
        bookUpdated.setId(createdBook.getId());
        bookDao.update(bookUpdated);
    }
}
