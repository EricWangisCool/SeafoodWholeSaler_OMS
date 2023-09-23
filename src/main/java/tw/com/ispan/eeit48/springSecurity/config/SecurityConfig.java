package tw.com.ispan.eeit48.springSecurity.config;

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
import tw.com.ispan.eeit48.mainFunction.service.AuthService;
import tw.com.ispan.eeit48.springSecurity.filter.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthService authService;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    // 建立密碼演算的實例
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    /*~~(Migrate manually based on https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)~~>*/@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception { return super.authenticationManagerBean(); }

    public SecurityConfig(AuthService authService, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.authService = authService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // 將 authService 跟 passwordEncoder 設定 Spring Security 進去
    /*~~(Migrate manually based on https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)~~>*/@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { auth.userDetailsService(authService).passwordEncoder(passwordEncoder()); }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 因為有 JWT，所以可以防範 csrf 攻擊，因此將其 disable
                .csrf(csrf -> csrf.disable())
                // 授權認證
                .authorizeHttpRequests()
                // 不需要被認證的API
                .requestMatchers("/login").permitAll()
                .requestMatchers("/ecPay").permitAll()
                // 必須要有特殊權限才可以訪問的API
                .requestMatchers("/views/analyze").hasRole("BOSS")
                // 其他頁面必須要有驗證才能訪問
                .anyRequest().authenticated()
                // 因為是 JWT 機制，所以不應該產生 Session，因此將 Session 建立取消
                .and().sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // Configure paths and requests that should be ignored by Spring Security ================================
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .requestMatchers(HttpMethod.OPTIONS, "/**")
                // allow anonymous resource requests
                .requestMatchers("/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png")
                // allow websocket
                .requestMatchers("/ws/**");
    }
}
