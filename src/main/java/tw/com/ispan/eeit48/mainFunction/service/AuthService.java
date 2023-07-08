package tw.com.ispan.eeit48.mainFunction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.mainFunction.model.table.Account;
import tw.com.ispan.eeit48.mainFunction.repository.table.AccountRepository;
import java.util.Optional;

/**
 * Authenticate a user from the database.
 */
@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // 查詢用戶是否存在，存在的話就將用戶資訊回傳給Spring Security驗證密碼
        System.out.println("Authenticating account: " + account);
        Optional<Account> user = accountRepository.findOneByAccount(account);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("Not found!");
    }

    @Transactional(readOnly = true)
    public Account findUserByAccount(String account) throws Exception {
        Optional<Account> result = accountRepository.findOneByAccount(account);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new Exception("Wrong account or password");
        }
    }

    public Account thirdPartyLogin(String email) throws Exception {
        if (email == null || email.length() == 0) {
            throw new Exception("Requested email is required");
        } else {
            Optional<Account> result = accountRepository.findOneByEmail(email);
            if (result.isEmpty()) {
                throw new Exception("Requested email does not exist");
            } else {
                return result.get();
            }
        }
    }

    public String getUserAccount() throws Exception {
        Optional<Account> account = accountRepository.findOneByAccountId(getCurrentUserId());
        if (account.isPresent()) {
            return account.get().getAccount();
        } else {
            throw new Exception("Unknown reason finding current user account");
        }
    }

    public static int getCurrentUserId() {
        return Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
