package heavynimbus.backend.mapper;

import heavynimbus.backend.db.attribute.Attribute;
import heavynimbus.backend.dto.product.AttributeOptionResponse;
import heavynimbus.backend.dto.product.AttributeResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public record AttributeMapper(AttributeOptionMapper attributeOptionMapper) {
    public AttributeResponse attributeToAttributeResponse(Attribute attribute) {
        List<AttributeOptionResponse> options = attribute.getOptions()
                .stream()
                .map(attributeOptionMapper::attributeOptionToAttributeOptionResponse)
                .toList();
        return AttributeResponse.builder()
                .id(attribute.getId())
                .name(attribute.getName())
                .description(attribute.getDescription())
                .options(options).build();
    }
}
