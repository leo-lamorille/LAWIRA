package heavynimbus.backend.mapper;

import heavynimbus.backend.db.configuration.Configuration;
import heavynimbus.backend.dto.configuration.ConfigurationResponse;
import org.springframework.stereotype.Component;


@Component
public record ConfigurationMapper(AttributeOptionMapper attributeOptionMapper) {

    public ConfigurationResponse configurationToConfigurationResponse(Configuration configuration) {
        return ConfigurationResponse.builder()
                .id(configuration.getId())
                .name(configuration.getName())
                .options(configuration
                        .getOptions()
                        .stream()
                        .map(attributeOptionMapper::attributeOptionToAttributeOptionDetailResponse)
                        .toList())
                .build();
    }
}