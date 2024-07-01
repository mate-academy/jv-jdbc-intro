package mate.academy.service;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.model.Book;

public class BookServiceImpl implements BookService {
    private BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public Optional<Book> readID(Long id) {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> readAll() {
        return bookDao.findAll();
    }

    @Override
    public Book update(Book book) {
        return bookDao.update(book);
    }

    @Override
    public boolean delete(Long id) {
        return bookDao.deleteById(id);
    }
}
