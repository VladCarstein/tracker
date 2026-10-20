# Task Tracker API

REST API для управления задачами и проектами. Учебный pet-проект.

## Стек

- Java, Spring Boot 4 (Web, Data JPA, Validation)
- PostgreSQL 16 (в Docker)
- Flyway: миграции базы данных
- JUnit 5 + Mockito: unit-тесты
- Swagger UI
- Maven
- Lombok

## Что умеет

- Задачи: создание, просмотр, обновление, удаление
- Проекты и привязка задач к проектам (`@ManyToOne`)
- Фильтрация задач по проекту: `GET /api/tasks?projectId=1`
- Валидация входных данных (@Valid)
- DTO
- Структура базы описана миграциями Flyway, Hibernate только проверяет её.

## Быстрый старт

Нужны: JDK 17+, Docker.

```bash
# 1. Запустить PostgreSQL
docker compose up -d

# 2. Запустить приложение
.\mvnw.cmd spring-boot:run
```

Приложение стартует на `http://localhost:8080`, миграции применятся автоматически.

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Эндпоинты

| Метод | Адрес | Описание |
|-------|-------|----------|
| GET | `/api/tasks` | Список задач |
| GET | `/api/tasks/{id}` | Задача по id |
| POST | `/api/tasks` | Создать задачу |
| PUT | `/api/tasks/{id}` | Обновить задачу |
| DELETE | `/api/tasks/{id}` | Удалить задачу |
| GET | `/api/projects` | Список проектов |
| POST | `/api/projects` | Создать проект |

Примеры запросов лежат в файле `requests.http`.

Пример создания задачи:

`projectId` необязателен. Если указан, проект с таким id должен существовать (иначе 404).
```json
POST /api/tasks
{
  "title": "Первая задача",
  "description": "Описание",
  "projectId": 1
}
```

Статусы задачи: `TODO`, `IN_PROGRESS`, `COMPLETED`.

## Тесты

```bash
./mvnw test
```

## Структура проекта

```
controller  - HTTP-слой
service     - бизнес-логика
repository  - работа с БД (Spring Data JPA)
entity      - JPA-сущности
dto         - объекты запросов и ответов
exception   - свои исключения и глобальный обработчик ошибок
```

## Планы

- [ ] Авторизация (Spring Security + JWT)