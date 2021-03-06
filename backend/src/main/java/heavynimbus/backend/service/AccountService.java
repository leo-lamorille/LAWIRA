package heavynimbus.backend.service;

import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.account.AccountRepository;
import heavynimbus.backend.db.command.CommandRepository;
import heavynimbus.backend.db.configuration.ConfigurationRepository;
import heavynimbus.backend.db.contactMessage.ContactMessageRepository;
import heavynimbus.backend.dto.account.AccountResponse;
import heavynimbus.backend.dto.login.LoginRequest;
import heavynimbus.backend.exception.AlreadyExistsException;
import heavynimbus.backend.exception.NotFoundException;
import heavynimbus.backend.mapper.AccountMapper;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AccountService {
  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;
  private final CommandRepository commandRepository;
  private final ConfigurationRepository configurationRepository;
  private final ContactMessageRepository contactMessageRepository;
  private final LoginService loginService;

  public String createAccount(LoginRequest loginRequest) throws AlreadyExistsException {
    Account account = accountMapper.loginRequestToAccount(loginRequest);
    try {
      accountRepository.save(account);
      log.info("New account: {}", account.getPassword());
    } catch (DataIntegrityViolationException e) {
      throw new AlreadyExistsException(
          String.format("Account with username %s already exists", loginRequest.getUsername()), e);
    }
    return loginService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
  }

  public Account findByUsername(String username) {
    return accountRepository.findByUsername(username).orElseThrow();
  }

  public Account findAccountById(UUID id) throws NotFoundException {
    return accountRepository
        .findById(id.toString())
        .orElseThrow(() -> new NotFoundException("account", "id", id.toString()));
  }

  public List<AccountResponse> findAllAccounts() {
    return accountRepository.findAll().stream()
        .map(accountMapper::accountToAccountResponse)
        .collect(Collectors.toList());
  }

  public AccountResponse enable(UUID accountId) throws NotFoundException {
    Account account = findAccountById(accountId);
    account.setEnabled(true);
    account = accountRepository.save(account);
    return accountMapper.accountToAccountResponse(account);
  }

  public AccountResponse disable(UUID accountId) throws NotFoundException {
    Account account = findAccountById(accountId);
    account.setEnabled(false);
    account = accountRepository.save(account);
    return accountMapper.accountToAccountResponse(account);
  }

  public AccountResponse createAdministrator(LoginRequest loginRequest)
      throws AlreadyExistsException {
    Account account = accountMapper.loginRequestToAdminAccount(loginRequest);
    try {
      accountRepository.save(account);
      log.info("New account: {}", account.getPassword());
    } catch (DataIntegrityViolationException e) {
      throw new AlreadyExistsException(
          String.format("Account with username %s already exists", loginRequest.getUsername()), e);
    }
    return accountMapper.accountToAccountResponse(account);
  }

  @Transactional
  public void deleteAccount(UUID accountId) throws NotFoundException {
    Account account = findAccountById(accountId);
    commandRepository.deleteAllByAccount(account);
    configurationRepository.deleteAllByAccount(account);
    contactMessageRepository.deleteAllByAccount(account);
    accountRepository.delete(account);
  }
}
