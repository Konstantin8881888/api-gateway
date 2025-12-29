# API Gateway - Единая точка входа

API Gateway на базе Spring Cloud Gateway с Circuit Breaker паттерном.

## Назначение
Единая точка входа для всех клиентских запросов с функциями:
- Маршрутизация запросов к соответствующим сервисам
- Балансировка нагрузки через Eureka
- Circuit Breaker для отказоустойчивости

## Особенности
- Динамическая маршрутизация на основе Eureka Service Discovery
- Circuit Breaker на Resilience4j с кастомными fallback
- Поддержка HATEOAS в ответах
- Подробный мониторинг через Actuator endpoints
- Логирование всех запросов и ошибок

## Технологии
- Spring Boot 3.5.8
- Spring Cloud Gateway 2025.0.1
- Spring Cloud Netflix Eureka Client
- Resilience4j Circuit Breaker
- Java 17

---
## Маршрутизация

### Основные маршруты:
| Паттерн | Сервис | URI в Gateway |
|---------|--------|---------------|
| `/api/users/*` | User Service | `lb://USER-SERVICE` |
| `/api/notifications/*` | Notification Service | `lb://NOTIFICATION-SERVICE` |

### Фильтры:
- **RewritePath**: Преобразование путей
- **CircuitBreaker**: Отказоустойчивость
- **StripPrefix**: Удаление префиксов

---
## Запуск

    mvn spring-boot:run

## Проверка работоспособности
1. Health check: http://localhost:8080/actuator/health
2. Список маршрутов: http://localhost:8080/actuator/gateway/routes
3. Статус Circuit Breaker: http://localhost:8080/actuator/circuitbreakers
4. Тест user-service: http://localhost:8080/api/users
5. Тест notification-service: http://localhost:8080/api/notifications/health

---
## Ответы Fallback
При недоступности сервисов Gateway возвращает:

    {
        "message": "User Service is temporarily unavailable",
        "timestamp": "2025-12-28T18:48:22.839Z",
        "status": 503,
        "service": "user-service"
    }

---
## Мониторинг
### Actuator Endpoints:
- `/actuator/gateway/routes` - все зарегистрированные маршруты
- `/actuator/gateway/globalfilters` - глобальные фильтры
- `/actuator/gateway/routefilters` - фильтры маршрутов
- `/actuator/circuitbreakers` - состояние Circuit Breaker'ов

---
## Интеграция
Gateway автоматически обнаруживает сервисы через Eureka и использует имена сервисов для маршрутизации.

---