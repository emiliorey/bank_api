### H2 Console
*   **`URL = http://localhost:8080/h2-console/login.jsp?jsessionid=8aac6476d5d32e05252c726984655840`
*   **`JDBC URL=jdbc:h2:mem:bankdb`
*   **`USER = SA`
*   **`PASS =`


### Puntos clave de la configuración circuitBreaker:

*   **`resilience4j.circuitbreaker.configs.default`**: Define una configuración base que se puede reutilizar en múltiples instancias.
*   **`resilience4j.circuitbreaker.instances`**: Define configuraciones específicas para diferentes servicios o componentes de tu aplicación. El nombre de la instancia (ej. `servicioExternoA`) se usa en el código con la anotación `@CircuitBreaker(name = "servicioExternoA")`.
*   **`slidingWindowSize`**: El número de llamadas que se registran para calcular la tasa de error.
*   **`failureRateThreshold`**: El umbral de porcentaje de fallos que, si se supera, abre el circuito.
*   **`waitDurationInOpenState`**: El tiempo que el circuito permanece abierto antes de pasar al estado *half-open* (medio abierto) para permitir algunas llamadas de prueba.
*   **`permittedNumberOfCallsInHalfOpenState`**: El número de llamadas permitidas en estado *half-open*.
*   **`registerHealthIndicator`**: Permite exponer la salud del circuit breaker a través de los *endpoints* de Spring Boot Actuator (ej. `/actuator/health`).


