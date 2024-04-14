package ru.alishev.springcourse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;

@SpringBootApplication
public class RestClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RestClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://94.198.50.185:7081/api/users";
        ResponseEntity<String> response;

        // Получение списка всех пользователей и сохранение session id
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);
        headers.add("Cookie", response.getHeaders().getFirst("Set-Cookie"));

        // Добавление пользователя
        User newUser = new User(3L, "James", "Brown", (byte) 20);
        HttpEntity<User> entityCreateUser = new HttpEntity<>(newUser, headers);
        response = restTemplate.exchange(baseUrl, HttpMethod.POST, entityCreateUser, String.class);
        String part1 = response.getBody(); // Получите первую часть кода

        // Обновление пользователя
        User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 20);
        HttpEntity<User> entityUpdateUser = new HttpEntity<>(updatedUser, headers);
        response = restTemplate.exchange(baseUrl, HttpMethod.PUT, entityUpdateUser, String.class);
        String part2 = response.getBody(); // Получите вторую часть кода

        // Удаление пользователя
        HttpEntity<String> entityDeleteUser = new HttpEntity<>(headers);
        response = restTemplate.exchange(baseUrl + "/" + newUser.getId(), HttpMethod.DELETE, entityDeleteUser, String.class);
        String part3 = response.getBody(); // Получите последнюю часть кода

        // Комбинирование частей кода
        String finalCode = part1 + part2 + part3;
        System.out.println("Итоговый код: " + finalCode);
    }
}

class User {
    private Long id;
    private String name;
    private String lastName;
    private Byte age;

    public User(Long id, String name, String lastName, Byte age) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }
// Конструкторы, геттеры и сеттеры
}