-- 1. Заказы за последние 7 дней
SELECT c.first_name, c.last_name, p.description, o.quantity, o.order_date
FROM "order" o
JOIN customer c ON o.customer_id = c.id
JOIN product p ON o.product_id = p.id
WHERE o.order_date >= CURRENT_DATE - 7;

-- 2. Топ-3 популярных товара
SELECT p.description, SUM(o.quantity) AS total
FROM "order" o
JOIN product p ON o.product_id = p.id
GROUP BY p.id
ORDER BY total DESC
LIMIT 3;

-- 3. Средняя цена по категориям
SELECT category, AVG(price) FROM product GROUP BY category;

-- 4. Клиенты с >1 заказом
SELECT c.first_name, c.last_name, COUNT(*)
FROM customer c JOIN "order" o ON c.id = o.customer_id
GROUP BY c.id HAVING COUNT(*) > 1;

-- 5. Заказы со статусом "новый"
SELECT o.id, c.email, p.description
FROM "order" o
JOIN customer c ON o.customer_id = c.id
JOIN product p ON o.product_id = p.id
JOIN order_status s ON o.status_id = s.id
WHERE s.name = 'новый';

-- 6. Обновить цену ноутбука
UPDATE product SET price = 62000 WHERE description = 'Ноутбук Dell';

-- 7. Увеличить количество флешек
UPDATE product SET quantity = quantity + 10 WHERE description = 'USB-флешка 64GB';

-- 8. Изменить статус всех заказов Ивана на "оплачен"
UPDATE "order"
SET status_id = (SELECT id FROM order_status WHERE name = 'оплачен')
WHERE customer_id = (SELECT id FROM customer WHERE first_name = 'Иван' AND last_name = 'Иванов');

-- 9. Удалить клиентов без заказов
DELETE FROM customer WHERE id NOT IN (SELECT DISTINCT customer_id FROM "order");

-- 10. Удалить товары с нулевым остатком
DELETE FROM product WHERE quantity = 0;