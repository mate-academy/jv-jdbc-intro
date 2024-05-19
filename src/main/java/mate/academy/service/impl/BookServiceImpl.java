package mate.academy.service.impl;

import mate.academy.dao.BookDao;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.model.Book;
import mate.academy.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public Book getById(Long id) {
        return bookDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Book with id %s not found", id)));
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
    public boolean deleteById(Long id) {
        return bookDao.deleteById(id);
    }
}
