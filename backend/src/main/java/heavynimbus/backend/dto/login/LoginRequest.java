package heavynimbus.backend.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
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

  @Schema(description = "Your username", example = "HeavyNimbus", required = true)
  private String username;

  @Schema(description = "Your password", example = "Maille_Sikret_Pazouaurd", required = true)
  private String password;
}
