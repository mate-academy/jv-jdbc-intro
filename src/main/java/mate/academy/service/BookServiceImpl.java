package mate.academy.service;

import mate.academy.dao.BookDao;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.models.Book;

import java.util.List;

public class BookServiceImpl implements BookService{
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        if (bookDao == null) {
            throw new DataProcessingException("The argument (bookDao) is null");
        }
        this.bookDao = bookDao;
    }

    @Override
    public Book save(Book book) {
        if (book == null) {
            throw new DataProcessingException("The argument (book) is null");
        }
        return bookDao.create(book);
    }

    @Override
    public Book get(Long id) {
        if (id == null) {
            throw new DataProcessingException("The argument (id) is null");
        }
        return bookDao.findById(id)
                .orElseThrow(() -> new DataProcessingException("Can't find a book with id: "
                        + id));
    }

    @Override
    public List<Book> getAll() {
        List<Book> bookList = bookDao.findAll();
        if (bookList.isEmpty()) {
            throw new DataProcessingException("Can't get any books from the database");
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        if (book == null) {
            throw new DataProcessingException("The argument (book) is null");
        }
        return bookDao.update(book);
    }

    @Override
    public boolean delete(Book book) {
        if (book == null || book.getId() == null) {
            throw new DataProcessingException("The argument (book) or id is null");
        }
        return bookDao.deleteById(book.getId());
    }
}
