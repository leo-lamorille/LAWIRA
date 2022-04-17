package heavynimbus.backend.controller;

import heavynimbus.backend.controller.doc.SignInControllerDocumentation;
import heavynimbus.backend.dto.login.LoginRequest;
import heavynimbus.backend.dto.login.LoginResponse;
import heavynimbus.backend.exception.AlreadyExistsException;
import heavynimbus.backend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signUp")
public class SignInController implements SignInControllerDocumentation {

  private final AccountService accountService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public LoginResponse createAccount(@RequestBody LoginRequest loginRequest)
      throws AlreadyExistsException {
    String jwtToken = accountService.createAccount(loginRequest);
    return new LoginResponse(jwtToken);
  }
}
