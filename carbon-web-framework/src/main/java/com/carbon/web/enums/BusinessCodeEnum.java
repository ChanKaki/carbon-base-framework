package com.carbon.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 枚举<code>BusinessErrorEnum</code>说明：
 *
 * @author kaki
 * @since 21/8/2023
 */
@AllArgsConstructor
@Getter
public enum BusinessCodeEnum {

    SUCCESS(200, "SUCCESS", "成功"),
    BAD_REQUEST(403, "BAD_REQUEST", "参数存在问题"),
    NOT_FOUND(404, "NOT_FOUND", "路径不存在"),
    ERROR(500, "ERROR", "系统异常"),
    ;

    private Integer code;

    private String msg;

    private String desc;
}
