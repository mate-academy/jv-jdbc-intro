package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BooksDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.model.Book.BuilderBook;

public class Main {
    private static final Injector injector = Injector.getInstance( "mate.academy.dao" );
    public static void main(String[] args) {
        BooksDao booksDao = (BooksDao) injector.getInstance( BooksDao.class );

        BuilderBook builderBook = new BuilderBook();
        Book book1 = builderBook.setTitle( "bonobo" ).setPrice( BigDecimal.valueOf( 1500 ) ).build();
        Book book2 = builderBook.setTitle( "aaabbb" ).setPrice( BigDecimal.valueOf( 1100 ) ).build();
        Book book3 = builderBook.setTitle( "qwerty" ).setPrice( BigDecimal.valueOf( 1350 ) ).build();

        booksDao.create( book1 );
        booksDao.create( book2 );
        booksDao.create( book3 );

        System.out.println("\nFrom findById()");
        System.out.println( booksDao.findById( 1L ) );
        System.out.println( booksDao.findById( 2L ) );
        System.out.println( booksDao.findById( 3L ) );

        System.out.println("\nFrom findALL()");
        for (Book book: booksDao.findAll()) {
            System.out.println( book );
        }

        System.out.println("\nFrom update()");
        book2.setTitle( "OOOOOOO" );
        book2.setPrice( BigDecimal.valueOf( 12345678.09 ) );
        System.out.println(booksDao.update( book2 ));

        System.out.println( "\nFrom update()" + booksDao.deleteById( 2L ) );
    }
}
