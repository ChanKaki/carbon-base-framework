package com.carbon.web.error;

import com.carbon.web.enums.BusinessCodeEnum;
import lombok.Getter;

/**
 * 类<code>BusinessExecption</code>说明：
 *
 * @author kaki
 * @since 21/8/2023
 */
@Getter
public class BusinessException extends RuntimeException {

    private BusinessCodeEnum businessErrorEnum;

    public BusinessException(BusinessCodeEnum businessErrorEnum) {
        super(businessErrorEnum.getDesc());
        this.businessErrorEnum = businessErrorEnum;
    }

    public BusinessException(BusinessCodeEnum businessErrorEnum, String message) {
        super(message);
        this.businessErrorEnum = businessErrorEnum;
    }
}
