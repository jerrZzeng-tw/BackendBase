package com.iisi.backendbase.framework.services;

import com.iisi.backendbase.entity.User;
import com.iisi.backendbase.framework.security.SecurityUser;
import com.iisi.backendbase.repo.UserRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("");
        }
        try {
            Optional<User> user_op = userRepository.findByUsername(username);
            if (user_op.isEmpty()) {
                throw new UsernameNotFoundException(username);
            }
            return new SecurityUser(user_op.get());
        } catch (final Exception e) {
            log.error("loadUserByUsername:", e);
            throw new UsernameNotFoundException(username);
        }
    }
}