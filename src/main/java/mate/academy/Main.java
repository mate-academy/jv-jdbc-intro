package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("Misery");
        book.setPrice(400);
        System.out.println("Book table values before create function :");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.findAll().forEach(System.out::println);
        Book newBook = bookDao.create(book);

        System.out.println("Book table values after create function:");
        bookDao.findAll().forEach(System.out::println);
        Book bookById = bookDao.findById(newBook.getId()).orElseThrow(()
                -> new RuntimeException("Cant find the book with id: "
                + newBook.getId()));

        System.out.println("Book find by id: " + newBook.getId() + " -> " + bookById);

        newBook.setTitle("Call of cthulhu");
        newBook.setPrice(600);
        bookDao.update(newBook);
        System.out.println("Book table values after update function:");
        bookDao.findAll().forEach(System.out::println);

        bookDao.deleteById(newBook.getId());
        System.out.println("Book table values after delete function:");
        bookDao.findAll().forEach(System.out::println);
    }
}
