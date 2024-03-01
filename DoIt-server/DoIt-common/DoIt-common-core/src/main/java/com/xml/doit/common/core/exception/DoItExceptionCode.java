package com.xml.doit.common.core.exception;

import com.xml.doit.common.core.api.ServiceResult;

/**
 * 异常枚举类
 *
 * @author XMINGL
 * @see ServiceResult
 * @since 1.0.0
 */
public enum DoItExceptionCode implements ServiceResult {
    SYSTEM_500(500, "未知系统异常"),
    SYSTEM_501(501, "请求参数异常"),
    SYSTEM_502(502, "请求调用异常"),
    SYSTEM_503(503, "响应超时"),

    SYSTEM_UNAUTHORIZED(401, "接口未授权"),
    SYSTEM_404(404, "请求路径不存在"),

    JWT_TOKEN_EXPIRED(600, "凭证已失效，请重新登录"),
    JWT_SIGNATURE(601, "凭证签名错误，请重新登录"),
    JWT_ILLEGAL_ARGUMENT(602, "无法获取凭证，请重新登录"),
    JWT_PARSER_TOKEN_FAIL(603, "凭证解析失败，请重新登录"),

    HTTP_REQUEST_ERROR(5000, "请求方式错误"),
    ;

    private int code;
    private String message;

    DoItExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    public DoItExceptionCode setCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public DoItExceptionCode setMessage(String message) {
        this.message = message;
        return this;
    }
}
