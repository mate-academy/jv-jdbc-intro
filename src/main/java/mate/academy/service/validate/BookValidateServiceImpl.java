package mate.academy.service.validate;

import java.math.BigDecimal;
import mate.academy.exceptions.ValidationException;
import mate.academy.lib.Service;
import mate.academy.model.Book;
import mate.academy.service.BookValidateService;

@Service
public class BookValidateServiceImpl implements BookValidateService {

    public void validateBeforeCreate(Book book) {
        validateBookTitle(book.getTitle());
        validateBookPrice(book.getPrice());
    }

    public void validateBeforeUpdate(Book book) {
        validateId(book.getId());
        validateBookTitle(book.getTitle());
        validateBookPrice(book.getPrice());
    }

    private void validateId(Long id) {
        if (id == null || id < 1) {
            throw new ValidationException("Invalid book id");
        }
    }

    private void validateBookTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Book title cannot be empty");
        }
    }

    private void validateBookPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Book price must be greater than 0");
        }
    }

}
