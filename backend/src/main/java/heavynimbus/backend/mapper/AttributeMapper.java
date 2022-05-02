package heavynimbus.backend.mapper;

import heavynimbus.backend.db.attribute.Attribute;
import heavynimbus.backend.dto.attribute.CreateAttributeRequest;
import heavynimbus.backend.dto.attributeOption.AttributeOptionResponse;
import heavynimbus.backend.dto.attribute.AttributeResponse;
import java.util.UUID;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public record AttributeMapper(AttributeOptionMapper attributeOptionMapper) {

    public Attribute createAttributeRequestToAttribute(CreateAttributeRequest createAttributeRequest){
        return Attribute.builder()
            .name(createAttributeRequest.getName())
            .description(createAttributeRequest.getDescription())
            .options(List.of())
            .build();
    }

    public void updateAttribute(Attribute attribute, CreateAttributeRequest createAttributeRequest){
        attribute.setName(createAttributeRequest.getName());
        attribute.setDescription(createAttributeRequest.getDescription());
    }
    public AttributeResponse attributeToAttributeResponse(Attribute attribute) {
        List<AttributeOptionResponse> options = attribute.getOptions()
                .stream()
                .map(attributeOptionMapper::attributeOptionToAttributeOptionResponse)
                .toList();
        return AttributeResponse.builder()
                .id(UUID.fromString(attribute.getId()))
                .name(attribute.getName())
                .description(attribute.getDescription())
                .options(options).build();
    }
}
