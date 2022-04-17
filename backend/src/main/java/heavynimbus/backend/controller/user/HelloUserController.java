package heavynimbus.backend.controller.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/hello")
@SecurityRequirement(name="jwt_auth")
public class HelloUserController {

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public String helloWorld() {
    return "Hello World !";
  }
}
