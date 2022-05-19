package heavynimbus.backend.controller.doc;

import heavynimbus.backend.dto.attributeOption.AttributeOptionResponse;
import heavynimbus.backend.dto.attributeOption.CreateAttributeOptionRequest;
import heavynimbus.backend.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@SecurityRequirement(name = "jwt_auth")
@Tag(
    name = "[ADMIN] Attribute options",
    description = "Actions related to attribute option handling for administrator")
public interface AdminAttributeOptionControllerDocumentation {

  @Operation(summary = "Find all attribute options")
  List<AttributeOptionResponse> findAll(UUID attributeId) throws NotFoundException;

  @Operation(summary = "Find attribute option by id")
  AttributeOptionResponse findById(UUID attributeId, UUID optionId) throws NotFoundException;

  @Operation(summary = "Create new attribute option")
  AttributeOptionResponse create(
      UUID attributeId, CreateAttributeOptionRequest createAttributeOptionRequest)
      throws NotFoundException;

  @Operation(summary = "Update existing attribute option")
  AttributeOptionResponse update(
      UUID attributeId, UUID optionId, CreateAttributeOptionRequest createAttributeOptionRequest)
      throws NotFoundException;

  @Operation(summary = "Delete attribute option")
  void delete(UUID attributeId, UUID optionId) throws NotFoundException;
}
