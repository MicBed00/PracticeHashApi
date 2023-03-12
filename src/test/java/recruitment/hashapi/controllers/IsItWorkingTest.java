package recruitment.hashapi.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import recruitment.hashapi.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IsItWorkingTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void returnsProperAnswer() {
        ResponseEntity<String> response = template.getForEntity("/isItWorking", String.class);
        assertThat(response.getBody()).isEqualTo("I am working.");
    }

    @Test
    public void returnsUsers() {
        //given
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        users.add(new User());

        //when
        ResponseEntity<List> response = template.getForEntity("/users", List.class);

        //then
        assertThat(response.getBody().getClass()).isEqualTo(users.getClass());
        assertThat(response.getBody().size()).isEqualTo(users.size());
    }

    @Test
    public void returnHashUserMd5() {
        //when
        ResponseEntity<String> response = template.getForEntity("/user/1/md5", String.class);

        //then
        assertThat(response.getBody()).isEqualTo("ffff403ae48676f7ca05f8a7cd69f");
    }

    @Test
    public void shouldThrowUserExceptionFromHashUserMd5() {
        //when
        ResponseEntity<String> response = template.getForEntity("/user/6/md5", String.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("recruitment.hashapi.exceptions.UserException: No user with id 6");
    }

    @Test
    public void returnHashUserSha1() {
        //when
        ResponseEntity<String> response = template.getForEntity("/user/1/sha1", String.class);

        //then
        assertThat(response.getBody()).isEqualTo("1bec245e29157c3bace6bfa0bcd9d9e3c8141b9");
    }

    @Test
    public void shouldThrowUserExceptionFromHashUserSha1() {
        //when
        ResponseEntity<String> response = template.getForEntity("/user/6/sha1", String.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("recruitment.hashapi.exceptions.UserException: No user with id 6");
    }

//    @Test
//    public void returnUserPages() {
//        //given
//        User user = new User("Kowalsky", "John");
//        Page<User> expectedPage = new PageImpl<>(Collections.singletonList(user), PageRequest.of(0, 3), 1);
//        //when
////    ResponseEntity<Page> response = template.getForEntity("/users/pages?page=0&size=3", Page.class);
//        ResponseEntity<Page<User>> response = template.exchange("/users/pages?page=0&size=3",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<Page<User>>() {
//                });
//
//        //then
//        assertThat(response.getHeaders().size()).isEqualTo(3);
//        assertThat(response.getBody().getTotalPages()).isEqualTo(2);
//        assertThat(response.getBody().getContent().get(0)).isEqualTo(user);
//        assertThat(response.getBody()).isEqualTo(expectedPage);
//    }


}
