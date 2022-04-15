package heavynimbus.backend.controller.user;

import heavynimbus.backend.controller.doc.HelloControllerDocumentation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/hello")
public class HelloUserController implements HelloControllerDocumentation {

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public String helloWorld() {
    return "Hello World !";
  }
}
