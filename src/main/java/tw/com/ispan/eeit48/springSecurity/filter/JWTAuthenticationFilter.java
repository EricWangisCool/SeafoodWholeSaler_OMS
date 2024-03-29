package tw.com.ispan.eeit48.springSecurity.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LogManager.getLogger(JWTAuthenticationFilter.class);
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            HashMap<String, String> clientInfo = jwtUtil.getClientInfoFromToken(authHeader.replace("Bearer ", ""));
            String userId = clientInfo.get(JWTUtil.USERID_KEY);
            String authority = clientInfo.get(JWTUtil.AUTHORITY_KEY);

            if (authority != null && userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authority));

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                log.info("UserId set in SecurityContextHolder: {}", SecurityContextHolder.getContext().getAuthentication());
            }
        }
        chain.doFilter(request, response);
    }
}
