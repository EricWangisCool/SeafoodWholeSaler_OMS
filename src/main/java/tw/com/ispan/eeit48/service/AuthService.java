package tw.com.ispan.eeit48.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.com.ispan.eeit48.model.AccountsBean;
import tw.com.ispan.eeit48.repository.AccountsRepository;
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
        Optional<AccountsBean> user = accountsRepository.findOneByaccount(account);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("Not found!");
    }

    @Transactional(readOnly = true)
    public AccountsBean findUserByaccount(String account) {
        Optional<AccountsBean> result = accountsRepository.findOneByaccount(account);
        if (result.isPresent()) {
            return result.get();
        } else {
            System.out.println("select bean not exist");
        }
        return null;
    }

    @Transactional(readOnly = true)
    public AccountsBean findUserByEmail(String email) {
        Optional<AccountsBean> result = accountsRepository.findOneByEmail(email);
        if (result.isPresent()) {
            return result.get();
        } else {
            System.out.println("select bean not exist");
        }
        return null;
    }

    public int getCurrentUserId() {
        return Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
