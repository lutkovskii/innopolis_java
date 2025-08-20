import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Product {
    private String name;
    private double cost;

    public Product(String name, double cost) {
        setName(name);
        setCost(cost);
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
        if (trimmedName.length() < 3) {
            throw new IllegalArgumentException("Название продукта не может быть короче 3 символов");
        }
        if (trimmedName.matches(".*\\d+.*")) {
            throw new IllegalArgumentException("Название продукта не должно содержать только цифры");
        }
        this.name = trimmedName;
    }

    public void setCost(double cost) {
        if (cost <= 0) {
            throw new IllegalArgumentException("Стоимость продукта должна быть положительной");
        }
        this.cost = cost;
    }

    @Override
    public String toString() {
        return name + " = " + String.format("%.2f", cost) + " руб.";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}