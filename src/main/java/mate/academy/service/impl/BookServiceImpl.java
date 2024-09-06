package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.BookDao;
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
        return bookDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found book with id: " + id));
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
    public boolean delete(Book book) {
        return bookDao.deleteById(book);
    }
}
