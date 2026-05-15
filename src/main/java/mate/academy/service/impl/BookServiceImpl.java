package mate.academy.service.impl;

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
        return bookDao.create(book);
    }

    @Override
    public Book get(Long id) {
        return bookDao.findById(id).orElseThrow(
                () -> new DataProcessingException(
                        "Can't get book by id " + id, new RuntimeException()));
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
