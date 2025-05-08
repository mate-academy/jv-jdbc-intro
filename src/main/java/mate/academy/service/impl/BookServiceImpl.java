package mate.academy.service.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.model.Book;
import mate.academy.service.BookService;

public class BookServiceImpl implements BookService {
    private BookDao bookDao;

    @Override
    public Book create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public Book update(Book book) {
        return bookDao.update(book);
    }

    @Override
    public boolean deleteById(Long id) {
        return bookDao.deleteById(id);
    }
}
