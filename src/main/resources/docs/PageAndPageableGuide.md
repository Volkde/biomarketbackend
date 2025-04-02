# Объект Pageable и Page в Spring Data JPA

## Введение

`Pageable` — это интерфейс в Spring Data JPA, который упрощает работу с пагинацией и сортировкой данных в базах данных. Он позволяет получать только определенное количество записей за один запрос, что улучшает производительность и снижает нагрузку на сервер.

Spring Data JPA предоставляет несколько ключевых классов и интерфейсов для работы с пагинацией:
- `Pageable` — интерфейс, определяющий параметры пагинации.
- `PageRequest` — реализация `Pageable`, используемая для создания объектов пагинации.
- `Page<T>` — контейнер, содержащий данные запрашиваемой страницы.
- `Slice<T>` — облегченная версия `Page<T>`, содержащая информацию только о наличии следующей страницы.

## 1. Создание объекта Pageable

Объект `Pageable` можно создать с помощью `PageRequest`. Он принимает номер страницы и количество элементов на странице:

```java
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

Pageable pageable = PageRequest.of(0, 10); // Первая страница, 10 записей на страницу
```

Также можно передать параметры сортировки:

```java
import org.springframework.data.domain.Sort;

Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
```

## 2. Использование Pageable в репозитории

Spring Data JPA поддерживает работу с `Pageable` в методах репозитория:

```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
}
```

Запрос с пагинацией:

```java
Pageable pageable = PageRequest.of(1, 5); // Вторая страница, 5 записей
Page<User> userPage = userRepository.findAll(pageable);

List<User> users = userPage.getContent(); // Получение данных
```

## 3. Объект Page<T>

Объект `Page<T>` предоставляет удобные методы для работы с результатами пагинации:

```java
int totalPages = userPage.getTotalPages(); // Общее количество страниц
long totalElements = userPage.getTotalElements(); // Общее количество записей
int pageSize = userPage.getSize(); // Размер страницы
int pageNumber = userPage.getNumber(); // Номер текущей страницы
boolean isFirst = userPage.isFirst(); // Проверка, является ли текущая страница первой
boolean isLast = userPage.isLast(); // Проверка, является ли текущая страница последней
boolean hasNext = userPage.hasNext(); // Есть ли следующая страница
boolean hasPrevious = userPage.hasPrevious(); // Есть ли предыдущая страница
```

### Пример использования Page<T>

```java
Page<User> userPage = userRepository.findAll(PageRequest.of(0, 10));

System.out.println("Всего страниц: " + userPage.getTotalPages());
System.out.println("Всего элементов: " + userPage.getTotalElements());
System.out.println("Текущий номер страницы: " + userPage.getNumber());
System.out.println("Количество элементов на странице: " + userPage.getSize());
```

## 4. Использование Slice<T>

`Slice<T>` похож на `Page<T>`, но не запрашивает общее количество записей, что делает его более производительным:

```java
Slice<User> userSlice = userRepository.findAll(pageable);
boolean hasNextPage = userSlice.hasNext();
```

## 5. Пагинация в контроллере

Spring поддерживает автоматическую обработку `Pageable` в `@RestController`:

```java
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
```

Теперь можно делать запросы с параметрами:

```
GET /users?page=0&size=5&sort=name,asc
```

## Заключение

Использование `Pageable` и `Page<T>` в Spring Data JPA позволяет эффективно работать с большими объемами данных, улучшая производительность запросов. `Page<T>` предоставляет удобные методы для работы с информацией о страницах, а `Slice<T>` позволяет оптимизировать запросы, когда не требуется общее количество записей. Эти инструменты упрощают реализацию пагинации и сортировки в репозиториях, сервисах и контроллерах, делая код более читаемым и удобным.
