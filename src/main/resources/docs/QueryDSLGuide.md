# Библиотека QueryDSL: Полное руководство

## Введение
QueryDSL — это мощная библиотека для создания безопасных и гибких запросов к базе данных на Java. Она позволяет писать типизированные запросы с использованием Java API, что улучшает читаемость, предотвращает ошибки времени выполнения и избавляет от необходимости писать SQL вручную.

## Установка
### Подключение QueryDSL в Maven
Добавьте зависимости в `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-jpa</artifactId>
        <version>5.0.0</version>
    </dependency>
    <dependency>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-apt</artifactId>
        <version>5.0.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### Подключение QueryDSL в Gradle
Для использования QueryDSL с Gradle добавьте в `build.gradle`:

```gradle
dependencies {
    implementation 'com.querydsl:querydsl-jpa:5.0.0'
    annotationProcessor 'com.querydsl:querydsl-apt:5.0.0'
}
```

## Генерация метамоделей
QueryDSL использует классы-модели (Q-классы), которые генерируются на основе сущностей JPA.
Для их создания используйте:

```shell
mvn compile
```

После этого появятся файлы вида `QEntityName.java` в каталоге `target/generated-sources/`.

## Базовые операции QueryDSL
### Создание экземпляра JPAQuery

```java
JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
QUser qUser = QUser.user;
```

### Простые запросы

```java
List<User> users = queryFactory.selectFrom(qUser)
        .where(qUser.age.gt(18))
        .fetch();
```

### Использование предикатов
```java
BooleanExpression isAdult = qUser.age.gt(18);
List<User> adults = queryFactory.selectFrom(qUser)
        .where(isAdult)
        .fetch();
```

## Объединения (Joins)
### Внутреннее объединение
```java
QOrder qOrder = QOrder.order;
QUser qUser = QUser.user;

List<Order> orders = queryFactory.selectFrom(qOrder)
        .join(qOrder.user, qUser)
        .where(qUser.name.eq("John"))
        .fetch();
```

### Левое соединение (LEFT JOIN)
```java
List<User> usersWithOrders = queryFactory.selectFrom(qUser)
        .leftJoin(qUser.orders, qOrder).fetchJoin()
        .fetch();
```

## Группировка и агрегация

```java
List<Tuple> results = queryFactory.select(qUser.age, qUser.count())
        .from(qUser)
        .groupBy(qUser.age)
        .fetch();
```

## Подзапросы
```java
QUser qUserSub = new QUser("userSub");

List<User> users = queryFactory.selectFrom(qUser)
        .where(qUser.age.eq(
                JPAExpressions.select(qUserSub.age.max())
                        .from(qUserSub)))
        .fetch();
```

## Динамические запросы с BooleanBuilder
Если условия выборки неизвестны заранее, можно использовать `BooleanBuilder`:

```java
public List<User> getUsers(String name, Integer minAge, Integer maxAge) {
    QUser qUser = QUser.user;
    BooleanBuilder predicate = new BooleanBuilder();
    
    if (name != null) {
        predicate.and(qUser.name.eq(name));
    }
    if (minAge != null) {
        predicate.and(qUser.age.goe(minAge));
    }
    if (maxAge != null) {
        predicate.and(qUser.age.loe(maxAge));
    }
    
    return queryFactory.selectFrom(qUser)
            .where(predicate)
            .fetch();
}
```

### Пример вызова метода:
```java
List<User> users = getUsers("Alice", 18, 30);
```

## Пагинация
QueryDSL поддерживает ограничение количества возвращаемых записей и смещение (`offset` и `limit`):

```java
List<User> users = queryFactory.selectFrom(qUser)
        .orderBy(qUser.name.asc())
        .offset(10)  // пропустить первые 10 записей
        .limit(5)    // взять следующие 5 записей
        .fetch();
```

## Сортировка с OrderSpecifier
Можно динамически сортировать результаты запроса:

```java
public List<User> getUsersSorted(String sortBy, boolean asc) {
    OrderSpecifier<?> orderSpecifier = asc ? qUser.name.asc() : qUser.name.desc();
    return queryFactory.selectFrom(qUser)
            .orderBy(orderSpecifier)
            .fetch();
}
```

Пример вызова:
```java
List<User> users = getUsersSorted("name", true);
```

## Интеграция с Spring Data JPA
QueryDSL можно использовать вместе с `Spring Data JPA` для построения динамических запросов:

```java
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {}
```

Теперь можно использовать QueryDSL-предикаты при вызове репозитория:
```java
BooleanExpression isAdult = qUser.age.gt(18);
List<User> adults = userRepository.findAll(isAdult);
```

## Заключение
QueryDSL делает запросы к базе данных более читаемыми, безопасными и мощными. С помощью него можно работать с предикатами, объединениями, группировками, подзапросами, динамической фильтрацией, пагинацией и сортировкой, а также интегрировать его с Spring Data JPA для удобного построения динамических запросов.
