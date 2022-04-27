package heavynimbus.backend.service;

import heavynimbus.backend.db.attribute.Attribute;
import heavynimbus.backend.db.attributeOption.AttributeOption;
import heavynimbus.backend.db.attributeOption.AttributeOptionRepository;
import heavynimbus.backend.dto.configuration.CreateConfigurationRequest;
import heavynimbus.backend.dto.product.AttributeOptionResponse;
import heavynimbus.backend.exception.BadRequestException;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.mapper.AttributeOptionMapper;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public record AttributeOptionService(AttributeOptionRepository attributeOptionRepository,
                                     AttributeOptionMapper attributeOptionMapper,
                                     AttributeService attributeService) {
    public AttributeOption findAttributeOptionById(UUID id) throws NotFoundException {
        return attributeOptionRepository.findById(id.toString())
                .orElseThrow(()->new NotFoundException("attribute option", "id", id.toString()));
    }

    public AttributeOptionResponse findByIdAndAttributeId(UUID id, UUID attributeId) throws NotFoundException {
        Attribute attribute = attributeService.findAttributeById(attributeId);
        AttributeOption attributeOption = attribute.getOptions()
                .stream()
                .filter(option->id.toString().equals(option.getId()))
                .findAny()
                .orElseThrow(()->new NotFoundException("Attribute option", "id", id.toString()));
        return attributeOptionMapper.attributeOptionToAttributeOptionResponse(attributeOption);
    }

    public List<AttributeOptionResponse> findAllByAttributeId(UUID attributeId) throws NotFoundException {
        Attribute attribute = attributeService.findAttributeById(attributeId);
        return attributeOptionRepository.findAllByAttribute(attribute)
                .stream()
                .map(attributeOptionMapper::attributeOptionToAttributeOptionResponse)
                .toList();
    }

    public List<AttributeOption> checkAndGetOptions(
        Iterable<UUID> ids) throws BadRequestException, NotFoundException {
        List<AttributeOption> optionList = new ArrayList<>();
        for (UUID optionId: ids) {
            AttributeOption option = findAttributeOptionById(optionId);
            List<String> alreadyPresentAttributes = optionList.stream().map(AttributeOption::getAttributeName).toList();
            if (alreadyPresentAttributes.contains(option.getAttributeName()))
                throw new BadRequestException("There are two options specifying " + option.getAttributeName());
            optionList.add(option);
        }
        return optionList;
    }

}
