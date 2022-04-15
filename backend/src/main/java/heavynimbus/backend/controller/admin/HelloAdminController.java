package heavynimbus.backend.controller.admin;

import heavynimbus.backend.controller.doc.HelloControllerDocumentation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/hello")
public class HelloAdminController implements HelloControllerDocumentation {

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public String helloWorld() {
    return "Hello World !";
  }
}
