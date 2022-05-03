package heavynimbus.backend.controller.pub;

import heavynimbus.backend.controller.doc.AttributeControllerDocumentation;
import heavynimbus.backend.dto.attribute.AttributeResponse;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.service.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/attributes")
public class AttributeController implements AttributeControllerDocumentation {
  private final AttributeService attributeService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<AttributeResponse> getProductAttributes() {
    return attributeService.findAll();
  }

  @GetMapping("/{attributeId}")
  public AttributeResponse getAttributeById(@PathVariable UUID attributeId)
      throws NotFoundException {
    return attributeService.findById(attributeId);
  }
}
