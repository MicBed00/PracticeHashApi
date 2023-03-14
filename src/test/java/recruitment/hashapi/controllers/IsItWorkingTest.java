package recruitment.hashapi.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import recruitment.hashapi.models.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static recruitment.hashapi.controllers.IsItWorkingTest.Page.Pageable.*;

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

    @Test
    public void returnUserPages() {
        //given
        User user = new User("Kowalsky", "John");
//        Page<User> expectedPage = new PageImpl<>(Collections.singletonList(user), PageRequest.of(0, 3), 1);
        //when
////    ResponseEntity<Page> response = template.getForEntity("/users/pages?page=0&size=3", Page.class);
        ResponseEntity<Page<User>> response = template.exchange("/users/pages?page=0&size=3",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Page<User>>() {
                });

//        //then
//        assertThat(response.getHeaders().size()).isEqualTo(3);
        assertThat(response.getBody().totalPages).isEqualTo(2);
//        assertThat(response.getBody().content.get(0)).isEqualTo(user);
        //assertThat(response.getBody()).isEqualTo(expectedPage);
    }


    public static class Page<T> {

        public List<T> content;
        public Pageable pageable;
        public boolean last;
        public int totalElements;
        public int totalPages;
        public Sort sort;
        public boolean first;
        public int number;
        public int numberOfElements;
        public int size;
        public boolean empty;

        public static class Pageable {

            public Sort sort;
            public int pageNumber;
            public int pageSize;
            public int offset;
            public boolean paged;
            public boolean unpaged;

            public static class Sort {

                public boolean sorted;
                public boolean unsorted;
                public boolean empty;
            }
        }
    }
}
