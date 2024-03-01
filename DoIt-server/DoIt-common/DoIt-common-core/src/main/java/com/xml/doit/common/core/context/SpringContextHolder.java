package com.xml.doit.common.core.context;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.xml.doit.common.core.constants.DoItStrPool;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring上下文管理工具
 *
 * @author XMINGL
 * @since 1.0.0
 */
public class SpringContextHolder implements ApplicationContextAware {

    /**
     * 上下文对象实例
     */
    @Getter
    private static ApplicationContext applicationContext;

    private static final ThreadLocal<Map<String, String>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 根据class类型获取bean
     *
     * @param clazz 接口class类型
     * @param <T>   泛型
     * @return 实例
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取HttpServletRequest对象
     *
     * @return 实例
     */
    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.nonNull(requestAttributes) ? requestAttributes.getRequest() : null;
    }

    /**
     * 设置THREAD_LOCAL
     *
     * @param localMap TransmittableThreadLocal
     */
    public static void setLocalMap(Map<String, String> localMap) {
        THREAD_LOCAL.set(localMap);
    }

    /**
     * 设置THREAD_LOCAL
     *
     * @param key   key
     * @param value value
     */
    public static void set(String key, String value) {
        Map<String, String> map = THREAD_LOCAL.get();
        if (MapUtil.isEmpty(map)) {
            map = new ConcurrentHashMap<>(16);
            THREAD_LOCAL.set(map);
        }
        map.put(key, value == null ? DoItStrPool.EMPTY : value);
    }

    /**
     * 获取Thread_local中设定的值
     *
     * @param key key
     * @return value
     */
    public static String get(String key) {
        Map<String, String> map = THREAD_LOCAL.get();
        return MapUtil.isEmpty(map) ? DoItStrPool.EMPTY : Convert.toStr(map.get(key));
    }

    /**
     * 移除THREAD_LOCAL
     */
    public static void removeLocalMap() {
        THREAD_LOCAL.remove();
    }

}
