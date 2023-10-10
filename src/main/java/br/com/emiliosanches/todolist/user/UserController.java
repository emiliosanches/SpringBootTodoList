package br.com.emiliosanches.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private IUserRepository userRepository;

  @PostMapping
  public ResponseEntity create(@RequestBody UserModel userModel) {
    var existingUser = this.userRepository.findByUsername(userModel.getUsername());

    if (existingUser != null) {
      System.out.println("Usuário já existe");
      return ResponseEntity.status(400).body("Usuário já existe");
    }

    var savedUser = this.userRepository.save(userModel);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
  }
}
