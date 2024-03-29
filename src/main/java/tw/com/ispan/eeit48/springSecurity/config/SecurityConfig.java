package tw.com.ispan.eeit48.springSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import tw.com.ispan.eeit48.mainFunction.service.AuthService;
import tw.com.ispan.eeit48.springSecurity.filter.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    private final AuthService authService;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    // 建立密碼演算的實例
    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    public SecurityConfig(AuthService authService, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.authService = authService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 將 authService 跟 passwordEncoder 設定 Spring Security 進去
    @Bean
	AuthenticationProvider daoAuthenticationProvider() {
	      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	      // 添加加解密物件 不加鹽
	      provider.setPasswordEncoder(passwordEncoder());
	      provider.setUserDetailsService(authService);
	      return provider;
	}

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,AuthenticationManagerBuilder auth) throws Exception {
        http
                // 因為有 JWT，所以可以防範 csrf 攻擊，因此將其 disable
                .csrf(csrf -> csrf.disable())
                // 授權認證
                .authorizeHttpRequests(authorize ->
     			   authorize
     			   // 不需要被認證的API
                   .requestMatchers("/login").permitAll()
                   .requestMatchers("/ecPay").permitAll()
                   // 必須要有特殊權限才可以訪問的API
                   .requestMatchers("/views/analyze").hasRole("BOSS")
                   // Spring 5.3後 基於效能理由 路徑解析從 AntPathMatcher 改為 PathPatternParser (若要使用 AntPathMatcher 需特別指定 AntPathRequestMatcher)
                   .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.OPTIONS,"/**")).permitAll()
                   .requestMatchers(AntPathRequestMatcher.antMatcher("/**/*.html")).permitAll()
                   .requestMatchers(AntPathRequestMatcher.antMatcher("/**/*.css")).permitAll()
                   .requestMatchers(AntPathRequestMatcher.antMatcher("/**/*.js")).permitAll()
                   .requestMatchers(AntPathRequestMatcher.antMatcher("/**/*.jpg")).permitAll()
                   .requestMatchers(AntPathRequestMatcher.antMatcher("/**/*.png")).permitAll()
                   .requestMatchers(AntPathRequestMatcher.antMatcher("/**/**/*.png")).permitAll()
                   .requestMatchers(AntPathRequestMatcher.antMatcher("/*.html")).permitAll()
                   .requestMatchers(AntPathRequestMatcher.antMatcher("/")).permitAll()
                   .requestMatchers(AntPathRequestMatcher.antMatcher("/ws/**")).permitAll()
                   // 無設立 /favicon.ico 404 會導向/error 因此 /error 也要 permitAll
                   .requestMatchers(AntPathRequestMatcher.antMatcher("/favicon.ico")).permitAll()
                   .requestMatchers("/error").permitAll()
                   // 其他頁面必須要有驗證才能訪問
                   .anyRequest().authenticated()
                );

        http.sessionManagement(management ->
                        // 因為是 JWT 機制，所以不應該產生 Session，因此將 Session 建立取消
                        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /**
                 * 因為每個受保護的 API 請求都需要去檢查其 Header 上面的 token 是否合法，因此添加了一個自定義的 jwt filter 在 UsernamePasswordAuthenticationFilter 前面，
                 * 這樣可以避免用原生 Spring Security 的方式幫我們做檢查，而是採用我們自定義的方式
                 */
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(daoAuthenticationProvider());

        // Configuring Spring Security to disable the X-Frame-Options header, which allows web pages to be displayed in iframes without restrictions.
        http.headers(headers->headers.frameOptions(frameOptions->frameOptions.disable()));

        return http.build();
    }
}
