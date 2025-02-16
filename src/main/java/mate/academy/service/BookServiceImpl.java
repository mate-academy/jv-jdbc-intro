package mate.academy.service;

import mate.academy.dao.BookDao;
import mate.academy.model.Book;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao;

    @Override
    public Book create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public Book get(Long id) {
        return bookDao
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find book with id " + id));
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
