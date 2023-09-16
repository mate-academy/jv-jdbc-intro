package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final Long BOOK_FIND = 2L;
    private static final Long BOOK_UPDATE = 4L;
    private static final Long BOOK_DELETE = 4L;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        List<Book> books = bookDao.findAll();
        if (books.size() == 0) {
            books = createBooks(bookDao);
            System.out.println("There are no books in the books "
                        + "table and they are added");
        }
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i));
        }

        Optional<Book> resultFind = bookDao.findById(BOOK_FIND);
        if (resultFind.isPresent()) {
            System.out.println("Book found " + resultFind.get());
        } else {
            System.out.println("Book not found with id " + BOOK_FIND);
        }

        Book book = updateBook(bookDao);
        Book updateBook = bookDao.update(book);
        System.out.println("Updated book with id " + book.getId()
                + ": " + updateBook);

        boolean isDelete = bookDao.deleteById(BOOK_DELETE);
        if (isDelete) {
            System.out.println("Book deleted with id " + BOOK_DELETE);
        } else {
            System.out.println("Book for deleted not found with id " + BOOK_DELETE);
        }
    }

    public static List<Book> createBooks(BookDao bookDao) {
        List<Book> books = new ArrayList<>();

        Book inferno = new Book();
        inferno.setTitle("Inferno");
        inferno.setPrice(BigDecimal.valueOf(80.75));
        Book infernoUpdate = bookDao.create(inferno);
        books.add(infernoUpdate);

        Book santa = new Book();
        santa.setTitle("Santa claus");
        santa.setPrice(BigDecimal.valueOf(30.25));
        Book santaUpdate = bookDao.create(santa);
        books.add(santaUpdate);

        Book quixote = new Book();
        quixote.setTitle("Don Quixote");
        quixote.setPrice(BigDecimal.valueOf(40.50));
        Book quixoteUpdate = bookDao.create(quixote);
        books.add(quixoteUpdate);

        Book story = new Book();
        story.setTitle("Story about cat");
        story.setPrice(BigDecimal.valueOf(30.50));
        Book storyUpdate = bookDao.create(story);
        books.add(storyUpdate);

        return books;
    }

    public static Book updateBook(BookDao bookDao) {
        Book book = new Book();
        book.setId(BOOK_UPDATE);
        book.setTitle("Thinking in C++");
        book.setPrice(BigDecimal.valueOf(100.25));
        return book;
    }
}
