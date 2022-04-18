package heavynimbus.backend.service;

import heavynimbus.backend.db.attribute.Attribute;
import heavynimbus.backend.db.attribute.AttributeRepository;
import heavynimbus.backend.dto.product.AttributeResponse;
import heavynimbus.backend.dto.product.ProductResponse;
import heavynimbus.backend.mapper.AttributeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record ProductService(AttributeRepository attributeRepository, AttributeMapper attributeMapper) {
    public ProductResponse getProduct(){
        List<Attribute> attributes = attributeRepository.findAll();
        List<AttributeResponse> attributeResponses = attributes.stream().map(attributeMapper::attributeToAttributeResponse).toList();
        return new ProductResponse(attributeResponses);
    }
}
