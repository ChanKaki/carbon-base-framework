package com.carbon.web.handler;

import com.carbon.web.enums.BusinessCodeEnum;
import com.carbon.web.error.BusinessException;
import com.carbon.web.vo.BaseResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * 类<code>GlobalExceptionHandler</code>说明：
 *
 * @author kaki
 * @since 21/8/2023
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private HttpServletResponse httpServletResponse;

    /**
     * 自定义 业务异常
     */
    @ExceptionHandler(value = BusinessException.class)
    public BaseResponseVo<?> handleBusinessException(BusinessException e) {
        log.warn("业务异常:{}", e.getMessage());
        return BaseResponseVo.error(e);
    }

    /**
     * 自定义 业务异常
     */
    @ExceptionHandler(value = Exception.class)
    public BaseResponseVo<?> handleException(Exception e) {
        log.error("代码异常:{}", e.getMessage());
        e.printStackTrace();
        return BaseResponseVo.error(new BusinessException(BusinessCodeEnum.ERROR));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResponseVo<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("入参校验异常:{}", e.getMessage());
        e.printStackTrace();
        return BaseResponseVo.error(e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(",")));
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public BaseResponseVo<?> handleNoHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        log.error("请求路径不存在:{}", e.getMessage());
        httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return BaseResponseVo.error(new BusinessException(BusinessCodeEnum.NOT_FOUND));
    }
}
