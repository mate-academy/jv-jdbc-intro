package mate.academy.services;

import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.models.Book;

public class BookServiceImpl implements BookService {

    private BookDao bookDao = new BookDaoImpl();

    @Override
    public Book create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public Book findById(Long id) {
        return bookDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find book with id: " + id));
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
