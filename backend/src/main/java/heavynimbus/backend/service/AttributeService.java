package heavynimbus.backend.service;

import heavynimbus.backend.db.attribute.Attribute;
import heavynimbus.backend.db.attribute.AttributeRepository;
import heavynimbus.backend.dto.product.AttributeResponse;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.mapper.AttributeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public record AttributeService(AttributeRepository attributeRepository, AttributeMapper attributeMapper) {
    public Attribute findAttributeById(UUID id) throws NotFoundException {
        return attributeRepository.findById(id.toString())
                .orElseThrow(()->new NotFoundException("attribute", "id", id.toString()));
    }

    public List<AttributeResponse> findAll(){
        List<Attribute> attributes = attributeRepository.findAll();
        return attributes.stream().map(attributeMapper::attributeToAttributeResponse).toList();
    }

    public AttributeResponse findById(UUID id) throws NotFoundException {
        Attribute attribute = attributeRepository.findById(id.toString())
                .orElseThrow(()->new NotFoundException("attribute", "id", id.toString()));
        return attributeMapper.attributeToAttributeResponse(attribute);
    }
}
