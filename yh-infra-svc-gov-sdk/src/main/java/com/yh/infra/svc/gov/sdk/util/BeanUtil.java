package com.yh.infra.svc.gov.sdk.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/3/11
 * Time: 14:07
 * To change this template use File | Settings | File Templates.
 * Description:bean工具类
 */
public class BeanUtil {


    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    private BeanUtil() {
    }

    /**
     * 拷贝对象属性，不抛出异常。深层次拷贝
     *
     * @param dest 目标对象
     * @param src  元对象
     */
    public static void copyPropertiesSafe(Object dest, Object src) {
        if ((null == src) || (null == dest)) {
            return;
        }
        try {
            BeanUtils.copyProperties(src, dest);
        } catch (Throwable e) {
            logger.warn("BeanUtil.copyPropertiesSafe exception ..", e);
        }
    }

}