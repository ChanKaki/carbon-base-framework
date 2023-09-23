package com.carbon.web.vo;

import com.carbon.web.enums.BusinessCodeEnum;
import com.carbon.web.error.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 类<code>BaseResponseVo</code>说明：
 *
 * @author kaki
 * @since 21/8/2023
 */
@Data
@AllArgsConstructor
public class BaseResponseVo<T> {

    private boolean success;

    private Integer code;

    private String msg;

    private Long currentTime;

    private T data;

    public static <T> BaseResponseVo<T> error(BusinessException e) {
        return BaseResponseVo.build(false, e.getBusinessErrorEnum(), null);
    }

    public static <T> BaseResponseVo<T> error(String message) {
        return BaseResponseVo.build(false, BusinessCodeEnum.ERROR.getCode(), message, null);
    }

    public static <T> BaseResponseVo<T> error(BusinessCodeEnum codeEnum, String message) {
        return BaseResponseVo.build(false, codeEnum.getCode(), message, null);
    }


    public static <T> BaseResponseVo<T> success(T data) {
        return BaseResponseVo.build(true, BusinessCodeEnum.SUCCESS, data);
    }

    public static <T> BaseResponseVo<T> success() {
        return BaseResponseVo.build(true, BusinessCodeEnum.SUCCESS, null);
    }

    private static <T> BaseResponseVo<T> build(boolean success, BusinessCodeEnum codeEnum, T data) {
        return build(success, codeEnum.getCode(), codeEnum.getMsg(), data);
    }

    private static <T> BaseResponseVo<T> build(boolean success, Integer code, String msg, T data) {
        return new BaseResponseVo<T>(success, code, msg, System.currentTimeMillis(), data);
    }

}
