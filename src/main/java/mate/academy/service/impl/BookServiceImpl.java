package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Book;
import mate.academy.service.BookService;

@Service
public class BookServiceImpl implements BookService {
    @Inject
    private BookDao bookDao;

    @Override
    public Book create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public Book findById(Long id) {
        return bookDao.findById(id).orElseThrow(() ->
              new RuntimeException("Book not found in DB." + id));
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
