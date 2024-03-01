package com.xml.doit.common.core.exception;

import com.xml.doit.common.core.api.ServiceResult;
import lombok.Getter;
import lombok.Setter;


/**
 * 基本的系统异常类
 *
 * @author XMINGL
 * @since 1.0.0
 */
@Getter
@Setter
public class DoItException extends RuntimeException {

    private ServiceResult serviceResult;

    public DoItException(ServiceResult serviceResult) {
        this.serviceResult = serviceResult;
    }

    public DoItException(int code, String msg) {
        this.serviceResult = new ServiceResult() {
            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return null;
            }
        };
    }
}
