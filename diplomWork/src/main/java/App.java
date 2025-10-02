import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Properties;

public class App {
    public static void main(String[] args) {
        Properties props = new Properties();
        try (InputStream is = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            props.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Не найден application.properties", e);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);
            try {
                // Загрузка и выполнение schema.sql из ресурсов (для демонстрации)
                InputStream schemaStream = App.class.getClassLoader().getResourceAsStream("schema.sql");
                if (schemaStream == null) {
                    System.err.println("⚠️ schema.sql не найден в ресурсах. Пропускаем миграцию.");
                } else {
                    String sql = new String(schemaStream.readAllBytes(), StandardCharsets.UTF_8);
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute(sql);
                    }
                    System.out.println("✅ Схема БД применена.");
                }

                // CRUD-демонстрация
                demonstrateCRUD(conn);
                conn.commit();
                System.out.println("✅ Все операции завершены успешно.");
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("❌ Ошибка! Выполняется откат транзакции.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void demonstrateCRUD(Connection conn) throws SQLException {
        // 1. Вставка
        int prodId = insertProduct(conn, "Тестовый товар", 999.99, 10, "Тест");
        int custId = insertCustomer(conn, "Тест", "Тестов", "+70000000000", "test@test.ru");

        // 2. Заказ
        createOrder(conn, prodId, custId, 2, 1);

        // 3. Чтение
        printLastOrders(conn);

        // 4. Обновление
        updateProduct(conn, prodId, 888.88, 5);

        // 5. Удаление
        deleteOrder(conn, prodId, custId);
        deleteCustomer(conn, custId);
        deleteProduct(conn, prodId);
    }

    // --- Вспомогательные методы ---
    private static int insertProduct(Connection c, String d, double p, int q, String cat) throws SQLException {
        String sql = "INSERT INTO product (description, price, quantity, category) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, d); ps.setDouble(2, p); ps.setInt(3, q); ps.setString(4, cat);
            ResultSet rs = ps.executeQuery(); rs.next(); int id = rs.getInt(1);
            System.out.println("➕ Добавлен товар ID: " + id);
            return id;
        }
    }

    private static int insertCustomer(Connection c, String fn, String ln, String ph, String em) throws SQLException {
        String sql = "INSERT INTO customer (first_name, last_name, phone, email) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, fn); ps.setString(2, ln); ps.setString(3, ph); ps.setString(4, em);
            ResultSet rs = ps.executeQuery(); rs.next(); int id = rs.getInt(1);
            System.out.println("➕ Добавлен клиент ID: " + id);
            return id;
        }
    }

    private static void createOrder(Connection c, int pid, int cid, int qty, int sid) throws SQLException {
        String sql = "INSERT INTO \"order\" (product_id, customer_id, quantity, status_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pid); ps.setInt(2, cid); ps.setInt(3, qty); ps.setInt(4, sid);
            ps.executeUpdate();
            System.out.println("➕ Создан заказ");
        }
    }

    private static void printLastOrders(Connection c) throws SQLException {
        String sql = """
            SELECT o.id, c.first_name, c.last_name, p.description, o.quantity, s.name
            FROM "order" o
            JOIN customer c ON o.customer_id = c.id
            JOIN product p ON o.product_id = p.id
            JOIN order_status s ON o.status_id = s.id
            ORDER BY o.order_date DESC
            LIMIT 5
            """;
        try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            System.out.println("\n📋 Последние заказы:");
            while (rs.next()) {
                System.out.printf("ID: %d | %s %s | %s | x%d | [%s]%n",
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getString("name")
                );
            }
        }
    }

    private static void updateProduct(Connection c, int id, double price, int qty) throws SQLException {
        String sql = "UPDATE product SET price = ?, quantity = ? WHERE id = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, price); ps.setInt(2, qty); ps.setInt(3, id);
            ps.executeUpdate();
            System.out.println("✏️ Товар ID " + id + " обновлён");
        }
    }

    private static void deleteOrder(Connection c, int pid, int cid) throws SQLException {
        String sql = "DELETE FROM \"order\" WHERE product_id = ? AND customer_id = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pid); ps.setInt(2, cid); ps.executeUpdate();
        }
    }

    private static void deleteCustomer(Connection c, int id) throws SQLException {
        String sql = "DELETE FROM customer WHERE id = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) { ps.setInt(1, id); ps.executeUpdate(); }
    }

    private static void deleteProduct(Connection c, int id) throws SQLException {
        String sql = "DELETE FROM product WHERE id = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) { ps.setInt(1, id); ps.executeUpdate(); }
    }
}