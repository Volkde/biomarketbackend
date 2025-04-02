# Классы Order и OrderSpecifier в QueryDSL

## Введение
В QueryDSL классы `Order` и `OrderSpecifier` используются для сортировки результатов запросов. Они позволяют задать порядок сортировки (по возрастанию или убыванию) и применить его к определённым полям сущностей.

## Класс Order
`Order` — это перечисление (`enum`), определяющее направление сортировки:

```java
public enum Order {
    ASC,  // Сортировка по возрастанию
    DESC  // Сортировка по убыванию
}
```

## Класс OrderSpecifier
`OrderSpecifier` позволяет задать порядок сортировки для конкретного выражения.

### Конструктор:
```java
public OrderSpecifier(Order order, Expression<T> target)
```
- `order` — направление сортировки (`Order.ASC` или `Order.DESC`).
- `target` — поле, по которому будет производиться сортировка.

## Примеры использования

### 1. Простая сортировка по одному полю

Допустим, у нас есть сущность `User`:

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private int age;
    // Геттеры и сеттеры
}
```

Теперь мы можем отсортировать пользователей по имени в порядке возрастания:

```java
JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
QUser qUser = QUser.user;

List<User> users = queryFactory.selectFrom(qUser)
        .orderBy(new OrderSpecifier<>(Order.ASC, qUser.name))
        .fetch();
```

### 2. Сортировка по нескольким полям

Можно сортировать по нескольким критериям. Например, сначала по фамилии (по возрастанию), а затем по возрасту (по убыванию):

```java
List<User> users = queryFactory.selectFrom(qUser)
        .orderBy(
            new OrderSpecifier<>(Order.ASC, qUser.lastName),
            new OrderSpecifier<>(Order.DESC, qUser.age)
        )
        .fetch();
```

### 3. Использование статических методов `OrderSpecifier`
QueryDSL предоставляет удобные статические методы `ExpressionUtils.asc()` и `ExpressionUtils.desc()`:

```java
import com.querydsl.core.types.dsl.Expressions;

List<User> users = queryFactory.selectFrom(qUser)
        .orderBy(Expressions.stringPath("name").asc())
        .fetch();
```

### 4. Динамическая сортировка
Можно передавать список `OrderSpecifier` в зависимости от условий:

```java
public List<User> getUsersSorted(String sortBy, boolean asc) {
    Order order = asc ? Order.ASC : Order.DESC;
    PathBuilder<User> entityPath = new PathBuilder<>(User.class, "user");
    OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(order, entityPath.get(sortBy));

    return queryFactory.selectFrom(QUser.user)
            .orderBy(orderSpecifier)
            .fetch();
}
```

Пример вызова метода:

```java
List<User> sortedUsers = getUsersSorted("lastName", true);
```

### 5. Сортировка с учетом null-значений
QueryDSL позволяет обрабатывать `null`-значения с помощью `NullHandling`:

```java
List<User> users = queryFactory.selectFrom(qUser)
        .orderBy(new OrderSpecifier<>(Order.ASC, qUser.name, NullHandling.NullsLast))
        .fetch();
```

Опции `NullHandling`:
- `NullHandling.NullsFirst` — `null`-значения идут первыми.
- `NullHandling.NullsLast` — `null`-значения идут последними.

### 6. Сортировка с использованием `BooleanBuilder`
Если у нас есть сложная логика фильтрации, можно использовать `BooleanBuilder` и динамически добавлять `OrderSpecifier`:

```java
public List<User> getFilteredAndSortedUsers(String sortBy, boolean asc, Integer minAge, Integer maxAge) {
    QUser qUser = QUser.user;
    BooleanBuilder predicate = new BooleanBuilder();

    if (minAge != null) {
        predicate.and(qUser.age.goe(minAge));
    }
    if (maxAge != null) {
        predicate.and(qUser.age.loe(maxAge));
    }

    Order order = asc ? Order.ASC : Order.DESC;
    PathBuilder<User> entityPath = new PathBuilder<>(User.class, "user");
    OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(order, entityPath.get(sortBy));

    return queryFactory.selectFrom(qUser)
            .where(predicate)
            .orderBy(orderSpecifier)
            .fetch();
}
```

Пример вызова:

```java
List<User> users = getFilteredAndSortedUsers("age", false, 20, 50);
```

## Заключение
Классы `Order` и `OrderSpecifier` в QueryDSL обеспечивают удобную и гибкую сортировку данных. С их помощью можно:
- сортировать по одному или нескольким полям,
- использовать статические методы `ExpressionUtils.asc()` и `ExpressionUtils.desc()`,
- динамически определять поле и порядок сортировки,
- обрабатывать `null`-значения,
- комбинировать сортировку с фильтрацией.

Эти возможности делают QueryDSL мощным инструментом для написания безопасных и удобочитаемых запросов в Java-приложениях.
