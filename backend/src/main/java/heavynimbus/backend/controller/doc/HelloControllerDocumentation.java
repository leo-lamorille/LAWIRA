package heavynimbus.backend.controller.doc;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "jwt_auth")
@Tag(name = "Hello World !", description = "Hello world controller with auth")
public interface HelloControllerDocumentation {}
