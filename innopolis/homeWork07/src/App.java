import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Person> people = new ArrayList<>();
        Map<String, Product> products = new HashMap<>();

        try {
            System.out.println("Введите покупателей в формате: Имя = деньги");
            System.out.println("Для завершения ввода введите пустую строку");

            String input;
            while (true) {
                input = scanner.nextLine();
                if (input.trim().isEmpty()) {
                    break;
                }
                if (input.trim().equalsIgnoreCase("END")) {
                    break;
                }

                try {
                    int equalsIndex = input.indexOf('=');
                    if (equalsIndex == -1) {
                        System.out.println("Неверный формат. Используйте: Имя = деньги");
                        continue;
                    }

                    String name = input.substring(0, equalsIndex).trim();
                    String moneyStr = input.substring(equalsIndex + 1).trim();

                    if (name.isEmpty()) {
                        System.out.println("Имя не может быть пустым");
                        continue;
                    }

                    double money = Double.parseDouble(moneyStr);

                    Person person = new Person(name, money);
                    people.add(person);
                    System.out.println("Добавлен: " + person);

                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат денег. Введите число.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("\nВведите продукты в формате: Название = цена");
            System.out.println("Для завершения ввода введите пустую строку");
            System.out.println("Для скидочных продуктов используйте: Название = цена = скидка = дата_окончания");

            while (true) {
                input = scanner.nextLine();
                if (input.trim().isEmpty()) {
                    break;
                }
                if (input.trim().equalsIgnoreCase("END")) {
                    break;
                }

                try {
                    int equalsIndex = input.indexOf('=');
                    if (equalsIndex == -1) {
                        System.out.println("Неверный формат. Используйте: Название = цена");
                        continue;
                    }

                    String[] parts = input.split("=");
                    if (parts.length == 4) {
                        // Скидочный продукт
                        String name = parts[0].trim();
                        String costStr = parts[1].trim();
                        String discountStr = parts[2].trim();
                        String expiryDateStr = parts[3].trim();

                        if (name.isEmpty()) {
                            System.out.println("Название продукта не может быть пустым");
                            continue;
                        }

                        double cost = Double.parseDouble(costStr);
                        double discount = Double.parseDouble(discountStr);


                        String[] dateParts = expiryDateStr.split("-");
                        if (dateParts.length != 3) {
                            System.out.println("Неверный формат даты. Используйте: ГГГГ-ММ-ДД");
                            continue;
                        }

                        int year = Integer.parseInt(dateParts[0]);
                        int month = Integer.parseInt(dateParts[1]);
                        int day = Integer.parseInt(dateParts[2]);

                        DiscountProduct product = new DiscountProduct(name, cost, discount, year, month, day);
                        products.put(name, product);
                        System.out.println("Добавлен скидочный продукт: " + product);
                    } else if (parts.length == 2) {

                        String name = parts[0].trim();
                        String costStr = parts[1].trim();

                        if (name.isEmpty()) {
                            System.out.println("Название продукта не может быть пустым");
                            continue;
                        }

                        double cost = Double.parseDouble(costStr);

                        Product product = new Product(name, cost);
                        products.put(name, product);
                        System.out.println("Добавлен: " + product);
                    } else {
                        System.out.println("Неверный формат. Используйте: Название = цена или Название = цена = скидка = дата_окончания");
                        continue;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат цены или скидки. Введите число.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("\nВведите покупки в формате: Полное_Имя Продукт");
            System.out.println("Пример: Павел Андреевич Хлеб");
            System.out.println("Для завершения введите END");

            while (true) {
                input = scanner.nextLine();
                if (input.trim().equalsIgnoreCase("END")) {
                    break;
                }
                if (input.trim().isEmpty()) {
                    continue;
                }

                try {
                    Person buyer = null;
                    String productName = null;

                    for (Person person : people) {
                        String personName = person.getName();
                        if (input.startsWith(personName)) {
                            int nameLength = personName.length();
                            if (input.length() > nameLength && input.charAt(nameLength) == ' ') {
                                buyer = person;
                                productName = input.substring(nameLength + 1).trim();
                                break;
                            }
                        }
                    }

                    if (buyer == null) {
                        for (Person person : people) {
                            String personName = person.getName();
                            if (input.startsWith(personName + " ")) {
                                buyer = person;
                                productName = input.substring(personName.length() + 1).trim();
                                break;
                            }
                        }
                    }

                    if (buyer == null) {
                        System.out.println("Покупатель не найден в строке: " + input);
                        System.out.println("Доступные покупатели:");
                        for (Person person : people) {
                            System.out.println("- " + person.getName());
                        }
                        continue;
                    }

                    if (productName == null || productName.isEmpty()) {
                        System.out.println("Не указан продукт");
                        continue;
                    }

                    Product product = products.get(productName);
                    if (product == null) {
                        System.out.println("Продукт " + productName + " не найден");
                        System.out.println("Доступные продукты:");
                        for (String prodName : products.keySet()) {
                            System.out.println("- " + prodName);
                        }
                        continue;
                    }

                    if (product instanceof DiscountProduct) {
                        DiscountProduct discountProduct = (DiscountProduct) product;
                        if (!discountProduct.isDiscountActive()) {
                            System.out.println("Скидка на продукт " + product.getName() + " истекла");

                            if (buyer.buyProduct(product)) {
                                String verbEnding = isFemaleName(buyer.getName()) ? "а" : "";
                                System.out.println(buyer.getName() + " купил" + verbEnding + " " + product.getName());
                            } else {
                                System.out.println(buyer.getName() + " не может позволить себе " + product.getName());
                            }
                        } else {

                            if (buyer.buyProduct(product)) {
                                String verbEnding = isFemaleName(buyer.getName()) ? "а" : "";
                                System.out.println(buyer.getName() + " купил" + verbEnding + " " + product.getName() + " со скидкой " +
                                        discountProduct.getDiscountPercentage() + "%");
                            } else {
                                System.out.println(buyer.getName() + " не может позволить себе " + product.getName());
                            }
                        }
                    } else {

                        if (buyer.buyProduct(product)) {
                            String verbEnding = isFemaleName(buyer.getName()) ? "а" : "";
                            System.out.println(buyer.getName() + " купил" + verbEnding + " " + product.getName());
                        } else {
                            System.out.println(buyer.getName() + " не может позволить себе " + product.getName());
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Ошибка при обработке покупки: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            System.out.println("\n=== РЕЗУЛЬТАТЫ ===");
            for (Person person : people) {
                System.out.println(person.getName() + " - " + person.getProductsAsString());
            }

        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static boolean isFemaleName(String name) {
        return name.endsWith("а") || name.endsWith("я");
    }
}