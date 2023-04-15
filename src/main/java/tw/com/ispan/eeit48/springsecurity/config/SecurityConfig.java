package tw.com.ispan.eeit48.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tw.com.ispan.eeit48.mainfunction.service.AuthService;
import tw.com.ispan.eeit48.springsecurity.filter.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthService authService;
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    // 建立密碼演算的實例
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception { return super.authenticationManagerBean(); }

    @Autowired
    public SecurityConfig(AuthService authService, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.authService = authService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // 將 authService 跟 passwordEncoder 設定 Spring Security 進去
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { auth.userDetailsService(authService).passwordEncoder(passwordEncoder()); }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 因為有 JWT，所以可以防範 csrf 攻擊，因此將其 disable
                .csrf().disable()
                // 授權認證
                .authorizeHttpRequests()
                // 不需要被認證的API
                .antMatchers("/login").permitAll()
                .antMatchers("/ecpay").permitAll()
                // 必須要有特殊權限才可以訪問的API
                .antMatchers("/views/analyze").hasRole("BOSS")
                // 其他頁面必須要有驗證才能訪問
                .anyRequest().authenticated()
                // 因為是 JWT 機制，所以不應該產生 Session，因此將 Session 建立取消
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                /**
                 * 因為每個受保護的 API 請求都需要去檢查其 Header 上面的 token 是否合法，因此添加了一個自定義的 jwt filter 在 UsernamePasswordAuthenticationFilter 前面，
                 * 這樣可以避免用原生 Spring Security 的方式幫我們做檢查，而是採用我們自定義的方式
                 */
                .and().addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // Configure paths and requests that should be ignored by Spring Security ================================
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                // allow anonymous resource requests
                .antMatchers("/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png")
                // allow websocket
                .antMatchers("/ws/**");
    }
    

}
