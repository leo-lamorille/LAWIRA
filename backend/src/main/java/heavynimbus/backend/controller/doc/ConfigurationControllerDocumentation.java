package heavynimbus.backend.controller.doc;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "jwt_auth")
@Tag(name = "[USER] Configurations", description = "Actions related to user's configurations")
public interface ConfigurationControllerDocumentation {
}
