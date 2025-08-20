import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DiscountProduct extends Product {
    private double discountPercentage;
    private LocalDate expiryDate;

    public DiscountProduct(String name, double cost, double discountPercentage, int year, int month, int day) {
        super(name, cost);
        setDiscountPercentage(discountPercentage);
        setExpiryDate(year, month, day);
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setDiscountPercentage(double discountPercentage) {
        if (discountPercentage <= 0 || discountPercentage >= 100) {
            throw new IllegalArgumentException("Скидка должна быть в диапазоне от 0 до 100%");
        }
        this.discountPercentage = discountPercentage;
    }

    public void setExpiryDate(int year, int month, int day) {
        try {
            this.expiryDate = LocalDate.of(year, month, day);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Неверная дата окончания скидки");
        }
    }

    @Override
    public double getCost() {
        // Если скидка активна, возвращаем цену со скидкой
        if (isDiscountActive()) {
            return super.getCost() * (1 - discountPercentage / 100);
        }
        return super.getCost();
    }

    public boolean isDiscountActive() {
        return LocalDate.now().isBefore(expiryDate) || LocalDate.now().isEqual(expiryDate);
    }

    public double getOriginalCost() {
        return super.getCost();
    }

    @Override
    public String toString() {
        return String.format("%s = %.2f руб. (скидка %s%% до %s)",
                getName(), getOriginalCost(), discountPercentage,
                expiryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}