-- =============================================
-- Схема БД для учёта заказов (Итоговая аттестация по Java)
-- =============================================

-- Справочник статусов заказов
CREATE TABLE IF NOT EXISTS order_status (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

COMMENT ON TABLE order_status IS 'Справочник статусов заказов';
COMMENT ON COLUMN order_status.name IS 'Название статуса (например: "новый", "оплачен", "отменён")';

-- Товары
CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    quantity INT NOT NULL CHECK (quantity >= 0),
    category VARCHAR(100) NOT NULL
);

COMMENT ON TABLE product IS 'Товары на складе';
COMMENT ON COLUMN product.description IS 'Описание товара';
COMMENT ON COLUMN product.price IS 'Цена за единицу (в рублях)';
COMMENT ON COLUMN product.quantity IS 'Количество на складе';
COMMENT ON COLUMN product.category IS 'Категория товара';

-- Клиенты
CREATE TABLE IF NOT EXISTS customer (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(150) UNIQUE
);

COMMENT ON TABLE customer IS 'Покупатели';
COMMENT ON COLUMN customer.first_name IS 'Имя клиента';
COMMENT ON COLUMN customer.last_name IS 'Фамилия клиента';
COMMENT ON COLUMN customer.phone IS 'Номер телефона (опционально)';
COMMENT ON COLUMN customer.email IS 'Электронная почта (уникальная)';

-- Заказы
CREATE TABLE IF NOT EXISTS "order" (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    customer_id INT NOT NULL REFERENCES customer(id) ON DELETE CASCADE,
    order_date DATE NOT NULL DEFAULT CURRENT_DATE,
    quantity INT NOT NULL CHECK (quantity > 0),
    status_id INT NOT NULL REFERENCES order_status(id)
);

COMMENT ON TABLE "order" IS 'Заказы клиентов';
COMMENT ON COLUMN "order".product_id IS 'Ссылка на товар';
COMMENT ON COLUMN "order".customer_id IS 'Ссылка на клиента';
COMMENT ON COLUMN "order".order_date IS 'Дата оформления заказа';
COMMENT ON COLUMN "order".quantity IS 'Количество заказанного товара';
COMMENT ON COLUMN "order".status_id IS 'Текущий статус заказа';

-- Индексы для ускорения JOIN и фильтрации
CREATE INDEX IF NOT EXISTS idx_order_product_id ON "order"(product_id);
CREATE INDEX IF NOT EXISTS idx_order_customer_id ON "order"(customer_id);
CREATE INDEX IF NOT EXISTS idx_order_status_id ON "order"(status_id);
CREATE INDEX IF NOT EXISTS idx_order_date ON "order"(order_date);

-- Заполнение данными (минимум 10 записей в каждой таблице)

-- Статусы
INSERT INTO order_status (name) VALUES
('новый'),
('оплачен'),
('в пути'),
('доставлен'),
('отменён')
ON CONFLICT (name) DO NOTHING;

-- Товары (10+)
INSERT INTO product (description, price, quantity, category) VALUES
('Ноутбук Dell Inspiron 15', 65000.00, 10, 'Электроника'),
('Мышь Logitech MX Master 3', 1500.00, 50, 'Периферия'),
('Механическая клавиатура Keychron K2', 3000.00, 30, 'Периферия'),
('Монитор Dell P2422H 24"', 12000.00, 15, 'Электроника'),
('Наушники Sony WH-1000XM5', 8000.00, 25, 'Аудио'),
('USB-флешка SanDisk 64GB', 800.00, 100, 'Аксессуары'),
('Зарядное устройство Anker 65W', 1200.00, 60, 'Аксессуары'),
('Рюкзак для ноутбука Xiaomi', 2500.00, 20, 'Аксессуары'),
('SSD Samsung 980 1TB', 10000.00, 40, 'Комплектующие'),
('Web-камера Logitech C920', 4000.00, 35, 'Периферия'),
('Коврик для мыши SteelSeries QcK', 900.00, 80, 'Аксессуары'),
('USB-C хаб с HDMI', 2200.00, 25, 'Аксессуары')
ON CONFLICT DO NOTHING;

-- Клиенты (10+)
INSERT INTO customer (first_name, last_name, phone, email) VALUES
('Иван', 'Иванов', '+79001112233', 'ivan@example.com'),
('Мария', 'Петрова', '+79002223344', 'maria@example.com'),
('Алексей', 'Сидоров', '+79003334455', 'alex@example.com'),
('Елена', 'Кузнецова', '+79004445566', 'elena@example.com'),
('Дмитрий', 'Смирнов', '+79005556677', 'dmitry@example.com'),
('Ольга', 'Попова', '+79006667788', 'olga@example.com'),
('Сергей', 'Волков', '+79007778899', 'sergey@example.com'),
('Анна', 'Лебедева', '+79008889900', 'anna@example.com'),
('Павел', 'Новиков', '+79009990011', 'pavel@example.com'),
('Татьяна', 'Морозова', '+79000001122', 'tanya@example.com'),
('Артём', 'Зайцев', '+79001112244', 'artem@example.com'),
('Юлия', 'Фролова', '+79002223355', 'julia@example.com')
ON CONFLICT (email) DO NOTHING;

-- Заказы (10+)
INSERT INTO "order" (product_id, customer_id, order_date, quantity, status_id)
SELECT
    p.id AS product_id,
    c.id AS customer_id,
    CURRENT_DATE - (random() * 30)::int AS order_date,
    (random() * 4 + 1)::int AS quantity,
    (random() * 4 + 1)::int AS status_id
FROM
    (SELECT id FROM product LIMIT 12) p,
    (SELECT id FROM customer LIMIT 12) c
ORDER BY random()
LIMIT 15
ON CONFLICT DO NOTHING;