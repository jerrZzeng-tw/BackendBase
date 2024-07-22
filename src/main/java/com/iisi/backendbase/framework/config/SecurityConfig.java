package com.iisi.backendbase.framework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iisi.backendbase.framework.ResponseData;
import com.iisi.backendbase.framework.StatusCode;
import com.iisi.backendbase.framework.security.JwtAuthenticationFilter;
import com.iisi.backendbase.framework.services.AuthService;
import com.iisi.backendbase.repo.UserRepository;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
    @Value("${server.servlet.context-path}")
    private String CONTEXT_BASE;
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Resource
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    SecurityFilterChain filterChain(final HttpSecurity http, final UserDetailsService userDetailsService,
                                    final AccessDeniedHandler accessDeniedHandler, final AuthenticationEntryPoint authenticationEntryPoint,
                                    final AuthorizationManager<RequestAuthorizationContext> authorizationManager) throws Exception {
        http.csrf(CsrfConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/login", "/test/**", "/h2-console/**", "*.html", "*.js")
                        .permitAll()
                        .anyRequest()
                        .access(authorizationManager))
                // for /h2-console
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .exceptionHandling(
                        exception -> exception.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * 權限管理 檢查USER使否有使用此URL的權限
     *
     * @return
     */
    @Bean
    public AuthorizationManager<RequestAuthorizationContext> authorizationManager() {
        return new AuthorizationManager<RequestAuthorizationContext>() {
            @Override
            public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
                // 查詢URL所需權限
                String requestURI = StringUtils.substringAfter(context.getRequest().getRequestURI(), CONTEXT_BASE);
                List<String> roles = authService.findRolesByItemUrl(StringUtils.removeEnd(requestURI, "/"));

                if (!authentication.get().getPrincipal().equals("anonymousUser")) {
                    // 檢查目前使用者是否有權限存取
                    for (String role : roles) {
                        if (authentication.get().getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(role))) {
                            return new AuthorizationDecision(true);
                        }
                    }
                }
                return new AuthorizationDecision(false);
            }
        };
    }

    /**
     * 未認證或認證錯誤處理
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {
                response.setStatus(HttpStatus.OK.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().print(objectMapper.writeValueAsString(new ResponseData(StatusCode.AUTH_ERROR)));
            }
        };
    }

    /**
     * 權限不足處理
     *
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response,
                               AccessDeniedException accessDeniedException) throws IOException, ServletException {
                response.setStatus(HttpStatus.OK.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().print(objectMapper.writeValueAsString(new ResponseData(StatusCode.ACCESS_ERROR)));
            }
        };
    }

}