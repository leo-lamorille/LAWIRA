package heavynimbus.backend.controller.doc;

import heavynimbus.backend.dto.product.AttributeOptionResponse;
import heavynimbus.backend.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Tag(name = "[PUBLIC] Attribute options", description = "Actions related to product attribute options for public")
public interface AttributeOptionControllerDocumentation {
    @Operation(summary = "Get all options by attribute", description = """
          Returns a list containing all options for an attribute
          """)
    List<AttributeOptionResponse> getAllOptionsByAttributeId(UUID attributeId)
            throws NotFoundException;

    @Operation(summary = "Get option by id and attribute", description = """
          Return an option considering the id and the attributeId
          """)
    AttributeOptionResponse getOptionByIdAndAttributeId(UUID attributeId, UUID optionId)
            throws NotFoundException;
}
