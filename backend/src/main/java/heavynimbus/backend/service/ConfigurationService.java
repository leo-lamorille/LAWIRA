package heavynimbus.backend.service;

import heavynimbus.backend.db.configuration.Configuration;
import heavynimbus.backend.db.configuration.ConfigurationRepository;
import heavynimbus.backend.dto.configuration.ConfigurationResponse;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.mapper.ConfigurationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public record ConfigurationService(ConfigurationRepository configurationRepository,
                                   ConfigurationMapper configurationMapper) {

    public Configuration findConfigurationByIdAndUsername(UUID configurationId, String username) throws NotFoundException {
        return configurationRepository.findByIdAndAccount_Username(configurationId.toString(), username)
                .orElseThrow(()->new NotFoundException("configuration", "id", configurationId.toString()));
    }

    public List<ConfigurationResponse> findAllByUsername(String username) {
        return configurationRepository.findAllByAccount_Username(username)
                .stream()
                .map(configurationMapper::configurationToConfigurationResponse)
                .toList();
    }

    public void deleteByIdAndUsername(UUID configurationId, String username) throws NotFoundException {
        Configuration configuration = findConfigurationByIdAndUsername(configurationId, username);
        configurationRepository.delete(configuration);
    }
}
