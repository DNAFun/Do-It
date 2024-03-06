package com.xml.common.util.cache.redis.exception;

import com.xml.doit.common.core.api.ServiceResult;

/**
 * Redis操作相关异常
 *
 * @author XMINGL
 * @since 1.0.0
 */
public enum RedisExceptionCode implements ServiceResult {
    SYSTEM_501(501, "参数异常");

    private int code;
    private String message;

    RedisExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RedisExceptionCode setCode(int code) {
        this.code = code;
        return this;
    }

    public RedisExceptionCode setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
