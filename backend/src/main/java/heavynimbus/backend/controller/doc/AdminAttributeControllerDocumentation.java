package heavynimbus.backend.controller.doc;

import heavynimbus.backend.dto.attribute.AttributeResponse;
import heavynimbus.backend.dto.attribute.CreateAttributeRequest;
import heavynimbus.backend.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "jwt_auth")
@Tag(
    name = "[ADMIN] Attributes",
    description = "Actions related to attribute handling for administrator")
public interface AdminAttributeControllerDocumentation {

  @Operation(summary = "Find all attributes")
  List<AttributeResponse> findAll();

  @Operation(summary = "Find attribute by id")
  AttributeResponse findById(UUID attributeId) throws NotFoundException;

  @Operation(summary = "Create new attribute")
  AttributeResponse create(CreateAttributeRequest createAttributeRequest);

  @Operation(summary = "Update existing attribute")
  AttributeResponse update(UUID attributeId, CreateAttributeRequest createAttributeRequest)
      throws NotFoundException;

  @Operation(summary = "Delete attribute")
  void delete(UUID attributeId) throws NotFoundException;
}
