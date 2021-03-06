package heavynimbus.backend.controller.doc;

import heavynimbus.backend.dto.login.LoginRequest;
import heavynimbus.backend.dto.login.LoginResponse;
import heavynimbus.backend.exception.AlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;

@Tag(name = "[PUBLIC] Authentication", description = "How to be authenticated")
public interface LoginControllerDocumentation {
  @Operation(summary = "Authenticate", description = """
          This request authenticates you by returning a jwt token
          
          You will need to use it on all secure endpoints as a bearer token in the headers like:
          `Authorization: Bearer <token>`
          """)
  LoginResponse authenticate(
      @RequestBody(
              required = true,
              description = "Login request containing username & password",
              content =
                  @Content(
                      schema = @Schema(implementation = LoginRequest.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE))
          LoginRequest loginRequest);

    @Operation(summary = "Create a user account", description = """
          This request create an account and authenticates you by returning a jwt token
          
          You will need to use it on all secure endpoints as a bearer token in the headers like:
          `Authorization: Bearer <token>`
          """)
    LoginResponse createAccount(@RequestBody(
            required = true,
            description = "Create account request containing username & password",
            content =
            @Content(
                    schema = @Schema(implementation = LoginRequest.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)) LoginRequest loginRequest)
            throws AlreadyExistsException;
}
