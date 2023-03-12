package recruitment.hashapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import recruitment.hashapi.exceptions.AlgorithmException;
import recruitment.hashapi.models.User;
import recruitment.hashapi.services.UserService;
import recruitment.hashapi.utils.HashCalculator;
import recruitment.hashapi.utils.Md5Alg;
import recruitment.hashapi.utils.Sh1Alg;

import java.util.List;

@RestController
public class IsItWorking {

  @Autowired
  private UserService userService;

  public IsItWorking(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/isItWorking")
  public String isItWorking() {
    return "I am working.";
  }

  @GetMapping("/users")
  public List<User> users() {
    return userService.getUsers();
  }

  //http://localhost:8080/users/pages?page=0&size=3
  @GetMapping("/users/pages")
  public Page<User> usersPages(@RequestParam int page, @RequestParam int size) {
    return userService.getPageable(page, size);
  }

  @GetMapping("/user/{id}/md5")
  public String hashUsersMd5(@PathVariable long id) {
    return userService.hashUser(id, generateHashAlg("MD5"));
  }

  @GetMapping("/user/{id}/sha1")
  public String hashUsersSha1(@PathVariable long id) {
    return  userService.hashUser(id, generateHashAlg("SHA-1"));
  }

  HashCalculator generateHashAlg(String nameAlg) {
    if(nameAlg.equals("MD5")) {
      return new Md5Alg();
    } else if(nameAlg.equals("SHA-1")) {
      return new Sh1Alg();
    }
   throw new AlgorithmException(UserService.ui.messageBundle("algorithmException", nameAlg));
  }
}
