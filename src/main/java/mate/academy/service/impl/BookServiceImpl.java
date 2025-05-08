package mate.academy.service.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;
import mate.academy.service.BookService;

public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book save(Book book) {
        return bookDao.save(book);
    }

    @Override
    public Optional<Book> get(Long id) {
        return Optional.ofNullable(bookDao.findById(id)
                .orElseThrow(() -> new DataProcessingException("Cannot find book by id: " + id)));
    }

    @Override
    public List<Book> getAll() {
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
