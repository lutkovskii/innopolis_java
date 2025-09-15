package repository;

import model.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarsRepositoryImpl implements CarsRepository {

    private static final String DATA_FILE = "data/cars.txt";
    private static final String OUTPUT_FILE = "data/output.txt";

    @Override
    public List<Car> loadCars() {
        List<Car> cars = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String number = parts[0];
                    String model = parts[1];
                    String color = parts[2];
                    long mileage = Long.parseLong(parts[3]);
                    long cost = Long.parseLong(parts[4]);
                    cars.add(new Car(number, model, color, mileage, cost));
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        return cars;
    }

//
@Override
public void saveReport(String report) {
    File dir = new File("data");
    if (!dir.exists()) {
        dir.mkdirs(); // Создаем папку data
    }

    try (PrintWriter pw = new PrintWriter(new FileWriter("data/output.txt"))) {
        pw.print(report);
    } catch (IOException e) {
        System.err.println("Ошибка записи в файл: " + e.getMessage());
    }
}
}