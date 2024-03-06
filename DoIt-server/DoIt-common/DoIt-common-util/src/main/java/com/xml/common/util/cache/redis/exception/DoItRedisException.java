package com.xml.common.util.cache.redis.exception;

import com.xml.doit.common.core.api.ServiceResult;
import com.xml.doit.common.core.exception.DoItException;

/**
 * Redis操作异常类
 *
 * @author XMINGL
 * @since 1.0.0
 */
public class DoItRedisException extends DoItException {
    public DoItRedisException(ServiceResult serviceResult) {
        super(serviceResult);
    }

    public DoItRedisException(int code, String msg) {
        super(code, msg);
    }
}
