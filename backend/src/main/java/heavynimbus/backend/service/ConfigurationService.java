package heavynimbus.backend.service;

import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.attribute.AttributeRepository;
import heavynimbus.backend.db.attributeOption.AttributeOption;
import heavynimbus.backend.db.command.Command;
import heavynimbus.backend.db.configuration.Configuration;
import heavynimbus.backend.db.configuration.ConfigurationRepository;
import heavynimbus.backend.dto.command.CommandResponse;
import heavynimbus.backend.dto.command.CreateCommandRequest;
import heavynimbus.backend.dto.configuration.ConfigurationResponse;
import heavynimbus.backend.dto.configuration.CreateConfigurationRequest;
import heavynimbus.backend.exception.BadRequestException;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.mapper.ConfigurationMapper;
import java.io.ObjectInputFilter.Config;
import java.util.ArrayList;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public record ConfigurationService(ConfigurationRepository configurationRepository,
                                   AttributeOptionService attributeOptionService,
                                   AttributeRepository attributeRepository,
                                   ConfigurationMapper configurationMapper,
                                   AccountService accountService) {

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

    public ConfigurationResponse findByIdAndUsername(UUID configurationId, String username)
        throws NotFoundException {
        return configurationMapper.configurationToConfigurationResponse(
            findConfigurationByIdAndUsername(configurationId, username));
    }



    public ConfigurationResponse createConfiguration(
        CreateConfigurationRequest createConfigurationRequest,
        Authentication authentication) throws BadRequestException, NotFoundException {
        Account account = accountService.findByUsername(authentication.getName());
        List<AttributeOption> options = attributeOptionService.checkAndGetOptions(createConfigurationRequest.getOptions());
        long count = attributeRepository.count();
        if(options.size() != count) throw new BadRequestException(String.format("Should have %s options but have %s", count, options.size()));
        Configuration configuration= configurationMapper.createConfigurationRequestToConfiguration(createConfigurationRequest,account, options);
        configuration = configurationRepository.save(configuration);
        return configurationMapper.configurationToConfigurationResponse(configuration);
    }

    public ConfigurationResponse updateConfiguration(UUID configurationId,
        CreateConfigurationRequest createConfigurationRequest,
        Authentication authentication)
        throws NotFoundException, BadRequestException {
        Configuration configuration = findConfigurationByIdAndUsername(configurationId, authentication.getName());
        List<AttributeOption> optionList = attributeOptionService.checkAndGetOptions(createConfigurationRequest.getOptions());
        long count = attributeRepository.count();
        if(optionList.size() != count) throw new BadRequestException(String.format("Should have %s options but have %s", count, optionList.size()));
        configurationMapper.updateConfiguration(configuration, createConfigurationRequest.getName(), optionList);
        configuration = configurationRepository.save(configuration);
        return configurationMapper.configurationToConfigurationResponse(configuration);
    }

    public void deleteByIdAndUsername(UUID configurationId, String username) throws NotFoundException {
        Configuration configuration = findConfigurationByIdAndUsername(configurationId, username);
        configurationRepository.delete(configuration);
    }
}
