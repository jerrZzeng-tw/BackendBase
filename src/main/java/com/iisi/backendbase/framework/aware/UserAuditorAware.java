package com.iisi.backendbase.framework.aware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class UserAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            //TODO 这里根据具体情况获取用户信息
            //            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //            if (authentication instanceof AnonymousAuthenticationToken) {
            //                return Optional.of("HSystem");
            //            } else {
            //                if (authentication == null) {
            //                    return Optional.of("sys");
            //                }
            //                User user = (User) authentication.getPrincipal();
            //                return Optional.of(user.getUsername());
            //            }
            return Optional.of("sys");
        } catch (Exception ex) {
            log.error("get user Authentication failed: {}", ex.getMessage(), ex);
            return Optional.of("sys");
        }
    }

}