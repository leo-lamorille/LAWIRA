package heavynimbus.backend.controller.doc;

import heavynimbus.backend.dto.attribute.AttributeResponse;
import heavynimbus.backend.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Tag(name = "[PUBLIC] Attributes", description = "Actions related to product attributes for public")
public interface AttributeControllerDocumentation {

    @Operation(summary = "Get all product attributes", description = """
          Returns a list containing all product attributes with their options
          """)
    List<AttributeResponse> getProductAttributes();

    @Operation(summary = "Get product attribute by id", description = """
          Returns the requested attribute
          """)
    AttributeResponse getAttributeById(UUID attributeId)
            throws NotFoundException;
}
