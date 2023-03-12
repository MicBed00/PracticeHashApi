package recruitment.hashapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import recruitment.hashapi.models.User;
import recruitment.hashapi.repositories.UserRepositorie;
import recruitment.hashapi.ui.UserInterface;
import recruitment.hashapi.utils.HashCalculator;
import recruitment.hashapi.utils.Md5Alg;
import recruitment.hashapi.utils.Sh1Alg;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepositorie repositorie;

    @Mock
    UserInterface ui;

    @Mock
    Page page;

    UserService service;

    @BeforeEach
    void setUp() {
        service = new UserService(repositorie, ui, page);
    }

    @Test
    public void returnUsers() {
        //given
        List<User> users = new ArrayList<>();
        //when
        when(repositorie.findAll()).thenReturn(users);
        List<User> result = service.getUsers();

        //then
        assertThat(result).isEqualTo(users);
    }

    @Test
    public void returnHashUserMd5() {
        //given
        Long id1 = 1L;
        Long id2 = 2L;
        HashCalculator md5 = new Md5Alg();
        List<User> users = List.of(new User("Kowalsky", "John"),
                new User("Nowak", "Adam", "Piotr"));

        //when
        when(repositorie.findById(id1)).thenReturn(Optional.of(users.get(0)));
        String result1 = service.hashUser(id1, md5);
        when(repositorie.findById(id2)).thenReturn(Optional.of(users.get(1)));
        String result2 = service.hashUser(id2, md5);

        //then
        assertThat(result1).isEqualTo("ffff403ae48676f7ca05f8a7cd69f");
        assertThat(result2).isEqualTo("d61be5ae95619c6116c22bc44218e6");
    }

    @Test
    public void returnHashUserSha1() {
        //given
        Long id1 = 1L;
        Long id2 = 2L;
        HashCalculator sha1 = new Sh1Alg();
        List<User> users = List.of(new User("Kowalsky", "John"),
                new User("Nowak", "Adam", "Piotr"));

        //when
        when(repositorie.findById(id1)).thenReturn(Optional.of(users.get(0)));
        String result1 = service.hashUser(id1, sha1);
        when(repositorie.findById(id2)).thenReturn(Optional.of(users.get(1)));
        String result2 = service.hashUser(id2, sha1);

        //then
        assertThat(result1).isEqualTo("1bec245e29157c3bace6bfa0bcd9d9e3c8141b9");
        assertThat(result2).isEqualTo("be2835dfb9a35b7f52a3207b5fffc74d7d132139");
    }

    @Test
    public void retrunPageable() {
        //given
        int page = 0;
        int size = 1;
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        users.add(new User());
        Pageable pageable = PageRequest.of(page,size);
        Page<User> pageUser= Mockito.mock(Page.class);

        //when
        when(repositorie.findAll(pageable)).thenReturn(pageUser);
        Page<User> result = service.getPageable(page, size);

        //then
        assertThat(result.getClass()).isEqualTo(pageUser.getClass());
        verify(repositorie, times(1)).findAll(pageable);
    }


}