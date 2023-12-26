package mate.academy.service.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.model.Book;
import mate.academy.service.BookService;

public class BookServiceImpl implements BookService {
    private BookDao bookDao;

    @Override
    public Book create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> book = bookDao.findById(id);
        return book.orElseThrow(() -> new DataProcessingException("Can't find a book by id " + id));
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
