package mate.academy.service.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.DataChangeService;

public class DataChangeServiceImpl implements DataChangeService {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final BookDao BOOK_DAO = (BookDao) INJECTOR.getInstance(BookDao.class);

    @Override
    public Book create(Book book) {
        if (book.getPrice() == null || book.getTitle() == null) {
            throw new RuntimeException("Price and title can't be null" + book);
        }
        return BOOK_DAO.create(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        if (id < 1) {
            throw new RuntimeException("Id can't be less that 1. Actual id " + id);
        }
        return BOOK_DAO.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return BOOK_DAO.findAll();
    }

    @Override
    public Book update(Book book) {
        if (book.getPrice() == null || book.getTitle() == null || book.getId() == null) {
            throw new RuntimeException("Price, id and title can't be null" + book);
        }
        return BOOK_DAO.update(book);
    }

    @Override
    public boolean deleteById(Long id) {
        if (id < 1) {
            throw new RuntimeException("Id can't be less that 1. Actual id " + id);
        }
        return BOOK_DAO.deleteById(id);
    }
}
