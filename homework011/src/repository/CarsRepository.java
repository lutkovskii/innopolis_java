package repository;

import model.Car;

import java.util.List;

public interface CarsRepository {
    List<Car> loadCars();
    void saveReport(String report);
}
