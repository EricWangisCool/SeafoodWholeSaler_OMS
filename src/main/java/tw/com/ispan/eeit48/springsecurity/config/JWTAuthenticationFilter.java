package tw.com.ispan.eeit48.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tw.com.ispan.eeit48.springsecurity.lib.JWTUtil;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
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
            }
            System.out.println("Set SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication());
        }
        chain.doFilter(request, response);
    }
}
