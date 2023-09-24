package mate.academy.service.impl;

import java.util.List;
import java.util.Optional;

import mate.academy.dao.BookDao;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.service.BookService;

@Dao
public class BookServiceImpl implements BookService {
    @Dao
    private BookDao bookDao;

    @Override
    public Book create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> bookById = bookDao.findById(id);
        return bookById.orElseThrow(() -> new RuntimeException("There is no book with the id: " + id));
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
