package heavynimbus.backend.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@Schema(
    name = "Login request payload",
    description = "The data you have to send when you want to be authenticated")
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

  @NotBlank(message = "username cannot be null or blank")
  @Schema(description = "Your username", example = "HeavyNimbus", required = true)
  private String username;

  @NotBlank(message = "password cannot be null or blank")
  @Schema(description = "Your password", example = "Maille_Sikret_Pazouaurd", required = true)
  private String password;
}
