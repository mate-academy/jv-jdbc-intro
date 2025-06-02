package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Injector;
import mate.academy.lib.dao.BookDao;
import mate.academy.lib.dao.impl.BookDaoImpl;
import mate.academy.lib.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    
    public static void main(String[] args) {
        BookDaoImpl bookDaoImpl = (BookDaoImpl) injector.getInstance(BookDao.class);
        
        Book bookOne = new Book("Book One", BigDecimal.valueOf(10.01));
        Book bookTwo = new Book("Book Two", BigDecimal.valueOf(20.02));
        Book bookThree = new Book("Book Three", BigDecimal.valueOf(30.03));
        
        bookDaoImpl.create(bookOne);
        bookDaoImpl.create(bookTwo);
        bookDaoImpl.create(bookThree);
        
        Optional<Book> bookOneFoundById = bookDaoImpl.findById(bookOne.getId());
        System.out.println(bookOneFoundById);
        
        bookTwo.setPrice(bookTwo.getPrice().add(BigDecimal.valueOf(200.2)));
        bookDaoImpl.update(bookTwo);
        
        bookThree.setPrice(bookThree.getPrice().add(BigDecimal.valueOf(300.3)));
        bookThree.setTitle("Book 3");
        bookDaoImpl.update(bookThree);
        
        List<Book> books = bookDaoImpl.findAll();
        System.out.println(books);
        
        bookDaoImpl.deleteById(bookOne.getId());
        
        List<Book> updatedBooks = bookDaoImpl.findAll();
        System.out.println(updatedBooks);
    }
}
