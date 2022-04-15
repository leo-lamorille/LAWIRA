package heavynimbus.backend.controller;

import heavynimbus.backend.controller.doc.LoginControllerDocumentation;
import heavynimbus.backend.dto.login.LoginRequest;
import heavynimbus.backend.dto.login.LoginResponse;
import heavynimbus.backend.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController implements LoginControllerDocumentation {

  private final LoginService loginService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public LoginResponse authenticate(@RequestBody LoginRequest loginRequest) {
    log.info("Try to authenticate {}", loginRequest.getUsername());
    String token =
        loginService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    return new LoginResponse(token);
  }
}
