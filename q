[1mdiff --git a/src/main/java/mate/academy/Main.java b/src/main/java/mate/academy/Main.java[m
[1mindex 2d0d2a5..311b963 100644[m
[1m--- a/src/main/java/mate/academy/Main.java[m
[1m+++ b/src/main/java/mate/academy/Main.java[m
[36m@@ -1,6 +1,39 @@[m
 package mate.academy;[m
 [m
[32m+[m[32mimport java.math.BigDecimal;[m
[32m+[m[32mimport java.util.List;[m
[32m+[m[32mimport java.util.Optional;[m
[32m+[m[32mimport mate.academy.dao.BookDao;[m
[32m+[m[32mimport mate.academy.lib.Injector;[m
[32m+[m[32mimport mate.academy.model.Book;[m
[32m+[m
 public class Main {[m
[32m+[m[32m    private static final Injector injector =[m
[32m+[m[32m            Injector.getInstance("mate.academy");[m
[32m+[m
     public static void main(String[] args) {[m
[32m+[m[32m        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);[m
[32m+[m[32m        Book book = new Book();[m
[32m+[m[32m        book.setTitle("Harry Potter");[m
[32m+[m[32m        book.setPrice(new BigDecimal("350.00"));[m
[32m+[m[32m        Book createdBook = bookDao.create(book);[m
[32m+[m[32m        System.out.println("Created book ID: " + createdBook.getId());[m
[32m+[m[32m        Optional<Book> foundBook = bookDao.findById(createdBook.getId());[m
[32m+[m[32m        if (foundBook.isPresent()) {[m
[32m+[m[32m            System.out.println("Found book: "[m
[32m+[m[32m                    + foundBook.get().getTitle());[m
[32m+[m[32m        }[m
[32m+[m[32m        List<Book> books = bookDao.findAll();[m
[32m+[m[32m        for (Book currentBook : books) {[m
[32m+[m[32m            System.out.println("ID: " + currentBook.getId()[m
[32m+[m[32m                    + ", title: " + currentBook.getTitle()[m
[32m+[m[32m                    + ", price: " + currentBook.getPrice());[m
[32m+[m[32m        }[m
[32m+[m[32m        createdBook.setTitle("Harry Potter Updated");[m
[32m+[m[32m        createdBook.setPrice(new BigDecimal("450.00"));[m
[32m+[m[32m        Book updatedBook = bookDao.update(createdBook);[m
[32m+[m[32m        System.out.println("Updated book: " + updatedBook.getTitle());[m
[32m+[m[32m        boolean isDeleted = bookDao.deleteById(createdBook.getId());[m
[32m+[m[32m        System.out.println("Book deleted: " + isDeleted);[m
     }[m
 }[m
[1mdiff --git a/src/main/java/mate/academy/dao/impl/BookDaoImpl.java b/src/main/java/mate/academy/dao/impl/BookDaoImpl.java[m
[1mindex f453fe5..d4c3c57 100644[m
[1m--- a/src/main/java/mate/academy/dao/impl/BookDaoImpl.java[m
[1m+++ b/src/main/java/mate/academy/dao/impl/BookDaoImpl.java[m
[36m@@ -1,6 +1,5 @@[m
 package mate.academy.dao.impl;[m
 [m
[31m-import java.math.BigDecimal;[m
 import java.sql.Connection;[m
 import java.sql.DriverManager;[m
 import java.sql.PreparedStatement;[m
[36m@@ -18,12 +17,20 @@[m [mimport mate.academy.model.Book;[m
 [m
 @Dao[m
 public class BookDaoImpl implements BookDao {[m
[32m+[m[32m    private Book mapResultSetToBook(ResultSet resultSet)[m[41m[m
[32m+[m[32m            throws SQLException {[m[41m[m
[32m+[m[32m        Book book = new Book();[m[41m[m
[32m+[m[32m        book.setId(resultSet.getObject("id", Long.class));[m[41m[m
[32m+[m[32m        book.setTitle(resultSet.getString("title"));[m[41m[m
[32m+[m[32m        book.setPrice(resultSet.getBigDecimal("price"));[m[41m[m
[32m+[m[32m        return book;[m[41m[m
[32m+[m[32m    }[m[41m[m
     @Override[m
     public Book create(Book book) {[m
         String sql = "INSERT INTO books (title, price) VALUES (?, ?)";[m
         Properties dbProperties = new Properties();[m
         dbProperties.put("user","root");[m
[31m-        dbProperties.put("password","15vlad0705");[m
[32m+[m[32m        dbProperties.put("password","0000");[m[41m[m
         try (Connection connection = DriverManager[m
                 .getConnection("jdbc:mysql://localhost:3306/book_store", dbProperties);[m
                 PreparedStatement preparedStatement = connection.prepareStatement([m
[36m@@ -40,11 +47,13 @@[m [mpublic class BookDaoImpl implements BookDao {[m
                     Long id = resultSet.getLong(1);[m
                     book.setId(id);[m
                 } else {[m
[31m-                    throw new DataProcessingException("Failed to get the ID of the new book");[m
[32m+[m[32m                    throw new DataProcessingException([m[41m[m
[32m+[m[32m                            "Failed to get ID for book: " + book.getTitle());[m[41m[m
                 }[m
             }[m
         } catch (SQLException e) {[m
[31m-            throw new DataProcessingException("Can't add new books", e);[m
[32m+[m[32m            throw new DataProcessingException([m[41m[m
[32m+[m[32m                    "Can't create book: " + book.getTitle(), e);[m[41m[m
         }[m
         return book;[m
     }[m
[36m@@ -54,24 +63,19 @@[m [mpublic class BookDaoImpl implements BookDao {[m
         String sql = "SELECT * FROM books WHERE id = ?";[m
         Properties dbProperties = new Properties();[m
         dbProperties.put("user","root");[m
[31m-        dbProperties.put("password","15vlad0705");[m
[32m+[m[32m        dbProperties.put("password","0000");[m[41m[m
         try (Connection connection = DriverManager[m
                 .getConnection("jdbc:mysql://localhost:3306/book_store", dbProperties);[m
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {[m
             preparedStatement.setLong(1, id);[m
             try (ResultSet resultSet = preparedStatement.executeQuery()) {[m
                 if (resultSet.next()) {[m
[31m-                    String title = resultSet.getString("title");[m
[31m-                    BigDecimal price = resultSet.getBigDecimal("price");[m
[31m-                    Book book = new Book();[m
[31m-                    book.setId(id);[m
[31m-                    book.setTitle(title);[m
[31m-                    book.setPrice(price);[m
[31m-                    return Optional.of(book);[m
[32m+[m[32m                    return Optional.of(mapResultSetToBook(resultSet));[m[41m[m
                 }[m
             }[m
         } catch (SQLException e) {[m
[31m-            throw new DataProcessingException("Can not create a connection to the DB", e);[m
[32m+[m[32m            throw new DataProcessingException([m[41m[m
[32m+[m[32m                    "Can't find book with id: " + id, e);[m[41m[m
         }[m
         return Optional.empty();[m
     }[m
[36m@@ -82,24 +86,18 @@[m [mpublic class BookDaoImpl implements BookDao {[m
         String sql = "SELECT * FROM books";[m
         Properties dbProperties = new Properties();[m
         dbProperties.put("user","root");[m
[31m-        dbProperties.put("password","15vlad0705");[m
[32m+[m[32m        dbProperties.put("password","0000");[m[41m[m
         try (Connection connection = DriverManager[m
                 .getConnection("jdbc:mysql://localhost:3306/book_store", dbProperties);[m
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {[m
             try (ResultSet resultSet = preparedStatement.executeQuery()) {[m
                 while (resultSet.next()) {[m
[31m-                    Long id = resultSet.getLong("id");[m
[31m-                    String title = resultSet.getString("title");[m
[31m-                    BigDecimal price = resultSet.getBigDecimal("price");[m
[31m-                    Book book = new Book();[m
[31m-                    book.setId(id);[m
[31m-                    book.setTitle(title);[m
[31m-                    book.setPrice(price);[m
[31m-                    books.add(book);[m
[32m+[m[32m                    books.add(mapResultSetToBook(resultSet));[m[41m[m
                 }[m
             }[m
         } catch (SQLException e) {[m
[31m-            throw new DataProcessingException("Can not create a connection to the DB", e);[m
[32m+[m[32m            throw new DataProcessingException([m[41m[m
[32m+[m[32m                    "Can't get all books from DB", e);[m[41m[m
         }[m
         return books;[m
     }[m
[36m@@ -109,7 +107,7 @@[m [mpublic class BookDaoImpl implements BookDao {[m
         String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";[m
         Properties dbProperties = new Properties();[m
         dbProperties.put("user","root");[m
[31m-        dbProperties.put("password","15vlad0705");[m
[32m+[m[32m        dbProperties.put("password","0000");[m[41m[m
         try (Connection connection = DriverManager[m
                 .getConnection("jdbc:mysql://localhost:3306/book_store", dbProperties);[m
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {[m
[36m@@ -123,7 +121,8 @@[m [mpublic class BookDaoImpl implements BookDao {[m
                         "Failed to update the book with id: " + book.getId());[m
             }[m
         } catch (SQLException e) {[m
[31m-            throw new DataProcessingException("Can not create a connection to the DB", e);[m
[32m+[m[32m            throw new DataProcessingException([m[41m[m
[32m+[m[32m                    "Can't update book with id: " + book.getId(), e);[m[41m[m
         }[m
         return book;[m
     }[m
[36m@@ -133,7 +132,7 @@[m [mpublic class BookDaoImpl implements BookDao {[m
         String sql = "DELETE FROM books WHERE id =?";[m
         Properties dbProperties = new Properties();[m
         dbProperties.put("user","root");[m
[31m-        dbProperties.put("password","15vlad0705");[m
[32m+[m[32m        dbProperties.put("password","0000");[m[41m[m
         try (Connection connection = DriverManager[m
                 .getConnection("jdbc:mysql://localhost:3306/book_store", dbProperties);[m
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {[m
[36m@@ -141,7 +140,8 @@[m [mpublic class BookDaoImpl implements BookDao {[m
             int affectedRows = preparedStatement.executeUpdate();[m
             return affectedRows > 0;[m
         } catch (SQLException e) {[m
[31m-            throw new DataProcessingException("Can't delete book with id: " + id, e);[m
[32m+[m[32m            throw new DataProcessingException([m[41m[m
[32m+[m[32m                    "Can't delete book with id: " + id, e);[m[41m[m
         }[m
     }[m
 }[m
