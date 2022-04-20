package heavynimbus.backend.controller.pub;

import heavynimbus.backend.controller.doc.LoginControllerDocumentation;
import heavynimbus.backend.dto.login.LoginRequest;
import heavynimbus.backend.dto.login.LoginResponse;
import heavynimbus.backend.exception.AlreadyExistsException;
import heavynimbus.backend.service.AccountService;
import heavynimbus.backend.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class LoginController implements LoginControllerDocumentation {

  private final LoginService loginService;
  private final AccountService accountService;

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public LoginResponse authenticate(@RequestBody LoginRequest loginRequest) {
    log.info("Try to authenticate {}", loginRequest.getUsername());
    String token =
        loginService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    return new LoginResponse(token);
  }

  @PostMapping("/sign-up")
  @ResponseStatus(HttpStatus.CREATED)
  public LoginResponse createAccount(@RequestBody LoginRequest loginRequest)
      throws AlreadyExistsException {
    String jwtToken = accountService.createAccount(loginRequest);
    return new LoginResponse(jwtToken);
  }
}
