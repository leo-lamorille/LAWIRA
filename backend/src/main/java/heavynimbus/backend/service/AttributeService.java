package heavynimbus.backend.service;

import heavynimbus.backend.db.attribute.Attribute;
import heavynimbus.backend.db.attribute.AttributeRepository;
import heavynimbus.backend.db.command.CommandRepository;
import heavynimbus.backend.db.configuration.ConfigurationRepository;
import heavynimbus.backend.dto.attribute.CreateAttributeRequest;
import heavynimbus.backend.dto.attribute.AttributeResponse;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.mapper.AttributeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public record AttributeService(AttributeRepository attributeRepository,
                               AttributeMapper attributeMapper,
                               ConfigurationRepository configurationRepository,
                               CommandRepository commandRepository) {
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

    public AttributeResponse createAttribute(CreateAttributeRequest createAttributeRequest){
        Attribute attribute = attributeMapper.createAttributeRequestToAttribute(createAttributeRequest);
        attribute = attributeRepository.save(attribute);
        return attributeMapper.attributeToAttributeResponse(attribute);
    }

    public AttributeResponse updateAttribute(UUID attributeId, CreateAttributeRequest createAttributeRequest)
        throws NotFoundException {
        Attribute attribute = findAttributeById(attributeId);
        attributeMapper.updateAttribute(attribute, createAttributeRequest);
        attribute = attributeRepository.save(attribute);
        return attributeMapper.attributeToAttributeResponse(attribute);
    }


    public void deleteById(UUID id) throws NotFoundException {
        Attribute attribute = findAttributeById(id);
        configurationRepository.findAll()
            .stream().peek(configuration ->
                configuration
                    .getOptions()
                    .removeIf(option -> option.getAttribute().getId().equals(attribute.getId())))
            .forEach(configurationRepository::save);

        commandRepository.findAll()
            .stream().peek(command->
                command
                    .getOptions()
                    .removeIf(option -> option.getAttribute().getId().equals(attribute.getId()))
            ).forEach(commandRepository::save);
        attributeRepository.delete(attribute);
    }
}
