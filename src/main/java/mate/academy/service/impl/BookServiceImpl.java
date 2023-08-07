package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.exceptions.EntityNotFoundException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import mate.academy.service.BookValidateService;

@Service
public class BookServiceImpl implements BookService {
    @Inject
    private BookDao bookDao;
    @Inject
    private BookValidateService bookValidateService;

    @Override
    public Book createBook(Book book) {
        bookValidateService.validateBeforeCreate(book);
        return bookDao.create(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookDao.get(id).orElseThrow(() -> new EntityNotFoundException("Couldn't find"
                + " a book with id: " + id));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
    public Book updateBook(Book book) {
        bookValidateService.validateBeforeCreate(book);
        return bookDao.update(book);
    }

    @Override
    public boolean deleteBook(Long id) {
        return bookDao.delete(id);
    }
}
