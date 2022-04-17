package heavynimbus.backend.controller.admin;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/hello")
@SecurityRequirement(name="jwt_auth")
public class HelloAdminController {

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public String helloWorld() {
    return "Hello World !";
  }
}
