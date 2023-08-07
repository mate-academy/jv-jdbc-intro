package mate.academy.service;

import mate.academy.model.Book;

public interface BookValidateService {
    public void validateBeforeCreate(Book book);

    public void validateBeforeUpdate(Book book);
}
