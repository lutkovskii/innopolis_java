package oop.streams;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.*;

import static java.util.Map.Entry;
import static java.util.stream.Collectors.*;

public class StreamMiniTasks_Extra {
    record Product(String id, String name, String category, BigDecimal price) {}
    record Inventory(String productId, int qty) {}
    record User(String id, String email, String city, int age) {}
    enum Level { INFO, WARN, ERROR }
    record LogEntry(LocalDateTime ts, String service, Level level, String msg) {}
    record Transaction(String id, String userId, BigDecimal amount, LocalDate date) {}

    public static void main(String[] args) {
        List<Product> products = List.of(
                new Product("P1", "MacBook Air", "ELECTRONICS", bd("1199.00")),
                new Product("P2", "Desk", "HOME", bd("199.90")),
                new Product("P3", "Office Chair", "HOME", bd("129.00")),
                new Product("P4", "Java Book", "BOOKS", bd("49.00"))
        );
        Map<String, Product> productById = products.stream()
                .collect(toMap(Product::id, Function.identity()));

        List<Inventory> stock = List.of(
                new Inventory("P1", 3),
                new Inventory("P2", 10),
                new Inventory("P3", 5),
                new Inventory("P4", 20)
        );

        List<User> users = List.of(
                new User("U1", "anna@example.com", "Bishkek", 19),
                new User("U2", "anna@example.com", "Bishkek", 19), // дубликат по email (для задач на дедуп)
                new User("U3", "boris@example.com", "Almaty", 35),
                new User("U4", "chen@example.com", "Bishkek", 28),
                new User("U5", "dina@example.com", "Tashkent", 16)
        );

        List<LogEntry> logs = List.of(
                new LogEntry(LocalDateTime.now(), "auth", Level.INFO,  "login ok"),
                new LogEntry(LocalDateTime.now(), "auth", Level.ERROR, "bad credentials"),
                new LogEntry(LocalDateTime.now(), "orders", Level.WARN, "slow query"),
                new LogEntry(LocalDateTime.now(), "orders", Level.ERROR,"timeout"),
                new LogEntry(LocalDateTime.now(), "orders", Level.ERROR,"db down")
        );

        List<Transaction> txs = List.of(
                new Transaction("T1","U1", bd("120.00"), LocalDate.of(2025,9,1)),
                new Transaction("T2","U3", bd("499.99"), LocalDate.of(2025,9,2)),
                new Transaction("T3","U4", bd("79.50"),  LocalDate.of(2025,9,2)),
                new Transaction("T4","U3", bd("59.00"),  LocalDate.of(2025,9,3)),
                new Transaction("T5","U1", bd("20.00"),  LocalDate.of(2025,9,3))
        );

        /* ========================= ЗАДАЧИ ========================= */

        // 28) Оценка стоимости склада (stock value): Σ(price * qty).
        // — вход: stock + productById
        // — приём: stream по складу → умножаем BigDecimal → reduce c identity BigDecimal.ZERO
        {
            BigDecimal totalStockValue = stock.stream()
                    // мапим позицию склада в денежную стоимость: price(productId) * qty
                    .map(inv -> productById.get(inv.productId()).price().multiply(BigDecimal.valueOf(inv.qty())))
                    // суммируем денежные значения: identity + accumulator
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            System.out.println("28) total stock value = " + totalStockValue); // 1199*3 + 199.9*10 + 129*5 + 49*20
        }

        // 29) Частоты слов (реальный кейс: подсчёт частоты тегов/слов в логе/текстах).
        // — вход: список строк (слов)
        // — приём: toMap с merge-функцией или groupingBy + counting
        {
            List<String> words = List.of("apple","banana","apple","cherry","banana","apple");
            // Вариант 1: groupingBy + counting
            Map<String, Long> freq1 = words.stream().collect(groupingBy(Function.identity(), counting()));
            // Вариант 2: toMap + merge (Long::sum)
            Map<String, Long> freq2 = words.stream().collect(
                    toMap(Function.identity(), w -> 1L, Long::sum)
            );
            System.out.println("29) freq1=" + freq1 + ", freq2=" + freq2);
        }

        // 30) Топ-N по ключу (топ-2 сервисов по количеству ERROR в логах).
        // — вход: logs
        // — приём: фильтр ERROR → groupingBy(service,counting) → сортировка по значению убыв. → limit(N)
        {
            int N = 2;
            List<Entry<String, Long>> topServicesByError = logs.stream()
                    .filter(le -> le.level() == Level.ERROR)
                    .collect(groupingBy(LogEntry::service, counting()))
                    .entrySet().stream()
                    .sorted(Comparator.<Entry<String, Long>>comparingLong(Entry::getValue).reversed())
                    .limit(N)
                    .toList();

            System.out.println("30) top services by ERROR = " + topServicesByError);
        }

        // 31) Слияние двух мап: цены + скидки → финальные цены.
        // — вход: Map<productId, price>, Map<productId, discountPercent>
        // — приём: stream по entrySet() «базовой» мапы + toMap(...)
        {
            Map<String, BigDecimal> priceMap = products.stream()
                    .collect(toMap(Product::id, Product::price));
            Map<String, BigDecimal> discountPct = Map.of("P1", bd("10"), "P3", bd("5")); // проценты, например 10% и 5%

            Map<String, BigDecimal> finalPrice = priceMap.entrySet().stream()
                    .collect(toMap(
                            Entry::getKey,
                            e -> {
                                String pid = e.getKey();
                                BigDecimal base = e.getValue();
                                BigDecimal pct = discountPct.getOrDefault(pid, BigDecimal.ZERO);
                                // price * (1 - pct/100)
                                return base.multiply(BigDecimal.ONE.subtract(pct.divide(bd("100"))));
                            }
                    ));

            System.out.println("31) finalPrice=" + finalPrice);
        }

        // 32) Индекс по полю: Map<city, List<User>> (с сортировкой внутри по email) + неизменяемость.
        // — приём: groupingBy(city, mapping(..., toList)) → post-processing sort → unmodifiable
        {
            Map<String, List<User>> byCity = users.stream()
                    .collect(groupingBy(User::city, mapping(Function.identity(), toList())));

            byCity.values().forEach(list -> list.sort(Comparator.comparing(User::email)));
            byCity = Collections.unmodifiableMap(byCity);

            System.out.println("32) users by city (immutable)=" + byCity);
        }

        // 33) Дедупликация по произвольному ключу (email) — «distinct by key».
        // — кейс: импорт пользователей и оставляем первого по email.
        // — приём: toMap(email, user, (keepFirst, keepNext) -> keepFirst) → values()
        {
            List<User> dedupUsers = users.stream()
                    .collect(toMap(User::email, Function.identity(), (u1, u2) -> u1))
                    .values().stream()
                    .toList();

            System.out.println("33) dedup by email = " + dedupUsers);
        }

        // 34) «Уникальные по ключу» через предикат (подходит для фильтра в середине пайплайна).
        // — потокобезопасный вариант для параллельных стримов: ConcurrentHashMap.newKeySet()
        {
            Set<String> seen = ConcurrentHashMap.newKeySet();
            List<User> uniqueByEmail = users.stream()
                    .filter(u -> seen.add(u.email())) // add() вернёт false, если такой email уже был
                    .toList();

            System.out.println("34) uniqueByEmail = " + uniqueByEmail);
        }

        // 35) Сводная статистика по транзакциям: сумма, среднее, min, max, count (BigDecimal-friendly).
        // — приём: reduce в кастомный аккумулятор + комбайнер (работает и в parallel), либо Collector.of(...)
        {
            var stats = txs.stream()
                    .map(Transaction::amount)
                    .collect(Collector.of(
                            MoneyStats::new,           // supplier — создаёт пустую «станцию»
                            MoneyStats::accumulate,    // accumulator — вбирает одно значение
                            MoneyStats::combine,       // combiner — сливает две станции
                            Function.identity()        // finisher — вернуть как есть
                    ));

            System.out.println("35) money stats = " + stats + ", avg=" + stats.avg());
        }

        // 36) reduce (1-аргументный): найти транзакцию с максимальной суммой (аналог max()).
        // — приём: txs.stream().reduce( (t1,t2) -> t1.amount>t2.amount ? t1 : t2 )
        {
            txs.stream()
                    .reduce((a, b) -> a.amount().compareTo(b.amount()) >= 0 ? a : b)
                    .ifPresent(maxTx -> System.out.println("36) max tx by amount = " + maxTx));
        }

        // 37) reduce (2-арг) к сумме BigDecimal: аккуратная сумма amounts.
        // — приём: identity + accumulator (ассоциативная операция)
        {
            BigDecimal total = txs.stream()
                    .map(Transaction::amount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            System.out.println("37) total amount = " + total);
        }

        // 38) reduce (3-арг) для параллельного суммирования (identity + accumulator + combiner).
        // — кейс: сумма квадратов qty на складе (просто как пример несамой тривиальной формулы)
        {
            int sumSquaresQty = stock.parallelStream()
                    .reduce(
                            0,                                  // identity, подаётся в каждую подзадачу
                            (acc, inv) -> acc + inv.qty()*inv.qty(), // accumulator: суммируем квадраты
                            Integer::sum                        // combiner: складываем частичные суммы
                    );

            System.out.println("38) parallel sum of qty^2 = " + sumSquaresQty);
        }

        // 39) Группировки с последующей сортировкой по ключу (TreeMap): заказы/транзакции по дню.
        // — приём: groupingBy(date, TreeMap::new, counting())
        {
            Map<LocalDate, Long> txCountPerDay = txs.stream()
                    .collect(groupingBy(Transaction::date, TreeMap::new, counting()));

            System.out.println("39) txCountPerDay (TreeMap sorted) = " + txCountPerDay);
        }

        // 40) ТОП-3 категорий по обороту: join tx → (здесь просто фейковая логика по продуктам со склада)
        // Реалистичнее было бы суммировать OrderItems * price, но для примера возьмём stock как «продажи».
        {
            Map<String, Integer> qtyByProduct = stock.stream()
                    .collect(toMap(Inventory::productId, Inventory::qty));
            // revenue по категории = Σ (qty(productId) * price(productId))
            Map<String, BigDecimal> revenueByCategory = products.stream()
                    .collect(groupingBy(Product::category,
                            mapping(p -> p.price().multiply(BigDecimal.valueOf(qtyByProduct.getOrDefault(p.id(), 0))),
                                    reducing(BigDecimal.ZERO, BigDecimal::add))));

            List<Entry<String, BigDecimal>> top3 = revenueByCategory.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(3)
                    .toList();

            System.out.println("40) top-3 categories by revenue = " + top3);
        }

        // 41) Построить индекс по первым буквам email: Map<Character, Set<User>>
        // — приём: groupingBy(firstLetter, mapping(user, toCollection(TreeSet по email)))
        {
            Map<Character, Set<User>> index = users.stream()
                    .collect(groupingBy(
                            u -> u.email().charAt(0),
                            mapping(Function.identity(),
                                    toCollection(() -> new TreeSet<>(Comparator.comparing(User::email))))
                    ));

            System.out.println("41) email index = " + index);
        }

        // 42) Параллельный денежный расчёт (BigDecimal) с безопасным комбинированием.
        // — кейс: «сумма чеков по пользователям» → Map<userId, BigDecimal> (конкурентный коллектор).
        {
            Map<String, BigDecimal> totalByUser = txs.parallelStream()
                    .collect(toConcurrentMap(
                            Transaction::userId,            // ключ — userId
                            Transaction::amount,            // значение — amount
                            BigDecimal::add                 // при коллизии ключей — складываем
                    ));

            System.out.println("42) totalByUser (parallel, concurrent) = " + totalByUser);
        }

        // 43) «Самый проблемный» сервис по ERROR в логах — одной цепочкой с Optional.
        // — приём: filter → groupingBy(counting) → maxBy(Map.Entry.comparingByValue)
        {
            logs.stream()
                    .filter(le -> le.level() == Level.ERROR)
                    .collect(groupingBy(LogEntry::service, counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .ifPresent(e -> System.out.println("43) worst service = " + e.getKey() + " (" + e.getValue() + " ERROR)"));
        }

        // 44) Разбиение пользователей по совершеннолетию + сводка по группам (count/avgAge).
        // — приём: partitioningBy → downstream: summarizingInt
        {
            Map<Boolean, IntSummaryStatistics> stats = users.stream()
                    .collect(partitioningBy(u -> u.age() >= 18, summarizingInt(User::age)));

            System.out.println("44) minors="+ stats.get(false) + " | adults=" + stats.get(true));
        }

        // 45) Безопасная сортировка по BigDecimal в убыв., потом по name (категория).
        // — приём: Comparator.comparing(Product::price).reversed().thenComparing(Product::name)
        {
            List<Product> sorted = products.stream()
                    .sorted(Comparator.<Product, BigDecimal>comparing(Product::price)
                            .reversed()
                            .thenComparing(Product::name))
                    .toList();

            System.out.println("45) products sorted (price desc, name) = " + sorted);
        }
    }

    /* ===== Вспомогательные вещи ===== */

    // Удобный конструктор BigDecimal из строки (без проблем double-конструктора)
    private static BigDecimal bd(String s) { return new BigDecimal(s); }

    /**
     * Аккумулятор для денежной статистики:
     *  — sum, min, max, count  (avg считается на лету при необходимости)
     * Работает как в последовательных, так и в параллельных стримах (есть combine()).
     */
    static class MoneyStats {
        private BigDecimal sum = BigDecimal.ZERO;
        private BigDecimal min = null;
        private BigDecimal max = null;
        private long count = 0;

        void accumulate(BigDecimal x) {
            // обновляем сумму
            sum = sum.add(x);
            // минимумы/максимумы с учётом null при первом элементе
            min = (min == null || x.compareTo(min) < 0) ? x : min;
            max = (max == null || x.compareTo(max) > 0) ? x : max;
            count++;
        }

        MoneyStats combine(MoneyStats other) {
            // комбинируем две «станции»: суммы, счётчики и границы
            this.sum = this.sum.add(other.sum);
            this.count += other.count;
            if (this.min == null || (other.min != null && other.min.compareTo(this.min) < 0)) this.min = other.min;
            if (this.max == null || (other.max != null && other.max.compareTo(this.max) > 0)) this.max = other.max;
            return this;
        }

        BigDecimal avg() {
            return count == 0 ? BigDecimal.ZERO : sum.divide(BigDecimal.valueOf(count), 2, java.math.RoundingMode.HALF_UP);
        }

        @Override public String toString() {
            return "MoneyStats{sum=" + sum + ", min=" + min + ", max=" + max + ", count=" + count + "}";
        }
    }
}
