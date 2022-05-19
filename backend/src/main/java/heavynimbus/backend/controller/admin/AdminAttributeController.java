package heavynimbus.backend.controller.admin;

import heavynimbus.backend.controller.doc.AdminAttributeControllerDocumentation;
import heavynimbus.backend.dto.attribute.CreateAttributeRequest;
import heavynimbus.backend.dto.attribute.AttributeResponse;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.service.AttributeService;
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
@RequestMapping("/admin/attributes")
public class AdminAttributeController implements AdminAttributeControllerDocumentation {

  private final AttributeService attributeService;

  @GetMapping
  public List<AttributeResponse> findAll() {
    return attributeService.findAll();
  }

  @GetMapping("/{attributeId}")
  public AttributeResponse findById(@PathVariable UUID attributeId) throws NotFoundException {
    return attributeService.findById(attributeId);
  }

  @PostMapping
  public AttributeResponse create(@RequestBody CreateAttributeRequest createAttributeRequest) {
    return attributeService.createAttribute(createAttributeRequest);
  }

  @PutMapping("/{attributeId}")
  public AttributeResponse update(
      @PathVariable UUID attributeId, @RequestBody CreateAttributeRequest createAttributeRequest)
      throws NotFoundException {
    return attributeService.updateAttribute(attributeId, createAttributeRequest);
  }

  @DeleteMapping("/{attributeId}")
  public void delete(@PathVariable UUID attributeId) throws NotFoundException {
    attributeService.deleteById(attributeId);
  }
}
