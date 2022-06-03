package expert.musachev;

import expert.musachev.model.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class Main {
    private final String URL = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders headers = new HttpHeaders();


    public Main() {
        String sessionId = getAllUsers();
        System.out.println(sessionId);
        headers.set("cookie", sessionId);
    }

    public static void main(String[] args) {
        Main main = new Main();
        String result = main.createUser() +
                main.updateUser() +
                main.deleteUser(3L);
        System.out.println("Итоговый код - " + result);
    }

    public String getAllUsers() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(URL, String.class);
        System.out.println(forEntity);
        return String.join(";", Objects.requireNonNull(forEntity.getHeaders().get("set-cookie")));
    }

    public String createUser() {
        User user = new User(3L, "James", "Brown", (byte) 25);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.postForEntity(URL, entity, String.class).getBody();
    }

    public String updateUser() {
        User user = new User(3L, "Thomas", "Shelby", (byte) 25);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class).getBody();
    }

    public String deleteUser(@PathVariable Long id) {
        HttpEntity<User> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class).getBody();
    }
}
