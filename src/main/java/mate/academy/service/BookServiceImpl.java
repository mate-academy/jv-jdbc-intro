package mate.academy.service;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.model.Book;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book createBook(Book book) {
        return bookDao.create(book);
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public Book updateBook(Book book) {
        return bookDao.update(book);
    }

    @Override
    public boolean deleteBookById(Long id) {
        return bookDao.deleteById(id);
    }

    @Override
    public boolean deleteAll() {
        return bookDao.deleteAll();
    }
}
