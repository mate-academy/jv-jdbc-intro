# Task description

Your task is to create a Book entity class and BookDao repository interface, and it's implementation.

Here are the steps you need to do in this HW:
- Establish connection to your Database. Be sure you have installed DB and you are able to create a new schema for this task.
- Create `init_db.sql` file in `src/main/resources` folder. In this file, put the scripts for creating required table.
- Create `Book` model.
- Create DAO  layer for `Book` model. Below you can see the list of required methods.
- You're already given an injector and `@Dao` annotation. Do not forget to use it for Dao implementations.
- Return [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) when you can return null in DAO.
  For example: ```public Optional<Book> findById(Long id);```
- In the `main` method call all CRUD methods. It may look like:
```java
public class Main {
    private static final Injector injector = Injector.getInstance("YOUR_PACKAGE");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        // initialize field values using setters or constructor
        bookDao.create(book);
        // test other methods from BookDao
    }
}
```
**WARNING!!!** Path to your project must contain only english letters. Also, it mustn't contain spaces. In other case `Injector` won't work correctly.
- Your table should be named `books` and contain these columns: `id`, `title`, `price`.
### Java classes structure:
- Book

```java
import java.math.BigDecimal;

public class Book {
  private Long id;
  private String title;
  private BigDecimal price;
}
```

### BookDao methods:
    - Book create(Book book);
    - Optional<Book> findById(Long id);
    - List<Book> findAll();
    - Book update(Book book);
    - boolean deleteById(Long id);
    
### Create custom exception
`e.printStackTrace()` - is a bad practice! Let's create custom exception `DataProcessingException`
and constructor with two parameters: `String message` and `Throwable ex`.  
It should be extended from `RuntimeException`. You should rethrow this exception in `catch` block on dao layer.
    
#### DB connection error: 
If you can't connect to your db because of this error: <br>
`The server time zone value ‘EEST’ is unrecognized or represents more than one time zone`. <br>
Try to set timezone explicitly in your connection URL. <br>
Example: <br>
`...localhost:3306/your_schema?serverTimezone=UTC` <br>
Or you can set a timezone in MySql directly by running command: `SET GLOBAL time_zone = '+3:00'`;

__You can check yourself using this__ [checklist](checklist.md)
