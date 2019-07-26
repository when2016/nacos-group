package com.lmcat.test.example.gateway.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 全局异常拦截器
 */
@Slf4j
public class GlobalExceptionHandler extends DefaultErrorWebExceptionHandler {

    private final ErrorAttributes errorAttributes;

    public GlobalExceptionHandler(
            ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
            ErrorProperties errorProperties, ApplicationContext applicationContext
    ) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
        this.errorAttributes = errorAttributes;
    }

    /**
     * 指定响应处理方法为JSON处理的方法
     * @param errorAttributes
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 根据code获取对应的HttpStatus
     * @param errorAttributes
     */
    @Override
    protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
        return HttpStatus.valueOf(200); //全部返回成功
    }

    /**
     * 获取异常属性
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        Throwable error = getError(request);
        HttpStatus errorStatus = determineHttpStatus(error);
        errorAttributes.put("code", String.valueOf(errorStatus.value()));
        errorAttributes.put("message", determineMessage(error));
        errorAttributes.put("result", null);

        log.error(
                "path:{}, status:{}, message:{}, error:{}",
                request.path(),
                errorStatus.value(),
                errorAttributes.get("message"),
                errorStatus.getReasonPhrase(),
                errorStatus.value() >= 500 ? error : null
        );
        error.printStackTrace();
        return errorAttributes;
    }

    private HttpStatus determineHttpStatus(Throwable error) {
        if (error instanceof ResponseStatusException) {
            return ((ResponseStatusException) error).getStatus();
        }
        ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(error.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            return responseStatus.code();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String determineMessage(Throwable error) {
        if (error instanceof WebExchangeBindException) {
            return error.getMessage();
        }
        if (error instanceof ResponseStatusException) {
            return ((ResponseStatusException) error).getReason();
        }
        ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(error.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            return responseStatus.reason();
        }
        return error.getMessage();
    }

}
