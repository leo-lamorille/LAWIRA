package heavynimbus.backend.controller.admin;

import heavynimbus.backend.controller.doc.AdminAttributeOptionControllerDocumentation;
import heavynimbus.backend.dto.attributeOption.AttributeOptionResponse;
import heavynimbus.backend.dto.attributeOption.CreateAttributeOptionRequest;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.service.AttributeOptionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/attributes/{attributeId}/options")
public class AdminAttributeOptionController implements AdminAttributeOptionControllerDocumentation {

  private final AttributeOptionService attributeOptionService;

  @GetMapping
  public List<AttributeOptionResponse> findAll(@PathVariable UUID attributeId)
      throws NotFoundException {
    return attributeOptionService.findAllByAttributeId(attributeId);
  }

  @GetMapping("/{optionId}")
  public AttributeOptionResponse findById(
      @PathVariable UUID attributeId, @PathVariable UUID optionId) throws NotFoundException {
    return attributeOptionService.findByIdAndAttributeId(optionId, attributeId);
  }

  @PostMapping
  public AttributeOptionResponse create(
      @PathVariable UUID attributeId,
      @RequestBody CreateAttributeOptionRequest createAttributeOptionRequest)
      throws NotFoundException {
    return attributeOptionService.create(attributeId, createAttributeOptionRequest);
  }

  @PutMapping("/{optionId}")
  public AttributeOptionResponse update(
      @PathVariable UUID attributeId,
      @PathVariable UUID optionId,
      @RequestBody CreateAttributeOptionRequest createAttributeOptionRequest)
      throws NotFoundException {
    return attributeOptionService.update(attributeId, optionId, createAttributeOptionRequest);
  }

  @DeleteMapping("/{optionId}")
  public void delete(@PathVariable UUID attributeId, @PathVariable UUID optionId)
      throws NotFoundException {
    attributeOptionService.delete(attributeId, optionId);
  }
}
