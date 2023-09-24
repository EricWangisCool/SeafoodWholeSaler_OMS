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
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
             throws Exception {
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
                .authorizeHttpRequests(authorize -> {
     			   authorize
     			// 不需要被認證的API
                   .requestMatchers("/login").permitAll()
                   .requestMatchers("/ecPay").permitAll()
                   // 必須要有特殊權限才可以訪問的API
                   .requestMatchers("/views/analyze").hasRole("BOSS")
                   // 其他頁面必須要有驗證才能訪問
                   .anyRequest().authenticated();
                   // 因為是 JWT 機制，所以不應該產生 Session，因此將 Session 建立取消
                });
                // 不需要被認證的API
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(daoAuthenticationProvider());
        return http.build();
    }

    // Configure paths and requests that should be ignored by Spring Security ================================
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
    	return (web)->web.ignoring()
              .requestMatchers(HttpMethod.OPTIONS, "/**")
//              // allow anonymous resource requests
              .requestMatchers("/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png")
//              // allow websocket
              .requestMatchers("/ws/**");
    }
}
