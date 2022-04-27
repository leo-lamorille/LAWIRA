package heavynimbus.backend.mapper;

import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.attributeOption.AttributeOption;
import heavynimbus.backend.db.configuration.Configuration;
import heavynimbus.backend.dto.configuration.ConfigurationResponse;
import heavynimbus.backend.dto.configuration.CreateConfigurationRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;


@Component
public record ConfigurationMapper(AttributeOptionMapper attributeOptionMapper) {

    public ConfigurationResponse configurationToConfigurationResponse(Configuration configuration) {
        return ConfigurationResponse.builder()
                .id(UUID.fromString(configuration.getId()))
                .name(configuration.getName())
                .options(configuration
                        .getOptions()
                        .stream()
                        .map(attributeOptionMapper::attributeOptionToAttributeOptionDetailResponse)
                        .toList())
                .build();
    }

    public Configuration createConfigurationRequestToConfiguration(
        CreateConfigurationRequest createConfigurationRequest, Account account,
        List<AttributeOption> attributeOptions){
        return Configuration.builder()
            .name(createConfigurationRequest.getName())
            .account(account)
            .options(attributeOptions).build();
    }

    public void updateConfiguration(Configuration configuration, String name,
        List<AttributeOption> attributeOptions){
        configuration.setName(name);
        configuration.setOptions(attributeOptions);
    }
}
