package heavynimbus.backend.service;

import heavynimbus.backend.db.account.Account;
import heavynimbus.backend.db.account.AccountRepository;
import heavynimbus.backend.dto.login.LoginRequest;
import heavynimbus.backend.exception.AlreadyExistsException;
import heavynimbus.backend.mapper.AccountMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public record AccountService(AccountRepository accountRepository,
                             AccountMapper accountMapper,
                             LoginService loginService) {
    public String createAccount(LoginRequest loginRequest) throws AlreadyExistsException {
        Account account = accountMapper.loginRequestToAccount(loginRequest);
        try {
            accountRepository.save(account);
            log.info("New account: {}", account.getPassword());
        }catch(DataIntegrityViolationException e){
            throw new AlreadyExistsException(String.format("Account with username %s already exists", loginRequest.getUsername()), e);
        }
        return loginService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    }

    public Account findByUsername(String username){
        return accountRepository.findByUsername(username).orElseThrow();
    }
}
