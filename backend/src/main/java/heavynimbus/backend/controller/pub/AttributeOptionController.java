package heavynimbus.backend.controller.pub;

import heavynimbus.backend.controller.doc.AttributeOptionControllerDocumentation;
import heavynimbus.backend.dto.product.AttributeOptionResponse;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.service.AttributeOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/attributes/{attributeId}/options")
public class AttributeOptionController implements AttributeOptionControllerDocumentation {

  private final AttributeOptionService attributeOptionService;

  @GetMapping
  public List<AttributeOptionResponse> getAllOptionsByAttributeId(@PathVariable UUID attributeId)
      throws NotFoundException {
    return attributeOptionService.findAllByAttributeId(attributeId);
  }

  @GetMapping("/{optionId}")
  public AttributeOptionResponse getOptionByIdAndAttributeId(@PathVariable UUID attributeId, @PathVariable UUID optionId)
          throws NotFoundException {
    return attributeOptionService.findByIdAndAttributeId(optionId, attributeId);
  }
}
