package test;

import model.Car;
import repository.CarsRepository;
import repository.CarsRepositoryImpl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        CarsRepository repo = new CarsRepositoryImpl();

        List<Car> cars = repo.loadCars();

        StringBuilder report = new StringBuilder();

        report.append("Автомобили в базе:\n");
        report.append("Number   Model      Color    Mileage  Cost\n");
        cars.forEach(car -> report.append(car).append("\n"));
        report.append("\n");


        String colorToFind = "Black";
        long mileageToFind = 0L;
        String numbersByColorOrMileage = cars.stream()
                .filter(car -> car.getColor().equals(colorToFind) || car.getMileage() == mileageToFind)
                .map(Car::getNumber)
                .collect(Collectors.joining(" "));
        report.append("Номера автомобилей по цвету или пробегу: ").append(numbersByColorOrMileage).append("\n");


        long minPrice = 700_000L;
        long maxPrice = 800_000L;
        long uniqueModelsCount = cars.stream()
                .filter(car -> car.getCost() >= minPrice && car.getCost() <= maxPrice)
                .map(Car::getModel)
                .distinct()
                .count();
        report.append("Уникальные автомобили: ").append(uniqueModelsCount).append(" шт.\n");


        String minCostColor = cars.stream()
                .min(Comparator.comparing(Car::getCost))
                .map(Car::getColor)
                .orElse("N/A");
        report.append("Цвет автомобиля с минимальной стоимостью: ").append(minCostColor).append("\n");


        String modelToFind1 = "Toyota";
        String modelToFind2 = "Volvo";
        for (String model : Arrays.asList(modelToFind1, modelToFind2)) {
            double avgCost = cars.stream()
                    .filter(car -> car.getModel().equals(model))
                    .mapToLong(Car::getCost)
                    .average()
                    .orElse(0.0);
            report.append(String.format("Средняя стоимость модели %s: %.2f\n", model, avgCost));
        }

        System.out.println(report);

        repo.saveReport(report.toString());
    }
}