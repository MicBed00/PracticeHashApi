package recruitment.hashapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import recruitment.hashapi.exceptions.UserException;
import recruitment.hashapi.models.User;
import recruitment.hashapi.repositories.UserRepositorie;
import recruitment.hashapi.ui.UserInterface;
import recruitment.hashapi.utils.HashCalculator;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepositorie userRepositorie;

    @Autowired
    public static UserInterface ui;

    public UserService(UserRepositorie userRepositorie, UserInterface ui) {
        this.userRepositorie = userRepositorie;
        this.ui = ui;
    }

    public List<User> getUsers() {
        return userRepositorie.findAll();
    }


    public String hashUser(Long idUser, HashCalculator calc) {
        User user = userRepositorie.findById(idUser).orElseThrow(
                () -> new UserException(ui.messageBundle("userException", idUser))
        );

        String conUser;
        if (user.secondName == null) {
            conUser = user.firstName + user.lastName;
        } else {
            conUser = user.firstName + user.secondName + user.lastName;
        }
        return calc.hashCalc(conUser);
    }

    public Page<User> getPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return userRepositorie.findAll(pageable);
    }

}
