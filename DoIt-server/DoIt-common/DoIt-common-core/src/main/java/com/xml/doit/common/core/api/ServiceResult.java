package com.xml.doit.common.core.api;


/**
 * 服务间调用响应结果
 *
 * @author XMINGL
 * @since 1.0.0
 */
public interface ServiceResult {

    /**
     * 获取响应code
     * @return 响应代码
     */
    int getCode();

    /**
     * 获取响应信息
     * @return 响应信息
     */
    String getMessage();
}
