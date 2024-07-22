package com.iisi.backendbase.framework.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iisi.backendbase.entity.Log;
import com.iisi.backendbase.framework.dto.BaseDTO;
import com.iisi.backendbase.framework.services.LogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class ApLogAdvice {
    @Autowired
    private LogService logService;

    @Autowired
    private ObjectMapper objectMapper;

    @Before("@annotation(com.iisi.backendbase.framework.annotation.ApLog)")
    public void doApLog(JoinPoint pointcut) {
        log.info("aplog");
        Log logData = Log.builder().userId("sys").build();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            logData.setUrl(request.getRequestURI());
            final SecurityContext securityContext = SecurityContextHolder.getContext();
            if (!Objects.isNull(securityContext)) {
                final Authentication authentication = securityContext.getAuthentication();
                if (!Objects.isNull(authentication)) {
                    final String username = authentication.getName();
                    logData.setUserId(username);
                }
            }
            MethodSignature methodSig = (MethodSignature) pointcut.getSignature();
            BaseDTO dto = getCase(pointcut);
            logData.setData(dto == null ? "" : objectMapper.writeValueAsString(dto));
        } catch (Exception e) {
            log.error("dtoToJson error!!", e);
            logData.setData("dtoToJson error!!");
        }
        logService.saveLog(logData);
    }


    public BaseDTO getCase(JoinPoint pointcut) {
        Optional<Object> ob = Arrays.stream(pointcut.getArgs()).filter(t -> t instanceof BaseDTO).findFirst();
        return (BaseDTO) ob.orElse(null);
    }

    //    public void hasAuthorities(final JoinPoint joinPoint, final HasEndpointAuthorities authorities) {
    //        final SecurityContext securityContext = SecurityContextHolder.getContext();
    //        if (!Objects.isNull(securityContext)) {
    //            final Authentication authentication = securityContext.getAuthentication();
    //            if (!Objects.isNull(authentication)) {
    //                final String username = authentication.getName();
    //
    //                final Collection<? extends GrantedAuthority> userAuthorities = authentication.getAuthorities();
    //
    //                if (Stream.of(authorities.authorities()).noneMatch(authorityName -> userAuthorities.stream().anyMatch(userAuthority ->
    //                        authorityName.name().equals(userAuthority.getAuthority())))) {
    //
    //                    log.error("User {} does not have the correct authorities required by endpoint", username);
    //                    throw new ApiException(DefaultExceptionReason.FORBIDDEN);
    //                }
    //            } else {
    //                log.error("The authentication is null when checking endpoint access for user request");
    //                throw new ApiException(DefaultExceptionReason.UNAUTHORIZED);
    //            }
    //        } else {
    //            log.error("The security context is null when checking endpoint access for user request");
    //            throw new ApiException(DefaultExceptionReason.FORBIDDEN);
    //        }
    //    }
}