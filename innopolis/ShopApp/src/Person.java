import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person {
    private String name;
    private double money;
    private List<Product> products;

    public Person(String name, double money) {
        setName(name);
        setMoney(money);
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products); // Возвращаем копию для безопасности
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (trimmedName.length() < 3) {
            throw new IllegalArgumentException("Имя не может быть короче 3 символов");
        }
        this.name = trimmedName;
    }

    public void setMoney(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Деньги не могут быть отрицательными");
        }
        this.money = money;
    }

    public boolean buyProduct(Product product) {
        if (product == null) {
            return false;
        }

        if (this.money >= product.getCost()) {
            this.money -= product.getCost();
            this.products.add(product);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return name + " - " + String.format("%.2f", money) + " руб.";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getProductsAsString() {
        if (products.isEmpty()) {
            return "Ничего не куплено";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < products.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(products.get(i).getName());
        }
        return sb.toString();
    }
}