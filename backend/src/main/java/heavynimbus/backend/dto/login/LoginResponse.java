package heavynimbus.backend.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(@Schema(description = "The token that authenticate you in all secured endpoints") String jwtToken) {
}
