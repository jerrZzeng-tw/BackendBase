package com.iisi.backendbase.framework.aop;

import com.iisi.backendbase.framework.BaseRuntimeException;
import com.iisi.backendbase.framework.ResponseData;
import com.iisi.backendbase.framework.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        String methodName = methodParameter.getMethod().getName();
        log.info(methodName);
        return !"exceptionHandler".equals(methodName);
    }

    /**
     * 統一封裝回傳JSON
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResponseData beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                        Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                        ServerHttpResponse response) {
        // log.info("beforeBodyWrite");
        if (body instanceof ResponseData) {
            return (ResponseData) body;
        }
        return new ResponseData(StatusCode.SUCCESS, body);
    }

    @ExceptionHandler(value = BaseRuntimeException.class)
    public ResponseEntity<ResponseData> baseExceptionHandler(BaseRuntimeException baseRuntimeException) {
        log.error("tb exception, message : " + baseRuntimeException.getMessage(), baseRuntimeException);
        ResponseData responseObject =
                new ResponseData(baseRuntimeException.getErrorCode(), baseRuntimeException.getErrorMessage(), baseRuntimeException.getData());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(responseObject, headers, HttpStatus.OK);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseData> exceptionHandler(Exception exception) {
        log.error("Occur internal exception, message : " + exception.getMessage(), exception);
        ResponseData responseObject = new ResponseData(StatusCode.SYS_ERROR);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(responseObject, headers, HttpStatus.OK);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ResponseData> exceptionHandler(BadCredentialsException exception) {
        log.error("Occur badCredentials exception, message : " + exception.getMessage(), exception);
        ResponseData responseObject = new ResponseData(StatusCode.LOGIN_ERROR);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(responseObject, headers, HttpStatus.OK);
    }
}