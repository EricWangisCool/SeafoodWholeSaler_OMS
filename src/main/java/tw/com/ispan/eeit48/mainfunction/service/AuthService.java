package tw.com.ispan.eeit48.mainfunction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.mainfunction.model.table.Account;
import tw.com.ispan.eeit48.mainfunction.repository.AccountsRepository;
import java.util.Optional;

/**
 * Authenticate a user from the database.
 */
@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // 查詢用戶是否存在，存在的話就將用戶資訊回傳給Spring Security驗證密碼
        System.out.println("Authenticating account: " + account);
        Optional<Account> user = accountsRepository.findOneByaccount(account);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("Not found!");
    }

    @Transactional(readOnly = true)
    public Account findUserByAccount(String account) {
        Optional<Account> result = accountsRepository.findOneByaccount(account);
        if (result.isPresent()) {
            return result.get();
        } else {
            System.out.println("select bean not exist");
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Account findUserByEmail(String email) {
        Optional<Account> result = accountsRepository.findOneByEmail(email);
        if (result.isPresent()) {
            return result.get();
        } else {
            System.out.println("select bean not exist");
        }
        return null;
    }

    public static int getCurrentUserId() {
        return Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
