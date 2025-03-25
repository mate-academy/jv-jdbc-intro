package mate.academy.dao;

import mate.academy.model.Car;
import java.util.List;
import java.util.Optional;

public interface CarDao {
    Car create(Car car);
    List<Car> findAll();
    Optional<Car> findById(Long id);
    Car update(Car car);
    boolean delete(Long id);

    boolean delete(Car car);
}
